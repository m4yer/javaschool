package com.tsystems.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.RouteDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.converter.Converter;
import com.tsystems.service.api.RouteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    private static final Logger log = Logger.getLogger(RouteServiceImpl.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private RouteDAO routeDAO;
    private StationDAO stationDAO;

    @Autowired
    public RouteServiceImpl(RouteDAO routeDAO, StationDAO stationDAO) {
        this.routeDAO = routeDAO;
        this.stationDAO = stationDAO;
    }

    /**
     * Creates route with specified stationSequence
     *
     * @param stationSequence
     */
    @Transactional
    public void createRoute(String stationSequence) {
        Integer newRouteId = routeDAO.getLastRouteId() + 1;
        List<String> stations = Arrays.asList(stationSequence.split(","));
        for (int i = 0; i < stations.size(); i++) {
            routeDAO.add(new Route(newRouteId, stationDAO.findByName(stations.get(i)), i + 1));
        }
    }

    /**
     * Edits specified route with specified stationSequence
     *
     * @param routeId routeId
     * @param stationSequence stationSequence
     */
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

    /**
     * Get all first and last routeRows
     *
     * @return list of routes
     */
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

    /**
     * Finds and returns route by routeId
     *
     * @param id routeId
     * @return list of route rows
     */
    @Transactional
    public List<RouteDTO> findRouteByRouteId(Integer id) {
        return Converter.getRouteDtos(routeDAO.findRouteByRouteId(id));
    }

    /**
     * Returns route distance by id
     *
     * @param id routeId
     * @return distance
     */
    @Transactional
    public double getRouteDistanceById(Integer id) {
        return routeDAO.getRouteDistanceByRouteId(id);
    }

    /**
     * Deletes route by it's id
     *
     * @param id routeId
     */
    @Transactional
    public void deleteRoute(Integer id) {
        routeDAO.deleteRoute(id);
    }

    /**
     * Returns last routeId
     *
     * @return last route Id
     */
    @Transactional
    public Integer getLastRouteId() {
        return routeDAO.getLastRouteId();
    }

}
