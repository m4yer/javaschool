package com.tsystems.controller;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.TrainDTO;
import com.tsystems.dto.TripDTO;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.Instant;

@Named("messenger")
@Stateless
public class UpdateControllerBean {

    @Inject
    private Event<ScheduleDTO> scheduleEvent;

    public void onNewSchedule() {
        System.out.println("ON NEW SCHEDYLE TRIGGERED");
        ScheduleDTO newSchedule = new ScheduleDTO(99,
                new TripDTO(999, new TrainDTO(
                        999, "my_poezd", 180D, 66, 12
                ), 7, Instant.now(), true), Instant.now(), "late", Instant.now(), "0000");
        scheduleEvent.fire(newSchedule);
    }

}
