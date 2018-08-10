package com.tsystems.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "route_id")
    private Integer route_id;

    @Column(name = "start_time")
    private Instant start_time;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "trip")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "trip")
    private List<Ticket> tickets;

    public Trip() {
    }

    public Trip(Train train, Integer route_id, Instant start_time, Boolean active) {
        this.train = train;
        this.route_id = route_id;
        this.start_time = start_time;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Integer route_id) {
        this.route_id = route_id;
    }

    public Instant getStart_time() {
        return start_time;
    }

    public void setStart_time(Instant start_time) {
        this.start_time = start_time;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) &&
                Objects.equals(train, trip.train) &&
                Objects.equals(route_id, trip.route_id) &&
                Objects.equals(start_time, trip.start_time) &&
                Objects.equals(active, trip.active) &&
                Objects.equals(schedules, trip.schedules) &&
                Objects.equals(tickets, trip.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, train, route_id, start_time, active, schedules, tickets);
    }
}
