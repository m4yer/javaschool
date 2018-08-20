package com.tsystems.service.api;

import com.tsystems.dto.StationDTO;
import com.tsystems.entity.Station;

import java.util.List;

public interface StationService {

    /**
     * Method for creating new station
     *
     * @param station station
     */
    void addStation(Station station);

    /**
     * Method for getting station by its id
     *
     * @param id stationId
     * @return station found by id
     */
    Station findById(Integer id);

    /**
     * Method for getting station by its name
     *
     * @param name stationName
     * @return station found by name
     */
    Station findByName(String name);

    /**
     * Method for deleting stations
     *
     * @param station station
     */
    void deleteStation(Station station);

    /**
     * Method for getting all stations
     *
     * @return list of stations
     */
    List<StationDTO> getAll();

    /**
     * Method for getting routeStations
     *
     * @param routeId routeId
     * @return list of stations
     */
    List<StationDTO> getRouteStations(Integer routeId);

}
