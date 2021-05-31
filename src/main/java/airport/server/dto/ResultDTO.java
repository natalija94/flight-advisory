package airport.server.dto;

import airport.server.enums.ResponseStatus;
import lombok.Data;

/**
 * @author natalija
 */
@Data
public class ResultDTO {
    private Object data;
    private ResponseStatus responseStatus;
    private String errorMessage;
}
