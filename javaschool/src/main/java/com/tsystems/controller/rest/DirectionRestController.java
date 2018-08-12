package com.tsystems.controller.rest;

import com.tsystems.service.api.DirectionService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectionRestController {
    private DirectionService directionService;

    @Autowired
    public DirectionRestController(DirectionService directionService) {
        this.directionService = directionService;
    }

    private static final Logger log = Logger.getLogger(DirectionRestController.class);

    @GetMapping("/station/get/departure/stations")
    public String getDepartureStationNames(@RequestParam("stationName") String stationName) {
        log.info("REST: Getting all directions [station names] from station: " + stationName);
        return ConverterUtil.parseJson(directionService.getDepartureStationNames(stationName));
    }

    @GetMapping("/station/get/departure/coordinates")
    public String getDepartureStationCoordinates(@RequestParam("stationName") String stationName) {
        log.info("REST: Getting all directions [coordinates] from station: " + stationName);
        return ConverterUtil.parseJson(directionService.getDepartureStationCoordinates(stationName));
    }

}
