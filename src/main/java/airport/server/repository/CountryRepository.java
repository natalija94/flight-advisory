package airport.server.repository;

import airport.server.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author natalija
 */
@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findFirstByName(String name);
}
