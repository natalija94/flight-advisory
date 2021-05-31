package airport.server.model;

import lombok.Data;

/**
 * @author natalija
 */
@Data
public class CityReviewVO {

    private Long cityId;
    private Long reviewId;
    private String reviewContent;

    public CityReviewVO(CityReview review) {
        this.cityId = review.getId();
        this.reviewId = review.getId();
        this.reviewContent = review.getDescription();
    }
}
