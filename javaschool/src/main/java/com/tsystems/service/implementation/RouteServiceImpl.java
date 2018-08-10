package com.tsystems.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.controller.converter.Converter;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.RouteDTO;
import com.tsystems.entity.Route;
import com.tsystems.service.api.RouteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteServiceImpl implements RouteService {

    private static final Logger log = Logger.getLogger(RouteServiceImpl.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RouteDAO routeDAO;

    @Autowired
    private StationDAO stationDAO;

    public void setRouteDAO(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    @Transactional
    public void createRoute(String stationSequence) {
        Integer newRouteId = routeDAO.getLastRouteId() + 1;
        List<String> stations = Arrays.asList(stationSequence.split(","));
        for (int i = 0; i < stations.size(); i++) {
            routeDAO.add(new Route(newRouteId, stationDAO.findByName(stations.get(i)), i + 1));
        }
    }

    @Transactional
    public void editRoute(Integer routeId, String stationSequence) {
        String[] split = stationSequence.split(",");
        List<String> stations = new ArrayList<>(Arrays.asList(split));
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);

        // Loop for deleting extra stations
        for (Route route : routes) {
            if (stations.contains(route.getStation().getName())) {
                stations.remove(route.getStation().getName());
            } else {
                routeDAO.delete(route);
            }
        }

        // For adding new needed stations in table route
        for (String station : stations) {
            routeDAO.add(new Route(routeId, stationDAO.findByName(station), 0));
        }

        List<String> stationsAgain = Arrays.asList(split);

        for (int i = 0; i < stationsAgain.size(); i++) {
            routeDAO.updateRouteRow(routeId, stationDAO.findByName(stationsAgain.get(i)), i + 1);
        }

    }

    @Transactional
    public List<RouteDTO> getFirstAndLastRoutesRows() {
        List<Integer> singleRouteIds = routeDAO.getSingleRoutesId();
        List<Route> resultRouteList = new ArrayList<>();
        for (Integer routeId : singleRouteIds) {
            List<Route> foundRoutes = routeDAO.getFirstAndLastRouteRows(routeId);
            resultRouteList.addAll(foundRoutes);
        }
        return Converter.getRouteDtos(resultRouteList);
    }


    @Transactional
    public List<RouteDTO> findRouteByRouteId(Integer id) {
        return Converter.getRouteDtos(routeDAO.findRouteByRouteId(id));
    }

    @Transactional
    public double getRouteDistanceById(Integer id) {
        return routeDAO.getRouteDistanceByRouteId(id);
    }

    @Transactional
    public void deleteRoute(Integer id) {
        routeDAO.deleteRoute(id);
    }

    @Transactional
    public Integer getLastRouteId() {
        return routeDAO.getLastRouteId();
    }

}
