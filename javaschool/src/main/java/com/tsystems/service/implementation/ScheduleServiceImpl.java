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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleDAO scheduleDAO;
    @Autowired
    RouteDAO routeDAO;
    @Autowired
    TripDAO tripDAO;
    @Autowired
    TrainDAO trainDAO;
    @Autowired
    StationDAO stationDAO;
    @Autowired
    SimpleMessageSender messageSender;

    private static final Logger log = Logger.getLogger(ScheduleServiceImpl.class);

    public void setScheduleDAO(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    @Transactional
    public Schedule findById(Integer id) {
        return scheduleDAO.findById(id);
    }

    @Transactional
    public List<Schedule> getAll() {
        return scheduleDAO.getAll();
    }

    @Transactional
    public List<ScheduleDTO> findScheduleByStationId(Integer stationId) {
        return Converter.getScheduleDtos(scheduleDAO.findScheduleByStationId(stationId));
    }

    @Transactional
    public List<ScheduleDTO> getScheduleByStationNameForToday(String stationName) {
        Station station = stationDAO.findByName(stationName);
        // TODO: Here now schedule fetching by ALL days. Need to change it for TODAY.
        List<Schedule> schedules = scheduleDAO.getScheduleByStationIdForToday(station.getId());
        return Converter.getScheduleDtos(schedules);
    }

    @Transactional
    public void addScheduleForTrip(Integer tripId, Integer routeId, String stationStopTimes, String tripStartTime, Integer trainId) {

        Trip trip = tripDAO.findById(tripId);
        Train chosenTrain = trainDAO.findById(trainId);
        Double trainSpeed = chosenTrain.getSpeed();

        List<String> stationsStopsInHoursAndMinutes = new ArrayList<>(Arrays.asList(stationStopTimes.split(",")));

        List<Long> stationTimeStops = ConverterUtil.parseMilliseconds(stationsStopsInHoursAndMinutes);

        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<Station> routeStations = new ArrayList<>();
        for (Route route : routes) {
            routeStations.add(route.getStation());
        }

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
        for (Station station : routeStations) {
            messageSender.sendMessage(station.getName());
        }

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
