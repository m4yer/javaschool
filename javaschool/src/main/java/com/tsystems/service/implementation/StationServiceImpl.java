package com.tsystems.service.implementation;

import com.tsystems.entity.converter.Converter;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.StationDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.service.api.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private StationDAO stationDAO;
    private RouteDAO routeDAO;

    @Autowired
    public StationServiceImpl(StationDAO stationDAO, RouteDAO routeDAO) {
        this.stationDAO = stationDAO;
        this.routeDAO = routeDAO;
    }

    @Transactional
    public void addStation(Station station) {
        stationDAO.add(station);
    }

    @Transactional
    public Station findById(Integer id) {
        return stationDAO.findById(id);
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

    @Transactional
    public List<StationDTO> getRouteStations(Integer routeId) {
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<StationDTO> stations = new ArrayList<>();
        routes.forEach(route -> stations.add(Converter.getStationDto(route.getStation())));
        return stations;
    }


}
