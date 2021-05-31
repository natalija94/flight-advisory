package airport.server.model;

import airport.server.enums.DST;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author natalija
 */
@Data
@Table
@Entity
@SequenceGenerator(name = "airport_seq", allocationSize = 1000)
public class Airport {
    /**
     * Airport id.
     */
    @Id
    @GeneratedValue(generator = "airport_seq", strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    private Long airportId;

    @Column(nullable = false)
    private String name;

    @Column
    private Long cityId;

    @Column(length = 3)
    private String iataCode;

    @Column(length = 4)
    private String icaoCode;

    @Column(precision = 6, scale = 2)
    private BigDecimal latitude;

    @Column(precision = 6, scale = 2)
    private BigDecimal longitude;

    @Column
    private int altitude;

    @Column(scale = 3)
    private double timezone;

    @Column
    @Enumerated(EnumType.STRING)
    private DST dst;

    @Column
    public String tzOlsen;

    @Column
    public String type;

    @Column
    public String source;
}
