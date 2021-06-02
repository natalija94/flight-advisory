package airport.server.dto;

import airport.server.model.FlightVO;
import airport.server.service.impl.RouteServiceImpl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author natalija
 */

@Data
public class FlightReviewDTO implements Serializable {
    private List<RouteServiceImpl.Path> allPossibleRoutes;
    private List<FlightVO> cheapestRoute;
}
