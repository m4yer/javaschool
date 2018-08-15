package com.tsystems.client;

import com.tsystems.dto.ScheduleDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Named("scheduleBean")
@ApplicationScoped
public class ScheduleBean {

    @Inject
    private ScheduleService scheduleService;

    private List<String> stations = new ArrayList<>();
    private HashMap<String, List<ScheduleDTO>> schedules = new HashMap<>();

    @PostConstruct
    public void init() {
        System.out.println("init() ScheduleBean was invoked!");
        try {
            stations = scheduleService.getAllStations();
        } catch (IOException e) {
            System.out.println("FAIL WHILE TRYING TO FETCH STATIONS");
            e.printStackTrace();
        }
        stations.forEach(station -> {
            try {
                List<ScheduleDTO> stationSchedules = scheduleService.getScheduleForToday(station);
                if (stationSchedules.size() > 0) schedules.put(station, stationSchedules);
            } catch (IOException e) {
                System.out.println("SOME TROUBLES WHILE TRYING TO GET SCHEDULE FOR: " + station);
            }
        });
        System.out.println("After fetсhing data list<ScheduleDto>: " + schedules);
    }

    public List<ScheduleDTO> getStationSchedule(String stationName) {
        return schedules.get(stationName);
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public HashMap<String, List<ScheduleDTO>> getSchedules() {
        return schedules;
    }

    public void setSchedules(HashMap<String, List<ScheduleDTO>> schedules) {
        this.schedules = schedules;
    }

    public void setScheduleListForMap(String key, List<ScheduleDTO> scheduleList) {
        System.out.println("setScheduleListForMap() invoked. Schedule was updated.");
        schedules.remove(key);
        schedules.put(key, scheduleList);
    }
}
