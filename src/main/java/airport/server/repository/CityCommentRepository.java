package airport.server.repository;

import airport.server.model.City;
import airport.server.model.CityComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author natalija
 */
@Repository
public interface CityCommentRepository extends CrudRepository<CityComment, Long> {
}
