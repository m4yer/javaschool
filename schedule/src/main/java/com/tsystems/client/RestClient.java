package com.tsystems.client;

import com.tsystems.dto.ScheduleDTO;

import java.io.IOException;
import java.util.List;

public interface RestClient {


    List<String> getAllStations() throws IOException;
    List<ScheduleDTO> getScheduleForToday(String stationName) throws IOException;

}
