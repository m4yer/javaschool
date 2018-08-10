package com.tsystems.dto;

import java.util.Objects;

public class RouteDTO {

    private Integer id;

    private Integer route_id;

    private StationDTO stationDto;

    private Integer station_order;

    public RouteDTO() {
    }

    public RouteDTO(Integer id, Integer route_id, StationDTO station, Integer station_order) {
        this.id = id;
        this.route_id = route_id;
        this.stationDto = station;
        this.station_order = station_order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Integer route_id) {
        this.route_id = route_id;
    }

    public StationDTO getStationDto() {
        return stationDto;
    }

    public void setStationDto(StationDTO stationDto) {
        this.stationDto = stationDto;
    }

    public Integer getStation_order() {
        return station_order;
    }

    public void setStation_order(Integer station_order) {
        this.station_order = station_order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDTO routeDTO = (RouteDTO) o;
        return Objects.equals(id, routeDTO.id) &&
                Objects.equals(route_id, routeDTO.route_id) &&
                Objects.equals(stationDto, routeDTO.stationDto) &&
                Objects.equals(station_order, routeDTO.station_order);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, route_id, stationDto, station_order);
    }
}
