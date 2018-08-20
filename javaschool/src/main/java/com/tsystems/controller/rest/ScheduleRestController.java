package com.tsystems.controller.rest;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Dispatches the schedule related rest-queries
 */
@RestController
public class ScheduleRestController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(ScheduleRestController.class);

    /**
     * Returns list of schedules for specified tripId
     *
     * @param tripId tripId
     * @return list of schedules in JSON
     */
    @GetMapping("/route/schedule/get/")
    public String getSchedulesByTripId(@RequestParam("tripId") Integer tripId) {
        return ConverterUtil.parseJson(scheduleService.getSchedulesByTripId(tripId));
    }

    /**
     * Returns list of schedules for specified stationName for TODAY
     *
     * @param stationName stationName
     * @return list of schedules in JSON
     */
    @GetMapping("/schedule/get/")
    public String getScheduleByStationNameForToday(@RequestParam("stationName") String stationName) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForDate(stationName, null);
        log.info("REST: Getting schedule for station: [" + stationName + "] for TODAY\nList<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

    /**
     *  Returns list of schedules for specified stationName for specified date
     *
     * @param stationName stationName
     * @param desiredDate desiredDate
     * @return list of schedules in JSON
     */
    @GetMapping("/schedule/get-by-date/")
    public String getScheduleByStationNameForDate(
            @RequestParam("stationName") String stationName,
            @RequestParam("desiredDate") String desiredDate) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForDate(stationName, desiredDate);
        log.info("REST: Getting schedule for station: [" + stationName + "] for DATE: " + desiredDate + "\nList<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

    /**
     * Allows to set time late of specified scheduleId
     *
     * @param scheduleId scheduleId
     * @param time_late time_late
     */
    @PostMapping("/admin/schedule/late")
    public void editLateStationSchedule(
            @RequestParam("scheduleId") Integer scheduleId,
            @RequestParam("time_late") String time_late) {
        scheduleService.editLateStationSchedule(scheduleId, time_late);
    }
}
