package airport.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author natalija
 */
@Data
public class CityDTO implements Serializable {
    private Long id;
    private String name;
    private CountryDTO countryDTO;

    private String description;
}
