package airport.server.model;

/**
 * @author natalija
 */

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author natalija
 */
@Data
@Table
@Entity
@SequenceGenerator(name = "route_seq", allocationSize = 1000)
public class Route {
    /**
     * Route id.
     */
    @Id
    @GeneratedValue(generator = "route_seq", strategy = GenerationType.TABLE)
    private Long id;

    @Column
    private String airlineId;

    @Column
    private String airline;

    @Column
    private Long airportSourceId;

    @Column
    private Long airportDestinationId;

    @Column
    private String codeShare = "N";

    @Column
    private int stops;

    @Column
    private String equipment;

    @Column(scale = 2)
    private BigDecimal price;
}
