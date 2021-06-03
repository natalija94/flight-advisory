package airport.server.service;

import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;

/**
 * @author natalija
 */
public interface CityReviewService {
    /**
     * Resolves all the reviews for the city.
     * @return
     */
    ResultDTO getAllReviews();

    /**
     * Resolves the specified amount of reviews for the city .
     * @param numberOfResults
     * @return reviews
     */
    ResultDTO getAllReviewsLatestResults(int numberOfResults);

    /**
     * Resolves the reviews for the city according to its name.
     * @param  cityName as name of the city
     * @return reviews
     */
    ResultDTO getAllReviews(String cityName);

    /**
     * Resolves the specified amount of reviews for the city according to its name.
     * @param  cityName as name of the city
     * @return reviews
     */
    ResultDTO getAllReviewsLatestResults(String cityName, int numberOfComments);

    /**
     * Adding new review for the city.
     * @param reviewDto as review to be added.
     * @return info about action
     */
    ResultDTO addReview(CityReviewDTO reviewDto);

    /**
     * Updating review for the city.
     * @param reviewDto as review to be updated.
     * @return info about action
     */
    ResultDTO updateReview(CityReviewDTO reviewDto);

    /**
     * Deletes review for the city.
     * @param reviewId as review to be deleted.
     * @return info about action
     */
    ResultDTO deleteComment(Long reviewId);

    /**
     * Deletes review for the city.
     * @param cityIdSource as a source city
     * @param cityIdDest as dest city
     * @return info about the cheapest flight
     */
    ResultDTO findTheCheapestFlight(Long cityIdSource, Long cityIdDest);
}
