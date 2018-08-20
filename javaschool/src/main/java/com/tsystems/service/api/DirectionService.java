package com.tsystems.service.api;

import com.tsystems.dto.CoordinateDTO;

import java.util.List;

public interface DirectionService {

    /**
     * Gets departure station names of specified station
     *
     * @param stationName stationName
     * @return list string of departure stations
     */
    List<String> getDepartureStationNames(String stationName);

    /**
     * Gets departure station coordinates of specified station
     *
     * @param stationName stationName
     * @return list coordinates of departures from station
     */
    List<CoordinateDTO> getDepartureStationCoordinates(String stationName);

    /**
     * Method for removing direction between two stations
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @return true if direction was removed, false otherwise
     */
    boolean removeDirectionBetweenStations(String stationFromName, String stationToName);

    /**
     * Method for adding direction between two stations
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     */
    void addDirectionBetweenStations(String stationFromName, String stationToName);

}
