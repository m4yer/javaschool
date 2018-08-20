package com.tsystems.entity.converter;

import com.tsystems.dto.*;
import com.tsystems.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for performing convert DTO operations
 */
public class Converter {

    private Converter() {
    }

    /**
     * Returns trainDTO from train entity
     *
     * @param train train
     * @return trainDto
     */
    public static TrainDTO getTrainDto(Train train) {
        return new TrainDTO(train.getId(), train.getName(), train.getSpeed(), train.getSeats_amount(), train.getCarriage_amount());
    }

    /**
     * Returns List of trainDTO from list train entity
     *
     * @param trainList trainList
     * @return list trainDto
     */
    public static List<TrainDTO> getTrainDtos(List<Train> trainList) {
        List<TrainDTO> trainDtosResultList = new ArrayList<>();
        trainList.forEach(train -> trainDtosResultList.add(Converter.getTrainDto(train)));
        return trainDtosResultList;
    }

    /**
     * Returns stationDTO from station entity
     *
     * @param station station
     * @return stationDTO
     */
    public static StationDTO getStationDto(Station station) {
        return new StationDTO(station.getId(), station.getName(), station.getLatitude(), station.getLongitude());
    }

    /**
     * Returns List of stationDTO from list station entity
     *
     * @param stationList stationList
     * @return list stationDTO
     */
    public static List<StationDTO> getStationDtos(List<Station> stationList) {
        List<StationDTO> stationDtosResultList = new ArrayList<>();
        stationList.forEach(station -> stationDtosResultList.add(Converter.getStationDto(station)));
        return stationDtosResultList;
    }

    /**
     * Returns tripDTO from trip entity
     *
     * @param trip trip
     * @return tripDTO
     */
    public static TripDTO getTripDto(Trip trip) {
        return new TripDTO(trip.getId(), Converter.getTrainDto(trip.getTrain()), trip.getRoute_id(), trip.getStart_time(), trip.getActive());
    }

    /**
     * Returns List of tripDTO from list trip entity
     *
     * @param tripList tripList
     * @return list tripDTO
     */
    public static List<TripDTO> getTripDtos(List<Trip> tripList) {
        List<TripDTO> tripDtosResulList = new ArrayList<>();
        tripList.forEach(trip -> tripDtosResulList.add(Converter.getTripDto(trip)));
        return tripDtosResulList;
    }

    /**
     * Returns routeDTO from route entity
     *
     * @param route route
     * @return routeDTO
     */
    public static RouteDTO getRouteDto(Route route) {
        return new RouteDTO(route.getId(), route.getRoute_id(), Converter.getStationDto(route.getStation()), route.getStation_order());
    }

    /**
     * Returns List of routeDTO from list route entity
     *
     * @param routeList routeList
     * @return list routeDTO
     */
    public static List<RouteDTO> getRouteDtos(List<Route> routeList) {
        List<RouteDTO> routeDtosResultList = new ArrayList<>();
        routeList.forEach(route -> routeDtosResultList.add(Converter.getRouteDto(route)));
        return routeDtosResultList;
    }

    /**
     * Returns scheduleDTO from schedule entity
     *
     * @param schedule schedule
     * @return scheduleDTO
     */
    public static ScheduleDTO getScheduleDto(Schedule schedule) {
        return new ScheduleDTO(schedule.getId(), Converter.getTripDto(schedule.getTrip()), Converter.getStationDto(schedule.getStation()), schedule.getTime_arrival(), schedule.getTime_stop(), schedule.getTime_departure(), schedule.getTime_late());
    }

    /**
     * Returns List of scheduleDTO from list schedule entity
     *
     * @param scheduleList scheduleList
     * @return list scheduleDTO
     */
    public static List<ScheduleDTO> getScheduleDtos(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDtosResultList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDtosResultList.add(Converter.getScheduleDto(schedule)));
        return scheduleDtosResultList;
    }

    /**
     * Returns userDTO from user entity
     *
     * @param user user
     * @return userDTO
     */
    public static UserDTO getUserDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getBirthday());
    }

    /**
     * Returns List of userDTO from list user entity
     *
     * @param userList userList
     * @return list userDTO
     */
    public static List<UserDTO> getUserDtos(List<User> userList) {
        List<UserDTO> userDtosResultList = new ArrayList<>();
        userList.forEach(user -> userDtosResultList.add(Converter.getUserDto(user)));
        return userDtosResultList;
    }

    /**
     * Returns ticketDTO from ticket entity
     *
     * @param ticket ticket
     * @return ticketDTO
     */
    public static TicketDTO getTicketDto(Ticket ticket) {
        return new TicketDTO(ticket.getId(), Converter.getTripDto(ticket.getTrip()), Converter.getUserDto(ticket.getUser()), ticket.getSeat_id(), Converter.getStationDto(ticket.getStation_from()), Converter.getStationDto(ticket.getStation_to()), ticket.getCarriage_num());
    }

    /**
     * Returns List of ticketDTO from list ticket entity
     *
     * @param ticketList ticketList
     * @return list ticketDTO
     */
    public static List<TicketDTO> getTicketDtos(List<Ticket> ticketList) {
        List<TicketDTO> ticketDtosResultList = new ArrayList<>();
        ticketList.forEach(ticket -> ticketDtosResultList.add(Converter.getTicketDto(ticket)));
        return ticketDtosResultList;
    }

}
