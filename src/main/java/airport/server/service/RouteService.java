package airport.server.service;

import airport.server.dto.ResultDTO;
import airport.server.service.impl.RouteServiceImpl;

import java.util.List;

/**
 * @author natalija
 */
public interface RouteService {
    List<RouteServiceImpl.Path> getAvailableRoutesForSourceCity(Long sourceId, Long finalDestination);

    ResultDTO getCheapestRouteDetails(Long sourceId, Long finalDestination);
}
