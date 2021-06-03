package airport.server.service;

import airport.server.dto.ResultDTO;
import airport.server.service.impl.RouteServiceImpl;

import java.util.List;

/**
 * @author natalija
 */
public interface RouteService {
    /**
     * Finds all the routes for one city to another.
     * @param sourceId as start city
     * @param finalDestination as  destination city
     * @return the list of Flighs from one to another city.
     */
    List<RouteServiceImpl.Path> getAvailableRoutesForSourceCity(Long sourceId, Long finalDestination);

    /**
     * Finds the cheapest route from one city to another.
     * @param sourceId as start city
     * @param finalDestination as  destination city
     * @return the cheapest route.
     */
    ResultDTO getCheapestRouteDetails(Long sourceId, Long finalDestination);
}
