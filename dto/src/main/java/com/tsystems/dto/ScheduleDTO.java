package com.tsystems.dto;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Objects;

public class ScheduleDTO {

    private Integer id;

    private TripDTO tripDto;

    private StationDTO stationDto;

    private Instant time_arrival;

    private LocalTime time_stop;

    private Instant time_departure;

    private LocalTime time_late;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Integer id, TripDTO tripDto, Instant time_arrival, LocalTime time_stop, Instant time_departure, LocalTime time_late) {
        this.id = id;
        this.tripDto = tripDto;
        this.time_arrival = time_arrival;
        this.time_stop = time_stop;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    public ScheduleDTO(Integer id, TripDTO tripDto, StationDTO stationDto, Instant time_arrival, LocalTime time_stop, Instant time_departure, LocalTime time_late) {
        this.id = id;
        this.tripDto = tripDto;
        this.stationDto = stationDto;
        this.time_arrival = time_arrival;
        this.time_stop = time_stop;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TripDTO getTripDto() {
        return tripDto;
    }

    public void setTripDto(TripDTO tripDto) {
        this.tripDto = tripDto;
    }

    public StationDTO getStationDto() {
        return stationDto;
    }

    public void setStationDto(StationDTO stationDto) {
        this.stationDto = stationDto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tripDto, stationDto, time_arrival, time_stop, time_departure, time_late);
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "id=" + id +
                ", tripDto=" + tripDto +
                ", stationDto=" + stationDto +
                ", time_arrival=" + time_arrival +
                ", time_stop='" + time_stop + '\'' +
                ", time_departure=" + time_departure +
                ", time_late='" + time_late + '\'' +
                '}';
    }
}
