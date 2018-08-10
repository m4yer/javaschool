package com.tsystems.service.api;

import com.tsystems.dto.CoordinateDTO;

import java.util.List;

public interface DirectionService {

    List<String> getDepartureStationNames(String stationName);

    List<CoordinateDTO> getDepartureStationCoordinates(String stationName);

    void removeDirectionBetweenStations(String stationFromName, String stationToName);

    void addDirectionBetweenStations(String stationFromName, String stationToName);

}
