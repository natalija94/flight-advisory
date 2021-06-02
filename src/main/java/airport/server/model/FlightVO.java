package airport.server.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author natalija
 */
@Data
public class FlightVO {
    private Long routeId;

    private Long sourceAirportId;
    private String sourceAirportName;

    private Long destAirportId;
    private String destAirportName;

    private BigDecimal price;

    private String citySource;
    private String cityDest;

    private Long citySourceId;
    private Long cityDestId;

    private int routeOrder = 0;

    public FlightVO(Route route, Airport airportSource, Airport airportDest, City citySource, City cityDest){
        this.routeId = route.getId();
        this.price = route.getPrice();

        this.sourceAirportId = airportSource.getAirportId();
        this.sourceAirportName = airportSource.getName();

        this.destAirportId = airportDest.getAirportId();
        this.destAirportName = airportDest.getName();

        this.citySourceId = citySource.getId();
        this.cityDestId = cityDest.getId();
        this.citySource = citySource.getName();
        this.cityDest = cityDest.getName();
    }

    public FlightVO(FlightVO flightVO) {
        this.routeId = flightVO.getRouteId();
        this.price = flightVO.getPrice();

        this.sourceAirportId = flightVO.getSourceAirportId();
        this.sourceAirportName = flightVO.getSourceAirportName();

        this.destAirportId = flightVO.getDestAirportId();
        this.destAirportName = flightVO.getDestAirportName();

        this.citySourceId = flightVO.getCitySourceId();
        this.cityDestId = flightVO.getCityDestId();
        this.citySource = flightVO.getCitySource();
        this.cityDest = flightVO.getCityDest();
    }
}
