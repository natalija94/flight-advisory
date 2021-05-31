package airport.server.model;

import lombok.Data;
import javax.persistence.*;

/**
 * @author natalija
 */
@Data
@Entity
@Table
public class CityComment {
    /**
     * Comment id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * City description.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * City.
     */
    @Column
    private Long cityId;
}
