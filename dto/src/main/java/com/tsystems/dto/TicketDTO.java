package com.tsystems.dto;

import java.util.Objects;

public class TicketDTO {

    private Integer id;

    private TripDTO trip;

    private UserDTO user;

    private Integer seat_id;

    private StationDTO station_from;

    private StationDTO station_to;

    private Integer carriage_num;

    public TicketDTO() {
    }

    public TicketDTO(Integer id, TripDTO trip, UserDTO user, Integer seat_id, StationDTO station_from, StationDTO station_to, Integer carriage_num) {
        this.id = id;
        this.trip = trip;
        this.user = user;
        this.seat_id = seat_id;
        this.station_from = station_from;
        this.station_to = station_to;
        this.carriage_num = carriage_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public StationDTO getStation_from() {
        return station_from;
    }

    public void setStation_from(StationDTO station_from) {
        this.station_from = station_from;
    }

    public StationDTO getStation_to() {
        return station_to;
    }

    public void setStation_to(StationDTO station_to) {
        this.station_to = station_to;
    }

    public Integer getCarriage_num() {
        return carriage_num;
    }

    public void setCarriage_num(Integer carriage_num) {
        this.carriage_num = carriage_num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(id, ticketDTO.id) &&
                Objects.equals(trip, ticketDTO.trip) &&
                Objects.equals(user, ticketDTO.user) &&
                Objects.equals(seat_id, ticketDTO.seat_id) &&
                Objects.equals(station_from, ticketDTO.station_from) &&
                Objects.equals(station_to, ticketDTO.station_to) &&
                Objects.equals(carriage_num, ticketDTO.carriage_num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trip, user, seat_id, station_from, station_to, carriage_num);
    }
}
