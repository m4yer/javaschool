package com.tsystems.dto;

import java.time.Instant;

public class TripDTO {

    private Integer id;

    private TrainDTO trainDto;

    private Integer route_id;

    private Instant start_time;

    private Boolean active;

    public TripDTO(Integer id, TrainDTO trainDto, Integer route_id, Instant start_time, Boolean active) {
        this.id = id;
        this.trainDto = trainDto;
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

    public TrainDTO getTrainDto() {
        return trainDto;
    }

    public void setTrainDto(TrainDTO trainDto) {
        this.trainDto = trainDto;
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
}
