package com.tsystems.service.implementation;

import com.tsystems.dao.api.DirectionDAO;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.CoordinateDTO;
import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;
import com.tsystems.service.api.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService {
    private DirectionDAO directionDAO;
    private StationDAO stationDAO;
    private RouteDAO routeDAO;

    @Autowired
    public DirectionServiceImpl(DirectionDAO directionDAO, StationDAO stationDAO, RouteDAO routeDAO) {
        this.directionDAO = directionDAO;
        this.stationDAO = stationDAO;
        this.routeDAO = routeDAO;
    }

    @Transactional
    public List<String> getDepartureStationNames(String stationName) {
        List<Station> stations = directionDAO.getDepartures(stationName);
        List<String> stationNames = new ArrayList<>();
        for (Station station : stations) {
            stationNames.add(station.getName());
        }
        return stationNames;
    }

    @Transactional
    public List<CoordinateDTO> getDepartureStationCoordinates(String stationName) {
        List<Station> stations = directionDAO.getDepartures(stationName);
        List<CoordinateDTO> coordinates = new ArrayList<>();
        for (Station station : stations) {
            coordinates.add(new CoordinateDTO(station.getLatitude(), station.getLongitude()));
        }
        return coordinates;
    }

    @Transactional
    public boolean removeDirectionBetweenStations(String stationFromName, String stationToName) {
        List<Integer> allRouteIds = routeDAO.getSingleRoutesId();
        for (Integer routeId : allRouteIds) {
            List<String> routeStations = new ArrayList<>();
            routeDAO.findRouteByRouteId(routeId).forEach(route -> routeStations.add(route.getStation().getName()));

            if (routeStations.contains(stationFromName)) {
                if (routeStations.contains(stationToName)) {

                    if (routeStations.indexOf(stationFromName) < routeStations.indexOf(stationToName)) {
                        return false;
                    }

                }
            }

        }
        directionDAO.removeDirectionBetweenStations(stationFromName, stationToName);
        return true;
    }

    @Transactional
    public void addDirectionBetweenStations(String stationFromName, String stationToName) {
        Station stationFrom = stationDAO.findByName(stationFromName);
        Station stationTo = stationDAO.findByName(stationToName);
        Direction newDirection = new Direction(stationFrom, stationTo);
        directionDAO.add(newDirection);
    }

}
