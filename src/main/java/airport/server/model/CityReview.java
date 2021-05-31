package airport.server.model;

import airport.server.util.DateUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author natalija
 */
@Data
@Entity
@Table
@SequenceGenerator(name = "review_seq", allocationSize = 1000)
public class CityReview {
    /**
     * Comment id.
     */
    @Id
    @GeneratedValue(generator = "review_seq", strategy = GenerationType.TABLE)
    private Long id;

    /**
     * City.
     */
    @Column
    private Long cityId;

    /**
     * City description.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    @Column
    @Setter(value = AccessLevel.NONE)
    LocalDateTime created = DateUtil.convertToLocalDateTimeNow();


    @Column
    LocalDateTime updated;
}
