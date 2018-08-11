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
        for (Train train : trainList) {
            trainDtosResultList.add(new TrainDTO(train.getId(), train.getName(), train.getSpeed(), train.getSeats_amount(), train.getCarriage_amount()));
        }
        return trainDtosResultList;
    }

    public static StationDTO getStationDto(Station station) {
        return new StationDTO(station.getId(), station.getName(), station.getLatitude(), station.getLongitude());
    }

    public static List<StationDTO> getStationDtos(List<Station> stationList) {
        List<StationDTO> stationDtosResultList = new ArrayList<>();
        for (Station station : stationList) {
            stationDtosResultList.add(new StationDTO(station.getId(), station.getName(), station.getLatitude(), station.getLongitude()));
        }
        return stationDtosResultList;
    }

    public static Station getStation(StationDTO stationDto) {
        return new Station(stationDto.getName(), stationDto.getLatitude(), stationDto.getLongitude());
    }

    public static TripDTO getTripDto(Trip trip) {
        return new TripDTO(trip.getId(), Converter.getTrainDto(trip.getTrain()), trip.getRoute_id(), trip.getStart_time(), trip.getActive());
    }

    public static List<TripDTO> getTripDtos(List<Trip> tripList) {
        List<TripDTO> tripDtosResulList = new ArrayList<>();
        for (Trip trip : tripList) {
            tripDtosResulList.add(new TripDTO(trip.getId(), Converter.getTrainDto(trip.getTrain()), trip.getRoute_id(), trip.getStart_time(), trip.getActive()));
        }
        return tripDtosResulList;
    }

    public static RouteDTO getRouteDto(Route route) {
        return new RouteDTO(route.getId(), route.getRoute_id(), Converter.getStationDto(route.getStation()), route.getStation_order());
    }

    public static List<RouteDTO> getRouteDtos(List<Route> routeList) {
        List<RouteDTO> routeDtosResultList = new ArrayList<>();
        for (Route route : routeList) {
            routeDtosResultList.add(new RouteDTO(route.getId(), route.getRoute_id(), Converter.getStationDto(route.getStation()), route.getStation_order()));
        }
        return routeDtosResultList;
    }

    public static ScheduleDTO getScheduleDto(Schedule schedule) {
        return new ScheduleDTO(schedule.getId(), Converter.getTripDto(schedule.getTrip()), Converter.getStationDto(schedule.getStation()), schedule.getTime_arrival(), schedule.getTime_stop(), schedule.getTime_departure(), schedule.getTime_late());
    }

    public static List<ScheduleDTO> getScheduleDtos(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDtosResultList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleDtosResultList.add(new ScheduleDTO(schedule.getId(), Converter.getTripDto(schedule.getTrip()), Converter.getStationDto(schedule.getStation()), schedule.getTime_arrival(), schedule.getTime_stop(), schedule.getTime_departure(), schedule.getTime_late()));
        }
        return scheduleDtosResultList;
    }

    public static UserDTO getUserDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getBirthday());
    }

    public static List<UserDTO> getUserDtos(List<User> userList) {
        List<UserDTO> userDtosResultList = new ArrayList<>();
        for (User user : userList) {
            userDtosResultList.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getBirthday()));
        }
        return userDtosResultList;
    }

    public static TicketDTO getTicketDto(Ticket ticket) {
        return new TicketDTO(ticket.getId(), Converter.getTripDto(ticket.getTrip()), Converter.getUserDto(ticket.getUser()), ticket.getSeat_id(), Converter.getStationDto(ticket.getStation_from()), Converter.getStationDto(ticket.getStation_to()), ticket.getCarriage_num());
    }

    public static List<TicketDTO> getTicketDtos(List<Ticket> ticketList) {
        List<TicketDTO> ticketDtosResultList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketDtosResultList.add(new TicketDTO(ticket.getId(), Converter.getTripDto(ticket.getTrip()), Converter.getUserDto(ticket.getUser()), ticket.getSeat_id(), Converter.getStationDto(ticket.getStation_from()), Converter.getStationDto(ticket.getStation_to()), ticket.getCarriage_num()));
        }
        return ticketDtosResultList;
    }

}
