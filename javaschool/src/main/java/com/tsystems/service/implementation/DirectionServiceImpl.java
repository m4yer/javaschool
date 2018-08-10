package com.tsystems.service.implementation;

import com.tsystems.dao.api.DirectionDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dto.CoordinateDTO;
import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;
import com.tsystems.service.api.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DirectionServiceImpl implements DirectionService {

    @Autowired
    DirectionDAO directionDAO;

    @Autowired
    StationDAO stationDAO;

    public void setDirectionDAO(DirectionDAO directionDAO) {
        this.directionDAO = directionDAO;
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
    public void removeDirectionBetweenStations(String stationFromName, String stationToName) {
        directionDAO.removeDirectionBetweenStations(stationFromName, stationToName);
    }

    @Transactional
    public void addDirectionBetweenStations(String stationFromName, String stationToName) {
        Station stationFrom = stationDAO.findByName(stationFromName);
        Station stationTo = stationDAO.findByName(stationToName);
        Direction newDirection = new Direction(stationFrom, stationTo);
        directionDAO.add(newDirection);
    }

}
