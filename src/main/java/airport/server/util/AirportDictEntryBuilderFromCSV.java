package airport.server.util;

import airport.server.enums.DST;
import airport.server.model.Airport;
import airport.server.model.City;
import airport.server.model.Country;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author natalija
 */
public class AirportDictEntryBuilderFromCSV {
    private AirportDictEntryBuilderFromCSV() {
    }
    private static Logger LOG = LoggerFactory.getLogger(AirportDictEntryBuilderFromCSV.class);

    public static Country buildCountry(String[] csvRow) {
        if (Arrays.isNullOrEmpty(csvRow)) {
            return null;
        }
        String value = csvRow[3];
        if (StringUtils.isNotEmpty(value)) {
            Country country = new Country();
            country.setName(value);
            return country;
        }
        return null;
    }

    public static City buildCity(String[] csvRow) {
        if (Arrays.isNullOrEmpty(csvRow)) {
            return null;
        }
        String cityName = csvRow[2];
        Country country = buildCountry(csvRow);
        if (StringUtils.isNotEmpty(cityName) && country != null) {
            City city = new City();
            city.setName(cityName);
            return city;
        }
        return null;
    }

    public static Airport buildAirport(String[] csvRow) {
        if (Arrays.isNullOrEmpty(csvRow)) {
            return null;
        }
        try {
            Airport airport;

            City city = buildCity(csvRow);
            String airportId = csvRow[0];

            if (city == null || StringUtils.isEmpty(airportId)) {
                return null;
            }
            airport = new Airport();
            airport.setAirportId(Long.parseLong(airportId));
            airport.setName(csvRow[1]);
            airport.setCityId(city.getId());

            airport.setIataCode(csvRow[4]);
            airport.setIcaoCode(csvRow[5]);

            String latitude = csvRow[6];
            String longitude = csvRow[7];
            String altitude = csvRow[8];
            airport.setLatitude(new BigDecimal(latitude));
            airport.setLongitude(new BigDecimal(longitude));
            if (altitude != null) {
                airport.setAltitude(Integer.parseInt(altitude));
            }

            String timezone = csvRow[9];
            try{
                airport.setTimezone(Float.parseFloat(timezone));
            } catch(Exception e){
                LOG.error("Not possible to parse timezone for airport: {}, timezone: {}", airportId, timezone);
            }
            String dst = csvRow[10];
            if (dst != null) {
                airport.setDst(dst != null ? DST.valueOf(dst) : null);
            }
            airport.setTzOlsen(csvRow[11]);
            airport.setType(csvRow[12]);
            airport.setSource(csvRow[13]);
            return airport;
        } catch (Exception e) {
            LOG.error("Error occured while pasing row. {}, {}", csvRow, e.getMessage());
        }
        return null;
    }
}
