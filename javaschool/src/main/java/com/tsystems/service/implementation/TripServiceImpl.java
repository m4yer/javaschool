package com.tsystems.service.implementation;

import com.tsystems.entity.converter.Converter;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.dao.api.StationDAO;
import com.tsystems.dao.api.TrainDAO;
import com.tsystems.dao.api.TripDAO;
import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.entity.Trip;
import com.tsystems.jms.SimpleMessageSender;
import com.tsystems.service.api.TripService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TripServiceImpl implements TripService {
    @Autowired
    TripDAO tripDAO;
    @Autowired
    RouteDAO routeDAO;
    @Autowired
    StationDAO stationDAO;
    @Autowired
    TrainDAO trainDAO;
    @Autowired
    SimpleMessageSender messageSender;

    public void setTripDAO(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Transactional
    public void addTrip(Trip trip) {
        tripDAO.add(trip);
    }

    @Transactional
    public TripDTO findById(Integer id) {
        return Converter.getTripDto(tripDAO.findById(id));
    }

    @Transactional
    public void deleteTrip(Trip trip) {
        tripDAO.delete(trip);
    }

    @Override
    public List<TripDTO> getAll() {
        return Converter.getTripDtos(tripDAO.getAll());
    }

    @Transactional
    public Integer getLastId() {
        return tripDAO.getLastId();
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

        Instant startTime = ConverterUtil.parseInstant(startSearchInterval);
        Instant endTime = ConverterUtil.parseInstant(endSearchInterval);

        List<Trip> resultTripList = new ArrayList<>();

        for (Integer routeId : validRouteIds) {
            List<Trip> trips = tripDAO.findValidTrips(routeId, startTime, endTime);
            resultTripList.addAll(trips);
        }

        List<TripDTO> validTripDtos = Converter.getTripDtos(resultTripList);

        return validTripDtos;
    }

    /**
     * This method adds new route and returns it's id
     *
     * @param trainId trainId of new trip
     * @param routeId routeId of new trip
     * @param tripStartTime start time of new trip
     * @return id of new added route
     */
    @Transactional
    public Integer createTrip(Integer trainId, Integer routeId, String tripStartTime) {
        Trip newTrip = new Trip(trainDAO.findById(trainId), routeId, ConverterUtil.parseInstant(tripStartTime), true);
        tripDAO.add(newTrip);
        return tripDAO.getLastId();
    }

    @Transactional
    public void cancelTrip(Integer tripId) {
        tripDAO.cancelTrip(tripId);

        // Entire code below is to send messages to ActiveMQ
        Trip currentTrip = tripDAO.findById(tripId);

        Integer routeId = currentTrip.getRoute_id();
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);

        List<Station> routeStations = new ArrayList<>();
        for (Route route : routes) {
            routeStations.add(route.getStation());
        }

        for (Station station : routeStations) {
            messageSender.sendMessage(station.getName());
        }

    }

    @Transactional
    public void addLateTime(Integer tripId, String timeLate) {
        tripDAO.addLateTime(tripId, timeLate);

        // Entire code below is to send messages to ActiveMQ
        Trip currentTrip = tripDAO.findById(tripId);

        Integer routeId = currentTrip.getRoute_id();
        List<Route> routes = routeDAO.findRouteByRouteId(routeId);

        List<Station> routeStations = new ArrayList<>();
        for (Route route : routes) {
            routeStations.add(route.getStation());
        }

        for (Station station : routeStations) {
            messageSender.sendMessage(station.getName());
        }
    }

    @Transactional
    public Instant getDepartureTime(Integer tripId) {
        return tripDAO.getDepartureTime(tripId);
    }

    @Transactional
    public Instant getArrivalTime(Integer tripId, String stationToName) {
        return tripDAO.getArrivalTime(tripId, stationToName);
    }

    public List<TicketDTO> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum) {
        return Converter.getTicketDtos(tripDAO.getTicketsByTripAndCarriageNum(tripId, carriageNum));
    }

}
