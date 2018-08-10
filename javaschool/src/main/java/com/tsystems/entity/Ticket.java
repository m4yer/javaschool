package com.tsystems.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "seat_id")
    private Integer seat_id;

    @ManyToOne
    @JoinColumn(name = "station_from")
    private Station station_from;

    @ManyToOne
    @JoinColumn(name = "station_to")
    private Station station_to;

    @Column(name = "carriage_num")
    private Integer carriage_num;

    public Ticket() {
    }

    public Ticket(Trip trip, User user, Integer seat_id, Station station_from, Station station_to, Integer carriage_num) {
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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
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
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(trip, ticket.trip) &&
                Objects.equals(user, ticket.user) &&
                Objects.equals(seat_id, ticket.seat_id) &&
                Objects.equals(carriage_num, ticket.carriage_num);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, trip, user, seat_id, carriage_num);
    }
}
