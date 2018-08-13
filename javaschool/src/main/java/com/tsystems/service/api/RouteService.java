package com.tsystems.service.api;

import com.tsystems.dto.RouteDTO;

import java.util.List;

public interface RouteService {

    void createRoute(String stationSequence);

    void editRoute(Integer routeId, String stationSequence);

    List<RouteDTO> getFirstAndLastRoutesRows();

    List<RouteDTO> findRouteByRouteId(Integer id);

    double getRouteDistanceById(Integer id);

    void deleteRoute(Integer id);

    Integer getLastRouteId();

}
