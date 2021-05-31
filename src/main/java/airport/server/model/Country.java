package airport.server.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author natalija
 */
@Data
@Table
@Entity
@SequenceGenerator(name = "country_seq", allocationSize = 1000)
public class Country {
    /**
     * Country id.
     */
    @Id
    @Column(name="country_id")
    @GeneratedValue(generator = "country_seq", strategy = GenerationType.TABLE)
    private Long id;

    /**
     * City name.
     */
    @Column
    private String name;
}
