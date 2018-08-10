package com.tsystems.service.implementation;

import com.tsystems.controller.converter.Converter;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.StationDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.service.api.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class StationServiceImpl implements StationService {

    @Autowired
    StationDAO stationDAO;

    @Autowired
    RouteDAO routeDAO;

    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Transactional
    public void addStation(Station station) {
        stationDAO.add(station);
    }

    @Transactional
    public Station findById(Integer id) {
        Station station = stationDAO.findById(id);
        return station;
    }

    @Transactional
    public Station findByName(String name) {
        return stationDAO.findByName(name);
    }

    @Transactional
    public void deleteStation(Station station) {
        stationDAO.delete(station);
    }

    @Transactional
    public List<StationDTO> getAll() {
        return Converter.getStationDtos(stationDAO.getAll());
    }

    public List<StationDTO> getRouteStations(Integer routeId) {
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<StationDTO> stations = new ArrayList<>();
        for (Route route : routes) {
            stations.add(Converter.getStationDto(route.getStation()));
        }
        return stations;
    }


}
