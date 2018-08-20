package com.tsystems.controller;

import com.tsystems.entity.Station;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Dispatches queries related to stations
 */
@Controller
@RequestMapping("/admin")
public class StationController {
    private StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    private static final Logger log = Logger.getLogger(StationController.class);

    /**
     * Returns list of stations page
     *
     * @return admin/station_list.jsp
     */
    @GetMapping("/station/list")
    public String stationsPage() {
        return "admin/station_list";
    }

    /**
     * Returns all stations in JSON
     *
     * @return list of stations in JSON
     */
    @GetMapping("/station/list/get")
    public @ResponseBody
    String getAllStationsJSON() {
        return ConverterUtil.parseJson(stationService.getAll());
    }

    /**
     * Returns add station page
     *
     * @param station station
     * @return admin/station_add.jsp
     */
    @GetMapping("/station/add")
    public String addStationPage(@ModelAttribute("station") Station station) {
        return "admin/station_add";
    }

    /**
     * Processes adding new station
     *
     * @param station station
     * @param result result
     * @return admin/station_list.jsp
     */
    @PostMapping("/station/add")
    public String addStationPost(@ModelAttribute("station") Station station, BindingResult result) {
        stationService.addStation(station);
        log.info("Station was successfully added!");
        return "redirect:/admin/station/list";
    }

}
