package com.tsystems.controller;

import com.tsystems.client.ScheduleService;
import com.tsystems.dto.ScheduleDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.event.Observes;
import javax.faces.bean.ViewScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("userSchedule")
@Stateful
public class UserSchedule implements Serializable {

    private String stationName;
    private List<ScheduleDTO> schedules;
    private int initCounter = 0;

    @EJB
    ScheduleService scheduleService;

    @Inject
    @Push(channel = "scheduleChannel")
    private PushContext pushContext;

    @PostConstruct
    private void init() {
        try {
            if (initCounter < 3) {
                System.out.println("GETTING SCHEDULE FOR: " + stationName);
                schedules = scheduleService.getScheduleForToday("Moscow");
                System.out.println("INIT SUCCESSFULLY");
                System.out.println("schedules.size{}: " + schedules.size());
                initCounter++;
            }
        } catch (IOException ex) {
            System.err.println("ERROR!");
            System.err.println(ex.getMessage());
        }
    }

    public void onNewSchedule(@Observes ScheduleDTO newSchedule) {
        System.out.println("onNewSchedule triggers! @Observer works!");
        pushContext.send("updateSchedule");
    }

    public List<ScheduleDTO> getSchedules() {
        System.out.println("getSchedules(), schedules.size(): " + schedules.size());
        return schedules;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
