package airport.server.service.impl;

import airport.server.dto.FlightReviewDTO;
import airport.server.dto.ResultDTO;
import airport.server.enums.ResponseStatus;
import airport.server.model.FlightVO;
import airport.server.repository.AirportRepository;
import airport.server.repository.RouteRepository;
import airport.server.service.RouteService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author natalija
 */
@Service
@Slf4j
public class RouteServiceImpl implements RouteService {

    RouteRepository routeRepository;
    AirportRepository airportRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, AirportRepository airportRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
    }

    public List<FlightVO> getAllRoutesDetailsForOneCity(Long sourceCityId) {
        return routeRepository.findAllRoutesDetailsForOneCity(sourceCityId);
    }


    //build path for:  given citySource - > cityDest;
    // this could be improvised by providing additional param: city/country you wouldn't like to visit
    //problem is that one city is connected to many others cities/airports so seaarch last long
    //example which doesn't return many
    public void buildPath(List<Path> globalRoutes, Path currentLine, Long sourceId, Long finalDestinationId) {
        Path currentNodePath = Path.prepareCopy(currentLine);
        //find all stops for current stop:  source
        List<FlightVO> possibleRoutes = getAllRoutesDetailsForOneCity(sourceId);

        if (CollectionUtils.isNotEmpty(possibleRoutes)) {
            for (int i = 0; i < possibleRoutes.size(); i++) {
                FlightVO finalRouteCandidate = possibleRoutes.get(i);
                //set max stopping points, otherwise it would last many hours
                if (!currentLine.cityVisited(finalDestinationId) && !currentLine.cityVisited(finalRouteCandidate.getCityDestId())) {

                    //found final point
                    if (Objects.equals(finalDestinationId, finalRouteCandidate.getCityDestId())) {
                        currentNodePath.addStopPoint(finalRouteCandidate);
                        log.info("*********************");
                        log.info("Current path built with stopping points : {}", currentNodePath);
                        log.info("_____________________");
                        globalRoutes.add(currentNodePath);
                        currentNodePath = Path.prepareCopy(currentLine);


                        log.info("Found routes in total  : {}", globalRoutes);
                        log.info("_____________________");
                        continue;
                    }

                    List<FlightVO> travelChoices = getAllRoutesDetailsForOneCity(finalRouteCandidate.getCityDestId());
                    if (CollectionUtils.isEmpty(travelChoices)) {
                        //not found final point, and no more source points
                        currentNodePath = Path.prepareCopy(currentLine);
                        continue;
                    }

                    if (currentNodePath.stopPoints.size() < 2) {
                        currentNodePath.addStopPoint(finalRouteCandidate);
                        //any connection to current airport is found, so : do the repeat; recursion
                        buildPath(globalRoutes, currentNodePath, finalRouteCandidate.getCityDestId(), finalDestinationId);
                    } else {
                        currentNodePath = Path.prepareCopy(currentLine);
                    }
                }
            }
        }
    }

    /**
     * @param sourceId           as start city
     * @param finalDestinationId
     * @return
     */
    @Override
    public List<Path> getAvailableRoutesForSourceCity(Long sourceId, Long finalDestinationId) {
        List<Path> result = new ArrayList<>();
        log.info("SourceId : {}", sourceId);
        log.info("finalDestinationId : {}", finalDestinationId);
        buildPath(result, new Path(), sourceId, finalDestinationId);
        return result;
    }

    /**
     * Finds the cheapest route from one city to another as result dto.
     *
     * @param sourceId           as start city
     * @param finalDestinationId as  destination city
     * @return the cheapest route with other routes details.
     */
    @Override
    public ResultDTO getCheapestRouteDetails(Long sourceId, Long finalDestinationId) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            List<Path> routes = getAvailableRoutesForSourceCity(sourceId, finalDestinationId);
            FlightReviewDTO reviewDTO = new FlightReviewDTO();
            if (CollectionUtils.isNotEmpty(routes)) {
                reviewDTO.setAllPossibleRoutes(routes);
                Path cheapestRoute = findCheapestRoute(routes);
                reviewDTO.setCheapestRoute(cheapestRoute.getStopPoints());
                reviewDTO.setCheapestAmount(cheapestRoute.getTotalPrice());
                resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
                resultDTO.setData(reviewDTO);
            } else {
                throw new Exception(StringUtils.join("Not found any matching result. for source : ", sourceId, ", and destination: ", finalDestinationId));
            }
        } catch (Exception e) {
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            resultDTO.setErrorMessage(StringUtils.join("Not possible to resolve cheapest route.", e.getMessage()));
            log.error("Not possible to resolve cheapest route. {}", e.getMessage());
        }

        return resultDTO;
    }

    /**
     * Finds the cheapest route from all routes.
     *
     * @param routes all possible routes built of flights
     * @return the cheapest route details.
     */
    public Path findCheapestRoute(List<Path> routes) {
        if (CollectionUtils.isNotEmpty(routes)) {
            Path cheapest = routes.get(0);

            for (Path flightVO : routes) {
                cheapest = cheapest.totalPrice.compareTo(flightVO.totalPrice) > 0 ? flightVO : cheapest;
            }
            return cheapest;
        }
        return new Path();
    }


    /**
     * Helper class. Handles routes from one to another city, contains all stop points for the route.
     */
    @Data
    public static class Path implements Serializable {
        public Path() {
        }

        /**
         * Create object copy.
         *
         * @param p current path details.
         * @return deep copy.
         */
        public static Path prepareCopy(Path p) {
            Path pathCopy = new Path();
            if (CollectionUtils.isNotEmpty(p.stopPoints)) {
                for (FlightVO stopPoint : p.stopPoints) {
                    pathCopy.addStopPoint(new FlightVO(stopPoint));
                }
            }
            return pathCopy;
        }

        /**
         * Constructor.
         *
         * @param stopPoints as flights.
         */
        public Path(List<FlightVO> stopPoints) {
            this.stopPoints = stopPoints;
        }

        /**
         * All routes. Built from : start - end nodes.
         */
        private List<FlightVO> stopPoints = new ArrayList<>();

        /**
         * Total price of the flight.
         */
        private BigDecimal totalPrice = new BigDecimal(0);

        /**
         * Adds new route.
         *
         * @param flightVO as Flight from one to another airport.
         */
        public void addStopPoint(FlightVO flightVO) {
            if (!stopPoints.contains(flightVO)) {
                stopPoints.add(flightVO);
                flightVO.setRouteOrder(stopPoints.size());
                totalPrice = totalPrice.add(flightVO.getPrice());
            }
        }

        /**
         * Checks whether the city is already visited.
         *
         * @param cityId
         * @return visited/not visited.
         */
        public boolean cityVisited(Long cityId) {
            Optional<FlightVO> sourceStream = stopPoints.stream().filter(point -> cityId == point.getCitySourceId()).findAny();
            Optional<FlightVO> destStream = stopPoints.stream().filter(point -> cityId == point.getCityDestId()).findAny();

//            Predicate check = o -> ((Optional<Object>) o).isPresent();
//            return check.evaluate(sourceStream) || check.evaluate(destStream);

            return sourceStream.isPresent() || destStream.isPresent();
        }

    }
}
