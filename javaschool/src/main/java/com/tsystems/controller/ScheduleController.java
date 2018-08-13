package com.tsystems.controller;

import com.tsystems.service.api.ScheduleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScheduleController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(ScheduleController.class);

    @GetMapping("/user/schedule")
    public String browseStationSchedule() {
        return "user/station_schedule";
    }

    @PostMapping("/admin/schedule/late")
    public void editLateStationSchedule(
            @RequestParam("scheduleId") Integer scheduleId,
            @RequestParam("time_late") String time_late) {
        scheduleService.editLateStationSchedule(scheduleId, time_late);
    }

}
