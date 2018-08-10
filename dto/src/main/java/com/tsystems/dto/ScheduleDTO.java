package com.tsystems.dto;

import java.time.Instant;
import java.util.Objects;

public class ScheduleDTO {

    private Integer id;

    private TripDTO tripDto;

    private StationDTO stationDto;

    private Instant time_arrival;

    private String time_stop;

    private Instant time_departure;

    private String time_late;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Integer id, TripDTO tripDto, Instant time_arrival, String time_stop, Instant time_departure, String time_late) {
        this.id = id;
        this.tripDto = tripDto;
        this.time_arrival = time_arrival;
        this.time_stop = time_stop;
        this.time_departure = time_departure;
        this.time_late = time_late;
    }

    public ScheduleDTO(Integer id, TripDTO tripDto, StationDTO stationDto, Instant time_arrival, String time_stop, Instant time_departure, String time_late) {
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
        ScheduleDTO that = (ScheduleDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tripDto, that.tripDto) &&
                Objects.equals(stationDto, that.stationDto) &&
                Objects.equals(time_arrival, that.time_arrival) &&
                Objects.equals(time_stop, that.time_stop) &&
                Objects.equals(time_departure, that.time_departure) &&
                Objects.equals(time_late, that.time_late);
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
