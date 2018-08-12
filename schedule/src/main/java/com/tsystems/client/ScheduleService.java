package com.tsystems.client;

import com.tsystems.dto.ScheduleDTO;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

public interface ScheduleService {

    List<String> getAllStations() throws IOException;

    List<ScheduleDTO> getScheduleForToday(String stationName) throws IOException;

}
