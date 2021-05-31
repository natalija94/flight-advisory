package airport.server.controller;

import airport.server.dto.ResultDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author natalija
 */
@RequestMapping("/review")
@RestController
public class RegularUserFacade {

    @PostMapping("/add-comment")
    public ResultDTO addComment(){
        return null;
    }

}
