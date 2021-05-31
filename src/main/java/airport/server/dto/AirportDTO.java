package airport.server.dto;

import lombok.Data;

/**
 * @author natalija
 */
@Data
public class AirportDTO {
    private CityDTO cityDTO;
    private String airportId;
    private String airportName;
    private String icao;
    private String iata;
}
