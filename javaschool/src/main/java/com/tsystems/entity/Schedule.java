package com.tsystems.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

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
    private String time_stop;
    // TODO: ^^^ OMG? String ??

    @Column(name = "time_departure")
    private Instant time_departure;

    @Column(name = "time_late")
    private String time_late;

    public Schedule() {
    }

    // For first station. (there is no time_arrival and time_stop at the first route station)
    public Schedule(Trip trip, Instant time_departure, Station station, String time_late) {
        this.trip = trip;
        this.station = station;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    // For all other stations. There are all part: time_arrival, time_departure and time_stop.
    public Schedule(Trip trip, Station station, Instant time_arrival, String time_stop, Instant time_departure, String time_late) {
        this.trip = trip;
        this.station = station;
        this.time_arrival = time_arrival;
        this.time_stop = time_stop;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    // For last station. (there is no time_departure and time_stop at the last route station)
    public Schedule(Trip trip, Station station, Instant time_arrival, String time_late) {
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

    public String getTime_stop() {
        return time_stop;
    }

    public void setTime_stop(String time_stop) {
        this.time_stop = time_stop;
    }

    public Instant getTime_departure() {
        return time_departure;
    }

    public void setTime_departure(Instant time_departure) {
        this.time_departure = time_departure;
    }

    public String getTime_late() {
        return time_late;
    }

    public void setTime_late(String time_late) {
        this.time_late = time_late;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) &&
                Objects.equals(trip, schedule.trip) &&
                Objects.equals(station, schedule.station) &&
                Objects.equals(time_arrival, schedule.time_arrival) &&
                Objects.equals(time_stop, schedule.time_stop) &&
                Objects.equals(time_departure, schedule.time_departure) &&
                Objects.equals(time_late, schedule.time_late);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trip, station, time_arrival, time_stop, time_departure, time_late);
    }
}
