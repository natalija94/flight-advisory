package airport.server.dto;

import airport.server.model.FlightVO;
import airport.server.service.impl.RouteServiceImpl;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author natalija
 */

@Data
public class FlightReviewDTO implements Serializable {
    /**
     * All possible routes for one to another city with its prices and number of stops.
     */
    private List<RouteServiceImpl.Path> allPossibleRoutes;

    /**
     * Cheapest route with all the details regarding any stopping point.
     */
    private List<FlightVO> cheapestRoute;
    /**
     * Total amount of the cheapest route.
     */
    private BigDecimal cheapestAmount;
}
