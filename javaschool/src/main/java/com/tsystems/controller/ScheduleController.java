package com.tsystems.controller;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.jms.SimpleMessageSender;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ScheduleController {
    @Autowired
    StationService stationService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SimpleMessageSender messageSender;

    private static final Logger log = Logger.getLogger(ScheduleController.class);

    @GetMapping("/user/schedule")
    public String browseStationSchedule() {
        return "user/station_schedule";
    }

    @GetMapping("/schedule/get/")
    public @ResponseBody
    String getScheduleByStationNameForToday(@RequestParam("stationName") String stationName) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByStationNameForToday(stationName);
        System.out.println("send schedules to second EJB application!!!!");
        System.out.println("List<ScheduleDTO>.size(): " + schedules.size());
        return ConverterUtil.parseJson(schedules);
    }

    @GetMapping("/admin/schedule/get/")
    public @ResponseBody
    String getSchedulesByTripId(@RequestParam("tripId") Integer tripId) {
        return ConverterUtil.parseJson(scheduleService.getSchedulesByTripId(tripId));
    }

    @PostMapping("/admin/schedule/late")
    public @ResponseBody
    void editLateStationSchedule(@RequestParam("scheduleId") Integer scheduleId,
            @RequestParam("time_late") String time_late) {
        scheduleService.editLateStationSchedule(scheduleId, time_late);
    }

}
