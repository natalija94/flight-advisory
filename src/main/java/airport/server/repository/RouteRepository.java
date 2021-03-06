package airport.server.repository;

import airport.server.model.FlightVO;
import airport.server.model.Route;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author natalija
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    /**
     * Returns all routes where one airport is start any airport is destination.
     * Made in order to handle connections between airports / stop points.
     *
     * @param sourceCityId source airport
     * @return route
     */
    @Query("select new airport.server.model.FlightVO(route, airportSource, airportDest, citySource, cityDest) from Route route " +
            " JOIN Airport  airportSource on route.airportSourceId = airportSource.id" +
            " JOIN Airport  airportDest on route.airportDestinationId = airportDest.id " +
            " JOIN City citySource on citySource.id = airportSource.cityId " +
            " JOIN City cityDest on cityDest.id = airportDest.cityId  " +
            " WHERE :sourceCityId = citySource.id")
    List<FlightVO> findAllRoutesDetailsForOneCity(Long sourceCityId);
}