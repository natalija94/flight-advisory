package airport.server.repository;

import airport.server.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author natalija
 */
@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
}
