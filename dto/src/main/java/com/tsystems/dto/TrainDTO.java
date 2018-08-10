package com.tsystems.dto;

public class TrainDTO {

    private Integer id;

    private String name;

    private Double speed;

    private Integer seats_amount;

    private Integer carriage_amount;


    public TrainDTO(Integer id, String name, Double speed, Integer seats_amount, Integer carriage_amount) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.seats_amount = seats_amount;
        this.carriage_amount = carriage_amount;
    }

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

    @Override
    public String toString() {
        return "TrainDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speed=" + speed +
                ", seats_amount=" + seats_amount +
                ", carriage_amount=" + carriage_amount +
                '}';
    }
}
