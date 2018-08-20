package com.tsystems.client;

import com.tsystems.dto.ScheduleDTO;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

/**
 * Central bean that communicates with 1st app
 */
public interface ScheduleService {

    /**
     * Rest for getting all stations from 1st applications
     *
     * @return List of station names
     * @throws IOException when code response != 200
     */
    List<String> getAllStations() throws IOException;

    /**
     * Method for getting schedule for station for today
     *
     * @param stationName stationName
     * @return list of schedules
     * @throws IOException when code response != 200
     */
    List<ScheduleDTO> getScheduleForToday(String stationName) throws IOException;

}
