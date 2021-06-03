package airport.server.service.impl;

import airport.server.converter.CountryCityConverter;
import airport.server.dto.CityDTO;
import airport.server.dto.ResultDTO;
import airport.server.enums.DictionaryType;
import airport.server.enums.ResponseStatus;
import airport.server.model.Airport;
import airport.server.model.City;
import airport.server.model.Country;
import airport.server.model.Route;
import airport.server.repository.AirportRepository;
import airport.server.repository.CityRepository;
import airport.server.repository.CountryRepository;
import airport.server.repository.RouteRepository;
import airport.server.service.AdministrationService;
import airport.server.util.AirportDictEntryBuilderFromCSV;
import airport.server.util.RouteDictEntryBuilderFromCSV;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author natalija
 */
@Service
@Slf4j
public class AdministrationServiceImpl implements AdministrationService {

    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private AirportRepository airportRepository;
    private CountryCityConverter countryCityConverter;
    private DictionaryServiceImpl dictionaryService;
    private RouteRepository routeRepository;

    public AdministrationServiceImpl(CountryRepository countryRepository, CityRepository cityRepository, AirportRepository
            airportRepository, CountryCityConverter countryCityConverter, DictionaryServiceImpl dictionaryService, RouteRepository routeRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.countryCityConverter = countryCityConverter;
        this.dictionaryService = dictionaryService;
        this.routeRepository = routeRepository;
    }

    @Transactional
    @Override
    public ResultDTO saveCity(CityDTO cityDTO) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            City city = countryCityConverter.assembleCityFromCityDTO(cityDTO);
            if (city != null) {
                cityRepository.save(city);
                resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
                resultDTO.setData(cityDTO);
            }
        } catch (Exception e) {
            resultDTO.setErrorMessage(StringUtils.join("Not possible to save cityDTO.", e.getMessage()));
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            log.error("Not possible to save cityDTO. {cityDTO}", cityDTO);
        }
        return resultDTO;
    }

    @Transactional
    @Override
    public void importAirports() throws Exception {
        List<String[]> airports = dictionaryService.parseDictionary(DictionaryType.AIRPORTS);
        for (String[] airportROW : airports) {
            Airport airportCSV = AirportDictEntryBuilderFromCSV.buildAirport(airportROW);
            Country countryCSV = AirportDictEntryBuilderFromCSV.buildCountry(airportROW);
            City cityFromDictCSV = AirportDictEntryBuilderFromCSV.buildCity(airportROW);

            if (cityFromDictCSV == null || countryCSV == null) {
                continue;
            }

            if (airportCSV != null && airportCSV.getAirportId() != null) {
                //take from DB of exists
                countryCSV = countryRepository.findFirstByName(countryCSV.getName()) != null ?
                        countryRepository.findFirstByName(countryCSV.getName()) : countryCSV;
                cityFromDictCSV = cityRepository.findFirstByName(countryCSV.getName()) != null ?
                        cityRepository.findFirstByName(countryCSV.getName()) : cityFromDictCSV;

                try {
                    if (countryCSV.getId() == null) {
                        countryCSV= countryRepository.save(countryCSV);
                    }

                    if (cityFromDictCSV.getId() == null) {
                        cityFromDictCSV.setCountryId(countryCSV.getId());
                        cityFromDictCSV = cityRepository.save(cityFromDictCSV);
                    }
                    airportCSV.setCityId(cityFromDictCSV.getId());
                    airportRepository.save(airportCSV);
                } catch (Exception e) {
                    log.error("Airport: {}", airportCSV);
                    //probably you will get some errors with sequence; which is strange while
                    log.error("Error occurred while saving to DB. {}", e.getMessage());
                }
            }
        }
    }

    @Transactional
    @Override
    public void importRoutes() {
        List<String[]> flightRoutes = dictionaryService.parseDictionary(DictionaryType.ROUTES);
        for (String[] routeRow : flightRoutes) {
            Route route = RouteDictEntryBuilderFromCSV.buildFlightRoute(routeRow);

            if (route != null) {
                Long sourceIdAirport = route.getAirportSourceId();
                Long destIdAirport = route.getAirportDestinationId();

                Optional<Airport> airport = airportRepository.findById(sourceIdAirport);
                Optional<Airport> airport2 = airportRepository.findById(destIdAirport);

                if (airport == null || airport.isEmpty() ||
                        airport2 == null || airport2.isEmpty()) {
                    log.error("Route: {} will not be added while no appropriate airport or city can be found in the database.", routeRow);
                    continue;
                }

                try {
                    routeRepository.save(route);
                } catch (Exception e) {
                    log.error("Not possible to save route: {}. ERROR:{}", route, e.getMessage());
                }

            }
        }
    }
}
