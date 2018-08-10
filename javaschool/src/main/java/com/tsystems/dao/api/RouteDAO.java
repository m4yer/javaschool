package com.tsystems.dao.api;

import com.tsystems.entity.Route;
import com.tsystems.entity.Station;

import java.util.List;

public interface RouteDAO extends GenericDAO<Route, Integer> {

    List<Integer> getSingleRoutesId();

    List<Route> getFirstAndLastRouteRows(Integer routeId);

    List<Route> findRouteByRouteId(Integer id);

    double getRouteDistanceByRouteId(Integer id);

    void deleteRoute(Integer id);

    void updateRouteRow(Integer routeId, Station station, Integer stationOrder);

    Integer getLastRouteId();

}
