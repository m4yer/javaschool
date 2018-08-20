package com.tsystems.dao.api;

import com.tsystems.entity.Route;
import com.tsystems.entity.Station;

import java.util.List;

/**
 * CRUD and specific operations
 */
public interface RouteDAO extends GenericDAO<Route, Integer> {

    /**
     * Get all route ids
     *
     * @return integer list of all route ids
     */
    List<Integer> getSingleRoutesId();

    /**
     * Gets first and last route rows for specified routeId
     *
     * @param routeId routeId
     * @return list of routes
     */
    List<Route> getFirstAndLastRouteRows(Integer routeId);

    /**
     * Finds and gets route by routeId
     *
     * @param id routeId
     * @return returns list of route rows
     */
    List<Route> findRouteByRouteId(Integer id);

    /**
     * Gets route distance by it's id
     *
     * @param id routeId
     * @return distance
     */
    double getRouteDistanceByRouteId(Integer id);

    /**
     * Deletes route by id
     *
     * @param id routeId
     */
    void deleteRoute(Integer id);

    /**
     * Updates route's station order by routeId
     *
     * @param routeId routeId
     * @param station station
     * @param stationOrder stationOrder
     */
    void updateRouteRow(Integer routeId, Station station, Integer stationOrder);

    /**
     * Gets last routeId
     *
     * @return last routeId
     */
    Integer getLastRouteId();

}
