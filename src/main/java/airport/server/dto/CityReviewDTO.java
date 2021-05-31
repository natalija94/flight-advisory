package airport.server.dto;

import lombok.Data;

/**
 * @author natalija
 */
@Data
public class CityReviewDTO {
    private Long cityId;
    private Long reviewId;
    private String reviewContent;

    public CityReviewDTO() {
    }
}
