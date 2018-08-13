package com.tsystems.controller.rest;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.utils.ConverterUtil;
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

    @GetMapping("/schedule/get/")
    public String getScheduleByStationNameForToday(@RequestParam("stationName") String stationName) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForToday(stationName);
        System.out.println("send schedules to second EJB application!!!!");
        System.out.println("List<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

    @GetMapping("/user/schedule/get/")
    public String getSchedulesByTripId(@RequestParam("tripId") Integer tripId) {
        return ConverterUtil.parseJson(scheduleService.getSchedulesByTripId(tripId));
    }

    @PostMapping("/admin/schedule/late")
    public void editLateStationSchedule(
            @RequestParam("scheduleId") Integer scheduleId,
            @RequestParam("time_late") String time_late) {
        scheduleService.editLateStationSchedule(scheduleId, time_late);
    }
}
