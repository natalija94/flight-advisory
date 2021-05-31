package airport.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author natalija
 */
@Data
public class AirportDTO implements Serializable {
    private CityDTO cityDTO;
    private String airportId;
    private String airportName;
    private String icao;
    private String iata;
}
