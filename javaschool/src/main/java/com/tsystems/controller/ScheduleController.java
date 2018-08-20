package com.tsystems.controller;

import com.tsystems.service.api.ScheduleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dispatches queries related to schedule
 */
@Controller
public class ScheduleController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(ScheduleController.class);

    /**
     * Returns schedule stations
     *
     * @return user/station_schedule.jsp
     */
    @GetMapping("/user/schedule")
    public String scheduleStationPage() {
        return "user/station_schedule";
    }

}
