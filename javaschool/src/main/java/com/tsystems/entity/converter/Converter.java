package com.tsystems.entity.converter;

import com.tsystems.dto.*;
import com.tsystems.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    private Converter() {
    }

    public static TrainDTO getTrainDto(Train train) {
        return new TrainDTO(train.getId(), train.getName(), train.getSpeed(), train.getSeats_amount(), train.getCarriage_amount());
    }

    public static List<TrainDTO> getTrainDtos(List<Train> trainList) {
        List<TrainDTO> trainDtosResultList = new ArrayList<>();
        trainList.forEach(train -> trainDtosResultList.add(Converter.getTrainDto(train)));
        return trainDtosResultList;
    }

    public static StationDTO getStationDto(Station station) {
        return new StationDTO(station.getId(), station.getName(), station.getLatitude(), station.getLongitude());
    }

    public static List<StationDTO> getStationDtos(List<Station> stationList) {
        List<StationDTO> stationDtosResultList = new ArrayList<>();
        stationList.forEach(station -> stationDtosResultList.add(Converter.getStationDto(station)));
        return stationDtosResultList;
    }

    public static TripDTO getTripDto(Trip trip) {
        return new TripDTO(trip.getId(), Converter.getTrainDto(trip.getTrain()), trip.getRoute_id(), trip.getStart_time(), trip.getActive());
    }

    public static List<TripDTO> getTripDtos(List<Trip> tripList) {
        List<TripDTO> tripDtosResulList = new ArrayList<>();
        tripList.forEach(trip -> tripDtosResulList.add(Converter.getTripDto(trip)));
        return tripDtosResulList;
    }

    public static RouteDTO getRouteDto(Route route) {
        return new RouteDTO(route.getId(), route.getRoute_id(), Converter.getStationDto(route.getStation()), route.getStation_order());
    }

    public static List<RouteDTO> getRouteDtos(List<Route> routeList) {
        List<RouteDTO> routeDtosResultList = new ArrayList<>();
        routeList.forEach(route -> routeDtosResultList.add(Converter.getRouteDto(route)));
        return routeDtosResultList;
    }

    public static ScheduleDTO getScheduleDto(Schedule schedule) {
        return new ScheduleDTO(schedule.getId(), Converter.getTripDto(schedule.getTrip()), Converter.getStationDto(schedule.getStation()), schedule.getTime_arrival(), schedule.getTime_stop(), schedule.getTime_departure(), schedule.getTime_late());
    }

    public static List<ScheduleDTO> getScheduleDtos(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDtosResultList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDtosResultList.add(Converter.getScheduleDto(schedule)));
        return scheduleDtosResultList;
    }

    public static UserDTO getUserDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getBirthday());
    }

    public static List<UserDTO> getUserDtos(List<User> userList) {
        List<UserDTO> userDtosResultList = new ArrayList<>();
        userList.forEach(user -> userDtosResultList.add(Converter.getUserDto(user)));
        return userDtosResultList;
    }

    public static TicketDTO getTicketDto(Ticket ticket) {
        return new TicketDTO(ticket.getId(), Converter.getTripDto(ticket.getTrip()), Converter.getUserDto(ticket.getUser()), ticket.getSeat_id(), Converter.getStationDto(ticket.getStation_from()), Converter.getStationDto(ticket.getStation_to()), ticket.getCarriage_num());
    }

    public static List<TicketDTO> getTicketDtos(List<Ticket> ticketList) {
        List<TicketDTO> ticketDtosResultList = new ArrayList<>();
        ticketList.forEach(ticket -> ticketDtosResultList.add(Converter.getTicketDto(ticket)));
        return ticketDtosResultList;
    }

}
