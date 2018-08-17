package com.tsystems.controller.rest;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleRestController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(ScheduleRestController.class);

    @GetMapping("/route/schedule/get/")
    public String getSchedulesByTripId(@RequestParam("tripId") Integer tripId) {
        return ConverterUtil.parseJson(scheduleService.getSchedulesByTripId(tripId));
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

    @PostMapping("/admin/schedule/late")
    public void editLateStationSchedule(
            @RequestParam("scheduleId") Integer scheduleId,
            @RequestParam("time_late") String time_late) {
        scheduleService.editLateStationSchedule(scheduleId, time_late);
    }
}
