package airport.server.converter;

import airport.server.dto.CityReviewDTO;
import airport.server.model.City;
import airport.server.model.CityReview;
import airport.server.repository.CityCommentRepository;
import airport.server.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author natalija
 */
@Service
public class CityReviewConverter {

    private CityRepository cityRepository;
    private CityCommentRepository cityCommentRepository;

    public CityReviewConverter(CityRepository cityRepository, CityCommentRepository cityCommentRepository) {
        this.cityRepository = cityRepository;
        this.cityCommentRepository = cityCommentRepository;
    }

    public CityReview convertReviewDTOToReview(CityReviewDTO reviewDto) {
        if (reviewDto == null || reviewDto.getCityId() == null) {
            return null;
        }
        Optional<City> cityOptional = cityRepository.findById(reviewDto.getCityId());
        if (cityOptional == null || cityOptional.isEmpty()) {
            return null;
        }
        Long commentId = reviewDto.getReviewId();
        CityReview review;

        Function<CityReview, Void> setCommonData = new Function<CityReview, Void>() {
            @Override
            public Void apply(CityReview cityReview) {
                cityReview.setDescription(reviewDto.getReviewContent());
                cityReview.setCity(cityOptional.get());
                return null;
            }
        };

        if (commentId != null) {
            Optional<CityReview> reviewOptional = cityCommentRepository.findById(commentId);
            //is there in city review with this reviewId
            if (reviewOptional == null || reviewOptional.isEmpty()) {
                return null;
            }
            review = reviewOptional.get();
        } else {
            review = new CityReview();
        }
        review.setCity(cityOptional.get());
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
        reviewDto.setCityId(review.getCity().getId());
        return reviewDto;
    }

    public List<CityReviewDTO> convertReviewsToReviewDTO(List<CityReview> reviews) {
        if (CollectionUtils.isEmpty(reviews)) {
            return new ArrayList<>();
        }
        return reviews.stream().map(temp -> convertReviewToReviewDTO(temp)).collect(Collectors.toList());
    }

}
