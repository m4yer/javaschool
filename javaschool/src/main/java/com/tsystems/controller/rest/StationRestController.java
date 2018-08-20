package com.tsystems.controller.rest;

import com.tsystems.dto.StationDTO;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Dispatches the station related rest-queries
 */
@RestController
public class StationRestController {
    private StationService stationService;

    @Autowired
    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }

    private static final Logger log = Logger.getLogger(StationRestController.class);

    /**
     * Returns list string of all station names
     *
     * @return list of all station names in JSON
     */
    @GetMapping("/station/get/list/title")
    public String getAllStations() {
        log.info("REST: Getting all stations [names]");
        List<StationDTO> allStations = stationService.getAll();
        List<String> stationNames = new ArrayList<>();
        allStations.forEach(station -> stationNames.add(station.getName()));
        return ConverterUtil.parseJson(stationNames);
    }

    /**
     * Return list of all stations for specified routeId
     *
     * @param routeId routeId
     * @return list of all stations in JSON
     */
    @GetMapping("/route/get/station/list/")
    public String getAllRouteStations(@RequestParam("routeId") Integer routeId) {
        log.info("REST: Getting all stations [StationDTO] for Route with ID: " + routeId);
        return ConverterUtil.parseJson(stationService.getRouteStations(routeId));
    }

}
