package com.tsystems.controller.rest;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.StationDTO;
import com.tsystems.service.api.DirectionService;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StationRestController {
    private StationService stationService;
    private ScheduleService scheduleService;

    @Autowired
    public StationRestController(StationService stationService, ScheduleService scheduleService) {
        this.stationService = stationService;
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(StationRestController.class);

    @GetMapping("/station/get/list/title")
    public String getAllStations() {
        log.info("REST: Getting all stations [names]");
        List<StationDTO> allStations = stationService.getAll();
        List<String> stationNames = new ArrayList<>();
        allStations.forEach(station -> stationNames.add(station.getName()));
        return ConverterUtil.parseJson(stationNames);
    }

    @GetMapping("/route/get/station/list/")
    public String getAllRouteStations(@RequestParam("routeId") Integer routeId) {
        log.info("REST: Getting all stations [StationDTO] for Route with ID: " + routeId);
        return ConverterUtil.parseJson(stationService.getRouteStations(routeId));
    }

    @GetMapping("/schedule/get/")
    public String getScheduleByStationNameForToday(@RequestParam("stationName") String stationName) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForDate(stationName, null);
        log.info("REST: Getting schedule for station: [" + stationName + "] for TODAY\nList<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

    @GetMapping("/schedule/get-by-date/")
    public String getScheduleByStationNameForDate(
            @RequestParam("stationName") String stationName,
            @RequestParam("desiredDate") String desiredDate) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForDate(stationName, desiredDate);
        log.info("REST: Getting schedule for station: [" + stationName + "] for DATE: " + desiredDate + "\nList<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

}
