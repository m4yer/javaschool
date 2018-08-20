package com.tsystems.dao.api;

import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;

import java.util.List;

/**
 * CRUD and specific operations
 */
public interface DirectionDAO extends GenericDAO<Direction, Integer> {

    /**
     * Get all stations which departing from specified Station
     *
     * @param stationName stationName
     * @return list of departing stations
     */
    List<Station> getDepartures(String stationName);

    /**
     * Method for removing directions between two stations
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     */
    void removeDirectionBetweenStations(String stationFromName, String stationToName);

}
