package airport.server.converter;

import airport.server.dto.CityReviewDTO;
import airport.server.model.City;
import airport.server.model.CityReview;
import airport.server.repository.CityRepository;
import airport.server.repository.CityReviewRepository;
import airport.server.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author natalija
 */

/**
 * Converts CityReviews DTOs to DB objects, and vice versa.
 */
@Service
public class CityReviewConverter {

    private CityRepository cityRepository;
    private CityReviewRepository cityReviewRepository;

    public CityReviewConverter(CityRepository cityRepository, CityReviewRepository cityReviewRepository) {
        this.cityRepository = cityRepository;
        this.cityReviewRepository = cityReviewRepository;
    }

    public CityReview convertReviewDTOToReview(CityReviewDTO reviewDto) {
        if (reviewDto == null || reviewDto.getCityId() == null) {
            return null;
        }
        Optional<City> cityOptional = cityRepository.findById(reviewDto.getCityId());
        if (!cityOptional.isPresent()) {
            return null;
        }
        Long commentId = reviewDto.getReviewId();
        CityReview review;

        if (commentId != null) {
            Optional<CityReview> reviewOptional = cityReviewRepository.findById(commentId);
            //is there in city review with this reviewId
            if (!reviewOptional.isPresent()) {
                return null;
            }
            review = reviewOptional.get();
            review.setUpdated(DateUtil.convertToLocalDateTimeNow());
        } else {
            review = new CityReview();
        }
        review.setCityId(cityOptional.get().getId());
        review.setDescription(reviewDto.getReviewContent());
        return review;
    }

    public CityReviewDTO convertReviewToReviewDTO(CityReview review) {
        if (review == null) {
            return null;
        }
        CityReviewDTO reviewDto = new CityReviewDTO();
        reviewDto.setReviewId(review.getId());
        reviewDto.setReviewContent(review.getDescription());
        reviewDto.setCityId(review.getCityId());
        return reviewDto;
    }

    public List<CityReviewDTO> convertReviewsToReviewDTO(List<CityReview> reviews) {
        if (CollectionUtils.isEmpty(reviews)) {
            return new ArrayList<>();
        }
        return reviews.stream().map(temp -> convertReviewToReviewDTO(temp)).collect(Collectors.toList());
    }

}
