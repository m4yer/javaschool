package com.tsystems.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dao.api.*;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.entity.*;
import com.tsystems.entity.converter.Converter;
import com.tsystems.jms.SimpleMessageSender;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.utils.ConverterUtil;
import com.tsystems.utils.HaversineUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

    private final static ObjectMapper objectMapper = new ObjectMapper();

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

    /**
     * Returns schedule by id
     *
     * @param id scheduleId
     * @return schedule
     */
    @Transactional
    public Schedule findById(Integer id) {
        return scheduleDAO.findById(id);
    }

    /**
     * Returns all schedules
     *
     * @return list of schedules
     */
    @Transactional
    public List<Schedule> getAll() {
        return scheduleDAO.getAll();
    }

    /**
     * Returns schedule of specified station for specified date
     *
     * @param stationName stationName
     * @param date date
     * @return list of schedules
     */
    @Transactional
    public List<ScheduleDTO> getScheduleByStationNameForDate(String stationName, String date) {
        Station station = stationDAO.findByName(stationName);
        List<Schedule> schedules = scheduleDAO.getScheduleByStationIdForToday(station.getId(), date);

        List<ScheduleDTO> resultScheduleDtoList = Converter.getScheduleDtos(schedules);

        for (ScheduleDTO schedule : resultScheduleDtoList) {
            List<Schedule> scheduleForAllTrip = scheduleDAO.getSchedulesByTripId(schedule.getTripDto().getId());
            LocalTime tempTime = LocalTime.of(0, 0);
            for (Schedule scheduleElement : scheduleForAllTrip) {
                if (scheduleElement.getId().equals(schedule.getId())) {
                    schedule.setTime_late(tempTime);
                    break;
                }
                tempTime = tempTime.plusHours(scheduleElement.getTime_late().getHour()).plusMinutes(scheduleElement.getTime_late().getMinute());
            }
        }

        return resultScheduleDtoList;
    }

    /**
     * Method for adding schedule for just created trip
     *
     * @param tripId tripId
     * @param routeId routeId
     * @param stationStopTimes stationStopTimes
     * @param tripStartTime tripStartTime
     * @param trainId trainId
     */
    @Transactional
    public void addScheduleForTrip(Integer tripId, Integer routeId, String stationStopTimes, String tripStartTime, Integer trainId) {
        log.info("Adding schedule for new just created trip:");
        log.info("stationStopTimes: " + stationStopTimes);

        Trip trip = tripDAO.findById(tripId);
        Instant tripStartInstant = trip.getStart_time();
        Train chosenTrain = trainDAO.findById(trainId);
        Double trainSpeed = chosenTrain.getSpeed();

        List<String> stationsStopsInHoursAndMinutes = new ArrayList<>(Arrays.asList(stationStopTimes.split(",")));

        List<Long> stationTimeStops = ConverterUtil.parseMilliseconds(stationsStopsInHoursAndMinutes);

        List<Route> routes = routeDAO.findRouteByRouteId(routeId);
        List<Station> routeStations = new ArrayList<>();
        routes.forEach(route -> routeStations.add(route.getStation()));

        List<Schedule> resultScheduleList = new ArrayList<>();

        Station tempStation = routeStations.get(0);
        Instant tempInstant = ConverterUtil.parseInstant(tripStartTime);
        log.info("@@tempTimestamp: " + tempInstant.toString());
        for (int i = 0; i < routeStations.size(); i++) {
            if (i == 0) { // First schedule row.
                Schedule schedule = new Schedule(trip, tempInstant, routeStations.get(i), LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm")));
                scheduleDAO.add(schedule);
                resultScheduleList.add(schedule);
                log.info("@@tempTimestamp IN 1 IF: " + tempInstant.toString());
                tempStation = routeStations.get(i);
            } else if (i != routeStations.size() - 1) { // All schedule row excerpt first and last.
                Station current = routeStations.get(i);
                Double distance = HaversineUtil.calculateDistanceInKm(Converter.getStationDto(tempStation), Converter.getStationDto(current));
                Long milliseconds = (long) (((distance / trainSpeed) * 60) * 60) * 1000;
                Instant currentInstant = Instant.ofEpochMilli(tempInstant.toEpochMilli() + milliseconds);
                log.info("@@currentTimestamp IN 2 IF: (without toString())" + currentInstant);
                Schedule schedule = new Schedule(trip, routeStations.get(i), currentInstant, LocalTime.parse(stationsStopsInHoursAndMinutes.get(i), DateTimeFormatter.ofPattern("HH:mm")), Instant.ofEpochMilli(currentInstant.toEpochMilli() + stationTimeStops.get(i)), LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm")));
                resultScheduleList.add(schedule);
                scheduleDAO.add(schedule);
                tempStation = current;
                tempInstant = currentInstant;
            } else { // Last schedule row.
                Station current = routeStations.get(i);
                Double distance = HaversineUtil.calculateDistanceInKm(Converter.getStationDto(tempStation), Converter.getStationDto(current));
                Long milliseconds = (long) (((distance / trainSpeed) * 60) * 60) * 1000;
                Instant currentInstant = Instant.ofEpochMilli(tempInstant.toEpochMilli() + milliseconds);
                log.info("@@currentTimestamp IN 3 IF: " + currentInstant);
                Schedule schedule = new Schedule(trip, routeStations.get(i), currentInstant, LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm")));
                resultScheduleList.add(schedule);
                scheduleDAO.add(schedule);
            }
        }

        // This loop sends schedule objects to ActiveMQ.
        Instant today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        Instant tomorrow = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        for (int i = 0; i < resultScheduleList.size(); i++) {
            if (i == 0) {
                if ((tripStartInstant.getEpochSecond() >= today.getEpochSecond()) && (tripStartInstant.getEpochSecond() <= tomorrow.getEpochSecond())) {
                    try {
                        messageSender.sendMessage(objectMapper.writeValueAsString(Converter.getScheduleDto(resultScheduleList.get(0))));
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    break;
                }
            } else {
                if ((resultScheduleList.get(i).getTime_arrival().getEpochSecond() > today.getEpochSecond()) && (resultScheduleList.get(i).getTime_arrival().getEpochSecond() < tomorrow.getEpochSecond())) {
                    try {
                        messageSender.sendMessage(objectMapper.writeValueAsString(Converter.getScheduleDto(resultScheduleList.get(i))));
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

    }

    /**
     * Method for getting schedule by tripId
     *
     * @param tripId tripId
     * @return schedule
     */
    @Transactional
    public List<ScheduleDTO> getSchedulesByTripId(Integer tripId) {
        return Converter.getScheduleDtos(scheduleDAO.getSchedulesByTripId(tripId));
    }

    /**
     * Method for managing lateness for specified scheduleId
     *
     * @param scheduleId scheduleId
     * @param time_late time_late
     */
    @Transactional
    public void editLateStationSchedule(Integer scheduleId, String time_late) {
        LocalTime timeLate = LocalTime.parse(time_late, DateTimeFormatter.ofPattern("HH:mm"));
        scheduleDAO.editLateStationSchedule(scheduleId, timeLate);
        Schedule schedule = scheduleDAO.findById(scheduleId);
        Trip scheduleTrip = schedule.getTrip();
        List<Schedule> tripSchedules = scheduleDAO.getSchedulesByTripId(scheduleTrip.getId());
        Instant today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        Instant tomorrow = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        LocalTime summaryTimeLate = LocalTime.of(0, 0);
        ScheduleDTO scheduleJms = null;
        for (Schedule scheduleElement : tripSchedules) {
            if (scheduleElement.getId() >= scheduleId) {
                if (scheduleElement.getTime_arrival() != null) {
                    if ((scheduleElement.getTime_arrival().getEpochSecond() >= today.getEpochSecond()) && (scheduleElement.getTime_arrival().getEpochSecond() <= tomorrow.getEpochSecond())) {
                        scheduleJms = Converter.getScheduleDto(scheduleElement);
                        scheduleJms.setTime_late(summaryTimeLate);
                        summaryTimeLate = summaryTimeLate.plusHours(scheduleElement.getTime_late().getHour()).plusMinutes(scheduleElement.getTime_late().getMinute());
                        try {
                            messageSender.sendMessage(objectMapper.writeValueAsString(scheduleJms));
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                        }
                    } else break;
                } else {
                    if ((scheduleTrip.getStart_time().getEpochSecond() >= today.getEpochSecond()) && (scheduleTrip.getStart_time().getEpochSecond() <= tomorrow.getEpochSecond())) {
                        scheduleJms = Converter.getScheduleDto(scheduleElement);
                        scheduleJms.setTime_late(summaryTimeLate);
                        summaryTimeLate = summaryTimeLate.plusHours(scheduleElement.getTime_late().getHour()).plusMinutes(scheduleElement.getTime_late().getMinute());
                        try {
                            messageSender.sendMessage(objectMapper.writeValueAsString(scheduleJms));
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                        }
                    } else break;
                }
            }
            if (scheduleElement.getId() < scheduleId) {
                summaryTimeLate = summaryTimeLate.plusHours(scheduleElement.getTime_late().getHour()).plusMinutes(scheduleElement.getTime_late().getMinute());
            }
        }

    }

}
