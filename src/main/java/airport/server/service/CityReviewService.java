package airport.server.service;

import airport.server.dto.CheapestFlightParamsDTO;
import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;

/**
 * @author natalija
 */
public interface CityReviewService {
    ResultDTO getAllComments();
    ResultDTO getAllCommentsLatestResults(int numberOfResults);
    ResultDTO getAllComments(String cityName);
    ResultDTO getAllCommentsLatestResults(String cityName, int numberOfComments);

    ResultDTO addComment(CityReviewDTO reviewDto);
    ResultDTO updateComment(CityReviewDTO reviewDto);
    ResultDTO deleteComment(CityReviewDTO reviewDto);

    ResultDTO findTheCheapestFlight(CheapestFlightParamsDTO flightParamsDTO);
}
