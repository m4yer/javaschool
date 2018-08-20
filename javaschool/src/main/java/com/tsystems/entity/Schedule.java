package com.tsystems.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalTime;

/**
 * Schedule entity
 */
@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "time_arrival")
    private Instant time_arrival;

    @Column(name = "time_stop")
    private LocalTime time_stop;

    @Column(name = "time_departure")
    private Instant time_departure;

    @Column(name = "time_late")
    private LocalTime time_late;

    public Schedule() {
    }

    // For first station. (there is no time_arrival and time_stop at the first route station)
    public Schedule(Trip trip, Instant time_departure, Station station, LocalTime time_late) {
        this.trip = trip;
        this.station = station;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    // For all other stations. There are all part: time_arrival, time_departure and time_stop.
    public Schedule(Trip trip, Station station, Instant time_arrival, LocalTime time_stop, Instant time_departure, LocalTime time_late) {
        this.trip = trip;
        this.station = station;
        this.time_arrival = time_arrival;
        this.time_stop = time_stop;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    // For last station. (there is no time_departure and time_stop at the last route station)
    public Schedule(Trip trip, Station station, Instant time_arrival, LocalTime time_late) {
        this.trip = trip;
        this.station = station;
        this.time_arrival = time_arrival;
        this.time_late = time_late;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Instant getTime_arrival() {
        return time_arrival;
    }

    public void setTime_arrival(Instant time_arrival) {
        this.time_arrival = time_arrival;
    }

    public LocalTime getTime_stop() {
        return time_stop;
    }

    public void setTime_stop(LocalTime time_stop) {
        this.time_stop = time_stop;
    }

    public Instant getTime_departure() {
        return time_departure;
    }

    public void setTime_departure(Instant time_departure) {
        this.time_departure = time_departure;
    }

    public LocalTime getTime_late() {
        return time_late;
    }

    public void setTime_late(LocalTime time_late) {
        this.time_late = time_late;
    }


}
