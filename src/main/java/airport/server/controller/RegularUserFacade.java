package airport.server.controller;

import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;
import airport.server.service.CityReviewService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author natalija
 */
@RequestMapping("/reviews")
@RestController
@Api( tags = "City reviews")
public class RegularUserFacade {

    private CityReviewService cityReviewService;

    @Autowired
    public RegularUserFacade(CityReviewService cityReviewService) {
        this.cityReviewService = cityReviewService;
    }

    @PostMapping("/add-review")
    public ResultDTO addComment(@RequestBody CityReviewDTO reviewDTO) {
        return cityReviewService.addReview(reviewDTO);
    }

    @PostMapping("/update-review")
    public ResultDTO updateReview(@RequestBody CityReviewDTO reviewDTO) {
        return cityReviewService.updateReview(reviewDTO);
    }

    @DeleteMapping("/review/{id}")
    public ResultDTO deleteReview(@PathVariable Long id) {
        return cityReviewService.deleteComment(id);
    }

    @GetMapping("/all")
    public ResultDTO getAll(@RequestParam(name = "numberOfResults", required = false) Integer numberOfResults) {
        return numberOfResults != null ? cityReviewService.getAllReviewsLatestResults(numberOfResults) :
                cityReviewService.getAllReviews();
    }

    @GetMapping("/all-by-name")
    public ResultDTO getAll(@RequestParam(name = "numberOfResults", required = false) Integer numberOfResults,
                            @RequestParam(name = "cityName") String cityName) {
        return numberOfResults != null ? cityReviewService.getAllReviewsLatestResults(cityName, numberOfResults) :
                cityReviewService.getAllReviews(cityName);
    }


    //returns all posible routes and also an information about the cheapest one
    //you can try with quick search cityIdSource=7072 & cityIdDest=7071    and vice versa:)))
    //some more popular destination would take more time for returning results
    @GetMapping("/cheapest-flight")
    public ResultDTO findCheapestFlight(@RequestParam("cityIdSource") Long cityIdSource,
                                        @RequestParam("cityIdDest") Long cityIdDest) {
        return cityReviewService.findTheCheapestFlight(cityIdSource, cityIdDest);
    }


    @GetMapping("/info")
    public String info(){
        return "TEST";
    }
}
