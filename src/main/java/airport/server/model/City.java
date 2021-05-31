package airport.server.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author natalija
 */
@Data
@Table
@Entity
@SequenceGenerator(name = "CITY_SEQ", allocationSize = 1000)
public class City {
    /**
     * City id.
     */
    @Id
    @Column(name = "city_id")
    @GeneratedValue(generator = "CITY_SEQ", strategy = GenerationType.TABLE)
    private Long id;

    /**
     * Country name.
     */
    @Column
    private Long countryId;

    /**
     * City name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * City description.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description = "IMPORTED FROM FILE.";
}
