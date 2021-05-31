package airport.server.dto;

import airport.server.enums.ResponseStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * @author natalija
 */
@Data
public class ResultDTO implements Serializable {
    private Object data;
    private ResponseStatus responseStatus;
    private String errorMessage;
}
