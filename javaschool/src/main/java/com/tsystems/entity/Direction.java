package com.tsystems.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Direction entity
 */
@Entity
@Table(name = "direction")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "station_from")
    private Station station_from;

    @ManyToOne
    @JoinColumn(name = "station_to")
    private Station station_to;

    public Direction() {
    }

    public Direction(Station station_from, Station station_to) {
        this.station_from = station_from;
        this.station_to = station_to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Station getStation_from() {
        return station_from;
    }

    public void setStation_from(Station station_from) {
        this.station_from = station_from;
    }

    public Station getStation_to() {
        return station_to;
    }

    public void setStation_to(Station station_to) {
        this.station_to = station_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(id, direction.id) &&
                Objects.equals(station_from, direction.station_from) &&
                Objects.equals(station_to, direction.station_to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, station_from, station_to);
    }
}
