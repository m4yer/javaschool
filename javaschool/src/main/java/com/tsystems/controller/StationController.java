package com.tsystems.controller;

import com.tsystems.entity.Station;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class StationController {
    private StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    private static final Logger log = Logger.getLogger(StationController.class);

    @GetMapping("/station/list")
    public String stationsPage() {
        return "admin/station_list";
    }

    @GetMapping("/station/list/get")
    public @ResponseBody String getAllStationsJSON() {
        return ConverterUtil.parseJson(stationService.getAll());
    }

    @GetMapping("/station/add")
    public String addStationPage(@ModelAttribute("station") Station station) {
        return "admin/station_add";
    }

    @PostMapping("/station/add")
    public String addStationPost(@ModelAttribute("station") Station station, BindingResult result) {
        stationService.addStation(station);
        log.info("Station was successfully added!");
        return "redirect:/admin/station/list";
    }

    @GetMapping("/station/delete/{id}")
    public String deleteStation(@PathVariable("id") Integer id) {
        Station station = stationService.findById(id);
        stationService.deleteStation(station);
        return "redirect:/admin/station/list";
    }


}
