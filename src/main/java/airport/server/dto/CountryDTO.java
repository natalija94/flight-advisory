package airport.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author natalija
 */
@Data
public class CountryDTO implements Serializable {
    private Long id;
    private String name;
}
