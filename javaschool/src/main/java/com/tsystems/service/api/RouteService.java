package com.tsystems.service.api;

import com.tsystems.dto.RouteDTO;

import java.util.List;

public interface RouteService {

    /**
     * Creates route with specified stationSequence
     *
     * @param stationSequence
     */
    void createRoute(String stationSequence);

    /**
     * Edits specified route with specified stationSequence
     *
     * @param routeId routeId
     * @param stationSequence stationSequence
     */
    void editRoute(Integer routeId, String stationSequence);

    /**
     * Get all first and last routeRows
     *
     * @return list of routes
     */
    List<RouteDTO> getFirstAndLastRoutesRows();

    /**
     * Finds and returns route by routeId
     *
     * @param id routeId
     * @return list of route rows
     */
    List<RouteDTO> findRouteByRouteId(Integer id);

    /**
     * Returns route distance by id
     *
     * @param id routeId
     * @return distance
     */
    double getRouteDistanceById(Integer id);

    /**
     * Deletes route by it's id
     *
     * @param id routeId
     */
    void deleteRoute(Integer id);

    /**
     * Returns last routeId
     *
     * @return last route Id
     */
    Integer getLastRouteId();

}
