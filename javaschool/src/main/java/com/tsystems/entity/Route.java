package com.tsystems.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Route entity
 */
@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "route_id")
    private Integer route_id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "station_order")
    private Integer station_order;

    public Route() {
    }

    public Route(Integer route_id, Station station, Integer station_order) {
        this.route_id = route_id;
        this.station = station;
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

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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
        Route route = (Route) o;
        return Objects.equals(id, route.id) &&
                Objects.equals(route_id, route.route_id) &&
                Objects.equals(station, route.station) &&
                Objects.equals(station_order, route.station_order);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, route_id, station, station_order);
    }
}
