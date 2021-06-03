package airport.server.repository;

import airport.server.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author natalija
 */
@Repository
public interface  CityRepository extends CrudRepository<City, Long>  {
    /**
     * Finds first found city from the DB.
     * @param name as city name
     * @return concrete city.
     */
    City findFirstByName(String name);
}
