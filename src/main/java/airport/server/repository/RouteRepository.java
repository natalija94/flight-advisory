package airport.server.repository;

import airport.server.model.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author natalija
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

}