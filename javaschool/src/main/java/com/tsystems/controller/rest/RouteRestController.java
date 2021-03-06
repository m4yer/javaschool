package com.tsystems.controller.rest;

import com.tsystems.dto.RouteDTO;
import com.tsystems.service.api.RouteService;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import com.tsystems.utils.RoundUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Dispatches the route related rest-queries
 */
@RestController
public class RouteRestController {
    private RouteService routeService;
    private StationService stationService;

    @Autowired
    public RouteRestController(RouteService routeService, StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
    }

    /**
     * Returns all routes (1st and last row from db)
     *
     * @return list of routes in JSON
     */
    @GetMapping("/route/list/get")
    public String getAllRoutes() {
        return ConverterUtil.parseJson(routeService.getFirstAndLastRoutesRows());
    }

    /**
     * Returns the List<String> of route Stations
     *
     * @param id routeId
     * @return list of route station names in JSON
     */
    @GetMapping("/route/get/{id}")
    public String getRouteStationsById(@PathVariable("id") Integer id) {
        List<RouteDTO> routes = routeService.findRouteByRouteId(id);
        List<String> stations = new ArrayList<>();
        routes.forEach(route -> stations.add(route.getStationDto().getName()));
        return ConverterUtil.parseJson(stations);
    }

    /**
     * Returns the distance of the specified routeId
     *
     * @param id routeId
     * @return double distance in JSON
     */
    @GetMapping("/route/distance/{id}")
    public double getRouteDistanceById(@PathVariable("id") Integer id) {
        return RoundUtil.round(routeService.getRouteDistanceById(id), 2);
    }
}
