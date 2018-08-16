package com.tsystems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.client.ScheduleBean;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named("messenger")
@Stateless
public class UpdateControllerBean {

    @Inject
    private Event<ScheduleDTO> scheduleEvent;
    @Inject
    private ScheduleBean scheduleBean;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void onNewSchedule(String newScheduleJson) {
        // JSON -> Object [ScheduleDTO]
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        try {
            ScheduleDTO newScheduleDto = objectMapper.readValue(newScheduleJson, ScheduleDTO.class);
            System.out.println("UpdateControllerBean invoked onNewSchedule()");
            String stationName = newScheduleDto.getStationDto().getName();
            List<ScheduleDTO> stationSchedule = scheduleBean.getSchedules().get(stationName);
            for (ScheduleDTO schedule : stationSchedule) {
                if (newScheduleDto.getId().equals(schedule.getId())) {
                    stationSchedule.set(stationSchedule.indexOf(schedule), newScheduleDto);
                    scheduleBean.setScheduleListForMap(stationName, stationSchedule);
                    return;
                }
                if (newScheduleDto.getId() < schedule.getId()) {
                    stationSchedule.add(stationSchedule.indexOf(schedule), newScheduleDto);
                    scheduleBean.setScheduleListForMap(stationName, stationSchedule);
                    return;
                }
            }
            stationSchedule.add(newScheduleDto);
            scheduleBean.setScheduleListForMap(stationName, stationSchedule);
            scheduleEvent.fire(newScheduleDto);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

}
