package com.tsystems.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dao.api.*;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Schedule;
import com.tsystems.entity.Station;
import com.tsystems.entity.Trip;
import com.tsystems.entity.converter.Converter;
import com.tsystems.jms.SimpleMessageSender;
import com.tsystems.service.api.TripService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripServiceImpl implements TripService {
    private TripDAO tripDAO;
    private RouteDAO routeDAO;
    private StationDAO stationDAO;
    private ScheduleDAO scheduleDAO;
    private TrainDAO trainDAO;
    private SimpleMessageSender messageSender;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public TripServiceImpl(TripDAO tripDAO, RouteDAO routeDAO, StationDAO stationDAO, ScheduleDAO scheduleDAO, TrainDAO trainDAO, SimpleMessageSender messageSender) {
        this.tripDAO = tripDAO;
        this.routeDAO = routeDAO;
        this.stationDAO = stationDAO;
        this.scheduleDAO = scheduleDAO;
        this.trainDAO = trainDAO;
        this.messageSender = messageSender;
    }

    private static final Logger log = Logger.getLogger(TripServiceImpl.class);

    @Transactional
    public TripDTO findById(Integer id) {
        return Converter.getTripDto(tripDAO.findById(id));
    }

    @Override
    public List<TripDTO> getAll() {
        return Converter.getTripDtos(tripDAO.getAll());
    }

    @Transactional
    public boolean isTripAvailableForUser(Integer tripId, String userName) {
        return tripDAO.isTripAvailableForUser(tripId, userName);
    }

    @Transactional
    public List<TripDTO> findValidTrips(String stationFromName,
                                        String stationToName,
                                        String startSearchInterval,
                                        String endSearchInterval) {
        Station stationFrom = stationDAO.findByName(stationFromName);
        Station stationTo = stationDAO.findByName(stationToName);
        List<Integer> validRouteIds = new ArrayList<>();
        List<Integer> routeIds = routeDAO.getSingleRoutesId();
        for (Integer routeId : routeIds) {
            List<Station> routeStations = new ArrayList<>();
            List<Route> routes = routeDAO.findRouteByRouteId(routeId);
            for (Route route : routes) {
                routeStations.add(route.getStation());
            }
            if (routeStations.contains(stationFrom)) {
                // There is station A is this route if we are here
                if (routeStations.contains(stationTo)) {
                    // There is station B is this route if we are here
                    if (routeStations.indexOf(stationFrom) < routeStations.indexOf(stationTo)) {
                        // This route is what we actually want.
                        validRouteIds.add(routeId);
                    }
                }
            }
        }

        Instant startTime;
        Instant endTime;

        if (startSearchInterval.length() == 20) {
            startTime = Instant.parse(startSearchInterval);
        } else {
            startTime = ConverterUtil.parseInstant(startSearchInterval);
        }
        if (endSearchInterval.length() == 20) {
            endTime = Instant.parse(endSearchInterval);
        } else {
            endTime = ConverterUtil.parseInstant(endSearchInterval);
        }

        List<Trip> resultTripList = new ArrayList<>();

        for (Integer routeId : validRouteIds) {
            List<Trip> trips = tripDAO.findValidTrips(routeId, startTime, endTime);
            resultTripList.addAll(trips);
        }

        List<TripDTO> validTripDtos = Converter.getTripDtos(resultTripList);

        return validTripDtos;
    }

    @Transactional
    public Map<String, String> findValidPartialTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval) {
        List<Integer> allRouteIds = routeDAO.getSingleRoutesId();
        List<Integer> validRoutesA = new ArrayList<>();
        List<Integer> validRoutesB = new ArrayList<>();

        allRouteIds.forEach(routeId -> {
            List<Station> routeStations = new ArrayList<>();
            routeDAO.findRouteByRouteId(routeId).forEach(route -> routeStations.add(route.getStation()));
            routeStations.forEach(station -> {
                if (station.getName().equals(stationFromName)) {
                    validRoutesA.add(routeId);
                }
                if (station.getName().equals(stationToName)) {
                    validRoutesB.add(routeId);
                }
            });
        });

        Map<List<String>, List<String>> validPartialRoutes = getValidPartialRoutes(validRoutesA, validRoutesB, stationFromName, stationToName);

        Map<String, String> resultValidPartialTrips = new HashMap<>();

        for (Map.Entry<List<String>, List<String>> entry : validPartialRoutes.entrySet()) {
            List<String> transferStations = new ArrayList<>();
            List<TripDTO> validPairOfTrips = new ArrayList<>();
            entry.getKey().forEach(transferStation -> {
                List<TripDTO> validTripsA = findValidTrips(stationFromName, transferStation, startSearchInterval, endSearchInterval);
                if (validTripsA.size() > 0) {
                    validTripsA.forEach(tripA -> {
                        scheduleDAO.getSchedulesByTripId(tripA.getId()).forEach(scheduleA -> {
                            if (scheduleA.getStation().getName().equals(transferStation)) {
                                String startSearchIntervalForTripB = scheduleA.getTime_arrival().toString();
                                String endSearchIntervalForTripB = scheduleA.getTime_arrival().plus(5, ChronoUnit.DAYS).toString();
                                List<TripDTO> validTripsB = findValidTrips(transferStation, stationToName, startSearchIntervalForTripB, endSearchIntervalForTripB);
                                if (validTripsB.size() > 0) {
                                    transferStations.add(transferStation);
                                    validPairOfTrips.add(tripA);
                                    validPairOfTrips.add(validTripsB.get(0));
                                    StringBuilder stationSequence = new StringBuilder();
                                    for (int i = 0; i < transferStations.size(); i++) {
                                        if (i != transferStations.size() - 1) {
                                            stationSequence.append(transferStations.get(i) + ",");
                                        } else {
                                            stationSequence.append(transferStations.get(i));
                                        }
                                    }
                                    resultValidPartialTrips.put(stationSequence.toString(), ConverterUtil.parseJson(validPairOfTrips));
                                    transferStations.clear();
                                    validPairOfTrips.clear();
                                }
                            }
                        });
                    });
                }
            });
        }
        System.out.println(resultValidPartialTrips);
        return resultValidPartialTrips;
    }

    private Map<List<String>, List<String>> getValidPartialRoutes(List<Integer> validRoutesA, List<Integer> validRoutesB, String stationFromName, String stationToName) {
        Map<List<String>, List<String>> validPartialRoutes = new HashMap<>();
        List<String> stationTransfers = new ArrayList<>();
        List<String> routeSequence = new ArrayList<>();

        validRoutesA.forEach(routeId -> {
            List<Station> routeStationsA = new ArrayList<>();
            routeDAO.findRouteByRouteId(routeId).forEach(route -> routeStationsA.add(route.getStation()));

            validRoutesB.forEach(routeIdB -> {
                List<Station> routeStationsB = new ArrayList<>();
                routeDAO.findRouteByRouteId(routeIdB).forEach(routeB -> routeStationsB.add(routeB.getStation()));

                routeStationsA.forEach(stationA -> {

                    routeStationsB.forEach(stationB -> {

                        if (stationA.getName().equals(stationB.getName())) {
                            int stationFromIndex = 0;
                            for (int i = 0; i < routeStationsA.size(); i++) {
                                if (routeStationsA.get(i).getName().equals(stationFromName)) {
                                    stationFromIndex = i;
                                    break;
                                }
                            }
                            int stationToIndex = 0;
                            for (int i = 0; i < routeStationsB.size(); i++) {
                                if (routeStationsB.get(i).getName().equals(stationToName)) {
                                    stationToIndex = i;
                                    break;
                                }
                            }
                            if ((routeStationsA.indexOf(stationA) > stationFromIndex) && (routeStationsB.indexOf(stationB) < stationToIndex)) {
                                String routeSequenceTemp = routeId + "-" + routeIdB;
                                if (!routeSequence.contains(routeSequenceTemp)) {
                                    routeSequence.add(routeId + "-" + routeIdB);
                                    stationTransfers.add(stationA.getName());
                                }

                            }
                        }
                    });

                });
            });

        });

        validPartialRoutes.put(stationTransfers, routeSequence);

        return validPartialRoutes;
    }

    /**
     * This method adds new route and returns it's id
     *
     * @param trainId       trainId of new trip
     * @param routeId       routeId of new trip
     * @param tripStartTime start time of new trip
     * @return id of new added route
     */
    @Transactional
    public Integer createTrip(Integer trainId, Integer routeId, String tripStartTime) {
        log.info("Creating new trip");
        log.info("trainId: " + trainId);
        log.info("routeId: " + routeId);
        log.info("tripStartTime: " + tripStartTime);
        Trip newTrip = new Trip(trainDAO.findById(trainId), routeId, ConverterUtil.parseInstant(tripStartTime), true);
        tripDAO.add(newTrip);
        return tripDAO.getLastId();
    }

    @Transactional
    public void cancelTrip(Integer tripId) {
        Trip currentTrip = tripDAO.findById(tripId);
        currentTrip.setActive(false);

        // Entire code below is to send messages to ActiveMQ
        List<Schedule> tripSchedules = scheduleDAO.getSchedulesByTripId(tripId);
        Instant today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        Instant tomorrow = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        LocalTime summaryTimeLate = LocalTime.of(0, 0);
        ScheduleDTO scheduleJms = null;
        for (Schedule scheduleElement : tripSchedules) {
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
                if ((currentTrip.getStart_time().getEpochSecond() >= today.getEpochSecond()) && (currentTrip.getStart_time().getEpochSecond() <= tomorrow.getEpochSecond())) {
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
    }

    @Transactional
    public Instant getDepartureTime(Integer tripId, String stationFromName) {
        return tripDAO.getDepartureTime(tripId, stationFromName);
    }

    @Transactional
    public Instant getArrivalTime(Integer tripId, String stationToName) {
        return tripDAO.getArrivalTime(tripId, stationToName);
    }

    @Transactional
    public List<Instant> getPartialTime(Integer tripId, String stationFromName, String stationToName) {
        List<Instant> resultList = new ArrayList<>();
        resultList.add(tripDAO.getArrivalTime(tripId, stationToName));
        resultList.add(tripDAO.getDepartureTime(tripId, stationFromName));
        return resultList;
    }

    @Transactional
    public List<TicketDTO> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum) {
        return Converter.getTicketDtos(tripDAO.getTicketsByTripAndCarriageNum(tripId, carriageNum));
    }

    @Transactional
    public List<TripDTO> findActiveTripsByRouteId(Integer routeId) {
        return Converter.getTripDtos(tripDAO.findActiveTripsByRouteId(routeId));
    }

}
