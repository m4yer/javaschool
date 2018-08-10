package com.tsystems.dao.api;

import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;

import java.util.List;

public interface DirectionDAO extends GenericDAO<Direction, Integer> {

    List<Station> getDepartures(String stationName);

    void removeDirectionBetweenStations(String stationFromName, String stationToName);

}
