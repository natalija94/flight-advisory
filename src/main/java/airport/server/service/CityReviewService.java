package airport.server.service;

import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;

/**
 * @author natalija
 */
public interface CityReviewService {
    ResultDTO getAllReviews();
    ResultDTO getAllReviewsLatestResults(int numberOfResults);
    ResultDTO getAllReviews(String cityName);
    ResultDTO getAllReviewsLatestResults(String cityName, int numberOfComments);

    ResultDTO addReview(CityReviewDTO reviewDto);
    ResultDTO updateReview(CityReviewDTO reviewDto);
    ResultDTO deleteComment(Long reviewId);

    ResultDTO findTheCheapestFlight(Long cityIdSource, Long cityIdDest);
}
