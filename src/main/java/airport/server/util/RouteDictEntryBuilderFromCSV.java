package airport.server.util;

import airport.server.model.Route;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author natalija
 */
public class RouteDictEntryBuilderFromCSV {
    private RouteDictEntryBuilderFromCSV() {
    }

    private static Logger LOG = LoggerFactory.getLogger(AirportDictEntryBuilderFromCSV.class);

    public static Route buildFlightRoute(String[] csvRow) {
        if (Arrays.isNullOrEmpty(csvRow)) {
            return null;
        }

        Route route = new Route();
        route.setAirline(csvRow[0]);
        route.setAirlineId(csvRow[1]);

        try {
            route.setAirportSourceId(Long.parseLong(csvRow[3]));
            route.setAirportDestinationId(Long.parseLong(csvRow[5]));
        } catch (Exception e){
            LOG.error("Not possible to parse route {}", csvRow);
            return null;
        }

        route.setCodeShare(StringUtils.isNotEmpty(csvRow[6]) ? csvRow[6] : "NO");
        try {
            route.setStops(Integer.parseInt(csvRow[7]));
        } catch (Exception e) {
            LOG.error("Not possible to resolve parse stops.");
        }

        route.setEquipment(csvRow[8]);

        try {
            route.setPrice(new BigDecimal(csvRow[9]));
        } catch (Exception e) {
            LOG.error("Not possible not resolve price value.");
        }

        return route;
    }

}
