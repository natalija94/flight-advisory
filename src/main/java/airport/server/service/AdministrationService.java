package airport.server.service;

import airport.server.dto.CityDTO;
import airport.server.dto.ResultDTO;
import airport.server.enums.ResponseStatus;
import org.apache.commons.lang3.StringUtils;

/**
 * @author natalija
 */
public interface AdministrationService {
    ResultDTO saveCity(CityDTO cityDTO);

    void importAirports() throws Exception;

    void importRoutes() throws Exception;

    default ResultDTO importDependentDictionaries() {
        ResultDTO resultDTO = new ResultDTO();
        try {
            importAirports();
            importRoutes();
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            resultDTO.setErrorMessage(StringUtils.join("Not possible to import dictionaries. ERROR: ",e.getMessage()));
        }
        return resultDTO;
    }
}
