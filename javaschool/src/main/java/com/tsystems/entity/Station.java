package com.tsystems.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Station entity
 */
@Entity
@Table(name = "station")
@NamedQuery(name = "Station.findByName",
        query = "SELECT station FROM Station station WHERE station.name=:name")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "station")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "station")
    private List<Route> routes;

    @OneToMany(mappedBy = "station_from")
    private List<Direction> directionsFrom;

    @OneToMany(mappedBy = "station_to")
    private List<Direction> directionsTo;

    @OneToMany(mappedBy = "station_from")
    private List<Ticket> ticketsFrom;

    @OneToMany(mappedBy = "station_to")
    private List<Ticket> ticketsTo;

    public Station() {
    }

    public Station(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id) &&
                Objects.equals(name, station.name) &&
                Objects.equals(latitude, station.latitude) &&
                Objects.equals(longitude, station.longitude) &&
                Objects.equals(schedules, station.schedules) &&
                Objects.equals(routes, station.routes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, latitude, longitude, schedules, routes);
    }
}
