package airport.server.controller;

import airport.server.dto.CityDTO;
import airport.server.dto.ResultDTO;
import airport.server.service.AdministrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author natalija
 */
@RestController
@Slf4j
@RequestMapping("/administrator")
public class AdministratorFacade {

    private AdministrationService service;

    @Autowired
    public AdministratorFacade(AdministrationService service) {
        this.service = service;
    }

    @PostMapping("/save-city")
    public ResultDTO saveCity(@RequestBody CityDTO cityDTO) {
        return service.saveCity(cityDTO);
    }

    @PostMapping("/init-dictionaries")
    public ResultDTO initDictionaries() {
        return service.importDependentDictionaries();
    }
}
