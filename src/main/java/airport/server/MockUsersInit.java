package airport.server;

import airport.server.model.User;
import airport.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author natalija
 */
@Component
public class MockUsersInit  implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public MockUsersInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Insert users into DB.
     * @param args app args.
     */
      public void run(ApplicationArguments args) {
        userRepository.save(new User(1, "natalija", "natalija","pavlovic", "1234", true, "USER"));
        userRepository.save(new User(2, "pavle", "pavle","pekic", "pavlee15", true, "USER"));
        userRepository.save(new User(3, "admin", "admin","admin", "admin1234", true, "ADMIN"));
    }
}