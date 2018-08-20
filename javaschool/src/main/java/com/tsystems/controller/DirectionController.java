package com.tsystems.controller;

import com.tsystems.service.api.DirectionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Dispatches queries related to directions
 */
@Controller
@RequestMapping("/admin")
public class DirectionController {
    private DirectionService directionService;

    @Autowired
    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    private static final Logger log = Logger.getLogger(DirectionController.class);

    /**
     * Returns the rail roads page.
     *
     * @return admin/rail_map.jsp
     */
    @GetMapping("/directions/map")
    public String directionsMapPage() {
        return "admin/rails_map";
    }

    /**
     * Allows to add direction between to stations
     *
     * @param stationFromName stationFrom
     * @param stationToName stationTo
     */
    @PostMapping("/direction/add")
    public @ResponseBody
    void addDirectionBetweenStations(
            @RequestParam("stationFromName") String stationFromName,
            @RequestParam("stationToName") String stationToName) {
        log.info("Add new direction between: " + stationFromName + " and " + stationToName + ". [" + stationFromName + " -> " + stationToName + "]");
        directionService.addDirectionBetweenStations(stationFromName, stationToName);
    }

    /**
     * Allows to remove direction between to stations
     *
     * @param stationFromName stationFrom
     * @param stationToName stationTo
     */
    @PostMapping("/direction/remove")
    public @ResponseBody
    boolean removeDirectionBetweenStations(
            @RequestParam("stationFromName") String stationFromName,
            @RequestParam("stationToName") String stationToName) {
        log.info("Remove direction between: " + stationFromName + " and " + stationToName + ". [" + stationFromName + " -> " + stationToName + "]");
        return directionService.removeDirectionBetweenStations(stationFromName, stationToName);
    }

}
