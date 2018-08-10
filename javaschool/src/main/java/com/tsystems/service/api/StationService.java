package com.tsystems.service.api;

import com.tsystems.dto.StationDTO;
import com.tsystems.entity.Station;

import java.util.List;

public interface StationService {

    void addStation(Station station);

    Station findById(Integer id);

    Station findByName(String name);

    void deleteStation(Station station);

    List<StationDTO> getAll();

    List<StationDTO> getRouteStations(Integer routeId);

}
