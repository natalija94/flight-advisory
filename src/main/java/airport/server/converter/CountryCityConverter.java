package airport.server.converter;

import airport.server.dto.CityDTO;
import airport.server.dto.CountryDTO;
import airport.server.exceptions.ParentEntityNotValid;
import airport.server.model.City;
import airport.server.model.Country;
import airport.server.repository.CityRepository;
import airport.server.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author natalija
 */
@Service
public class CountryCityConverter {

    private CountryRepository countryRepository;
    private CityRepository cityRepository;

    public CountryCityConverter(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    public CountryDTO assembleCountryDTOFromCountry(Country country) {
        if (country == null) {
            return null;
        } else {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setId(country.getId());
            countryDTO.setName(country.getName());
            return countryDTO;
        }
    }

    public Country assembleCountryFromCountryDTO(CountryDTO countryDTO) {
        if (countryDTO == null) {
            return null;
        } else {
            Country country = new Country();
            country.setId(countryDTO.getId());
            country.setName(countryDTO.getName());

            return country;
        }
    }

    public CityDTO assembleCityDTOFromCity(City city) {
        if (city != null && city.getCountryId() != null) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setId(city.getId());
            cityDTO.setName(city.getName());
            cityDTO.setCountryDTO(assembleCountryDTOFromCountry(assembleCountryFromCountryDTO(cityDTO.getCountryDTO())));
            return cityDTO;
        }
        return null;
    }

    public City assembleCityFromCityDTO(CityDTO cityDTO) throws ParentEntityNotValid {
        City city = null;
        if (cityDTO != null && cityDTO.getCountryDTO() != null && cityDTO.getCountryDTO().getId() != null) {
            Optional<Country> country = countryRepository.findById(cityDTO.getCountryDTO().getId());
            if (country != null && !country.isEmpty()) {
                city = new City();
                city.setId(cityDTO.getId());
                city.setName(cityDTO.getName());
                city.setCountryId(country.get().getId());
            } else {
                throw new ParentEntityNotValid();
            }
        }
        return city;
    }
}
