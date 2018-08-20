package com.tsystems.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Train entity
 */
@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "seats_amount")
    private Integer seats_amount;

    @Column(name = "carriage_amount")
    private Integer carriage_amount;

    @OneToMany(mappedBy = "train")
    private List<Trip> trips;

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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getSeats_amount() {
        return seats_amount;
    }

    public void setSeats_amount(Integer seats_amount) {
        this.seats_amount = seats_amount;
    }

    public Integer getCarriage_amount() {
        return carriage_amount;
    }

    public void setCarriage_amount(Integer carriage_amount) {
        this.carriage_amount = carriage_amount;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return seats_amount == train.seats_amount &&
                carriage_amount == train.carriage_amount &&
                Objects.equals(id, train.id) &&
                Objects.equals(name, train.name) &&
                Objects.equals(trips, train.trips);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, seats_amount, carriage_amount, trips);
    }
}
