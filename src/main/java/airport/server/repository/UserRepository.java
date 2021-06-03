package airport.server.repository;

import airport.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author natalija
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Returns user according to its username.
     * @param userName as name of the user
     * @return user
     */
    Optional<User> findByUserName(String userName);
}