package com.tsystems.service.implementation;

import com.tsystems.dao.api.*;
import com.tsystems.entity.converter.Converter;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.entity.*;
import com.tsystems.jms.SimpleMessageSender;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.utils.ConverterUtil;
import com.tsystems.utils.HaversineUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleDAO scheduleDAO;
    private RouteDAO routeDAO;
    private TripDAO tripDAO;
    private TrainDAO trainDAO;
    private StationDAO stationDAO;
    private SimpleMessageSender messageSender;

    @Autowired
    public ScheduleServiceImpl(ScheduleDAO scheduleDAO, RouteDAO routeDAO, TripDAO tripDAO, TrainDAO trainDAO, StationDAO stationDAO, SimpleMessageSender messageSender) {
        this.scheduleDAO = scheduleDAO;
        this.routeDAO = routeDAO;
        this.tripDAO = tripDAO;
        this.trainDAO = trainDAO;
        this.stationDAO = stationDAO;
        this.messageSender = messageSender;
    }

    private static final Logger log = Logger.getLogger(ScheduleServiceImpl.class);

    @Transactional
    public Schedule findById(Integer id) {
        return scheduleDAO.findById(id);
    }

    @Transactional
    public List<Schedule> getAll() {
        return scheduleDAO.getAll();
    }

    @Transactional
    public List<ScheduleDTO> getScheduleByStationNameForToday(String stationName) {
        Station station = stationDAO.findByName(stationName);
        List<Schedule> schedules = scheduleDAO.getScheduleByStationIdForToday(station.getId(), null);
        return Converter.getScheduleDtos(schedules);
    }

    @Transactional
    public List<ScheduleDTO> getScheduleByStationNameForDate(String stationName, String date) {
        Station station = stationDAO.findByName(stationName);
        List<Schedule> schedules = scheduleDAO.getScheduleByStationIdForToday(station.getId(), date);
        return Converter.getScheduleDtos(schedules);
    }

    @Transactional
    public void addScheduleForTrip(Integer tripId, Integer routeId, String stationStopTimes, String tripStartTime, Integer trainId) {
        log.info("Adding schedule for new just created trip:");
        log.info("stationStopTimes: " + stationStopTimes);

        Trip trip = tripDAO.findById(tripId);
        Train chosenTrain = trainDAO.findById(trainId);
        Double trainSpeed = chosenTrain.getSpeed();

        List<String> stationsStopsInHoursAndMinutes = new ArrayList<>(Arrays.asList(stationStopTimes.split(",")));

        List<Long> stationTimeStops = ConverterUtil.parseMilliseconds(stationsStopsInHoursAndMinutes);

        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<Station> routeStations = new ArrayList<>();
        routes.forEach(route -> routeStations.add(route.getStation()));

        Station tempStation = routeStations.get(0);
        Instant tempInstant = ConverterUtil.parseInstant(tripStartTime);
        log.info("@@tempTimestamp: " + tempInstant.toString());
        for (int i = 0; i < routeStations.size(); i++) {
            if (i == 0) { // First schedule row.
                scheduleDAO.add(new Schedule(trip, tempInstant, routeStations.get(i), "00:00"));
                log.info("@@tempTimestamp IN 1 IF: " + tempInstant.toString());
                tempStation = routeStations.get(i);
            } else if (i != routeStations.size() - 1) { // All schedule row excerpt first and last.
                Station current = routeStations.get(i);
                Double distance = HaversineUtil.calculateDistanceInKm(Converter.getStationDto(tempStation), Converter.getStationDto(current));
                Long milliseconds = (long) (((distance / trainSpeed) * 60) * 60) * 1000;
                Instant currentInstant = Instant.ofEpochMilli(tempInstant.toEpochMilli() + milliseconds);
                log.info("@@currentTimestamp IN 2 IF: (without toString())" + currentInstant);
                scheduleDAO.add(new Schedule(trip, routeStations.get(i), currentInstant, stationsStopsInHoursAndMinutes.get(i), Instant.ofEpochMilli(currentInstant.toEpochMilli() + stationTimeStops.get(i)), "00:00"));
                tempStation = current;
                tempInstant = currentInstant;
            } else { // Last schedule row.
                Station current = routeStations.get(i);
                Double distance = HaversineUtil.calculateDistanceInKm(Converter.getStationDto(tempStation), Converter.getStationDto(current));
                Long milliseconds = (long) (((distance / trainSpeed) * 60) * 60) * 1000;
                Instant currentInstant = Instant.ofEpochMilli(tempInstant.toEpochMilli() + milliseconds);
                log.info("@@currentTimestamp IN 3 IF: " + currentInstant);
                scheduleDAO.add(new Schedule(trip, routeStations.get(i), currentInstant, "00:00"));
            }
        }

        // This loop sends messages to ActiveMQ.
        routeStations.forEach(station -> messageSender.sendMessage(station.getName()));

    }

    @Transactional
    public List<ScheduleDTO> getSchedulesByTripId(Integer tripId) {
        return Converter.getScheduleDtos(scheduleDAO.getSchedulesByTripId(tripId));
    }

    @Transactional
    public void editLateStationSchedule(Integer scheduleId, String time_late) {
        scheduleDAO.editLateStationSchedule(scheduleId, time_late);
    }

}
