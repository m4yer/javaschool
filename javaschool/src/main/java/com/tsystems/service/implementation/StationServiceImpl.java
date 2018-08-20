package com.tsystems.service.implementation;

import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.StationDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.entity.converter.Converter;
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

    /**
     * Method for creating new station
     *
     * @param station station
     */
    @Transactional
    public void addStation(Station station) {
        stationDAO.add(station);
    }

    /**
     * Method for getting station by its id
     *
     * @param id stationId
     * @return station found by id
     */
    @Transactional
    public Station findById(Integer id) {
        return stationDAO.findById(id);
    }

    /**
     * Method for getting station by its name
     *
     * @param name stationName
     * @return station found by name
     */
    @Transactional
    public Station findByName(String name) {
        return stationDAO.findByName(name);
    }

    /**
     * Method for deleting stations
     *
     * @param station station
     */
    @Transactional
    public void deleteStation(Station station) {
        stationDAO.delete(station);
    }

    /**
     * Method for getting all stations
     *
     * @return list of stations
     */
    @Transactional
    public List<StationDTO> getAll() {
        return Converter.getStationDtos(stationDAO.getAll());
    }

    /**
     * Method for getting routeStations
     *
     * @param routeId routeId
     * @return list of stations
     */
    @Transactional
    public List<StationDTO> getRouteStations(Integer routeId) {
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<StationDTO> stations = new ArrayList<>();
        routes.forEach(route -> stations.add(Converter.getStationDto(route.getStation())));
        return stations;
    }


}
