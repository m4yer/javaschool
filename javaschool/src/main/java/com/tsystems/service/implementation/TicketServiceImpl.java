package com.tsystems.service.implementation;

import com.tsystems.controller.converter.Converter;
import com.tsystems.dao.api.*;
import com.tsystems.dto.TicketDTO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.entity.Ticket;
import com.tsystems.entity.Trip;
import com.tsystems.service.api.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    TripDAO tripDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    StationDAO stationDAO;
    @Autowired
    RouteDAO routeDAO;

    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Transactional
    public Ticket findById(Integer id) {
        return ticketDAO.findById(id);
    }

    @Transactional
    public List<Ticket> getAll() {
        return ticketDAO.getAll();
    }

    @Transactional
    public List<TicketDTO> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, String stationToName, Integer carriageNum) {
        Trip trip = tripDAO.findById(tripId);
        List<Route> route = routeDAO.findRouteByRouteId(trip.getRoute_id());
        List<Station> routeStations = new ArrayList<>();
        for (Route routeElement : route) {
            routeStations.add(routeElement.getStation());
        }

        List<String> stationsInterval = new ArrayList<>();
        boolean add = false;
        for (Station station : routeStations) {
            if (station.getName().equals(stationFromName)) {
                add = true;
            } else if (station.getName().equals(stationToName)) {
                add = false;
                stationsInterval.add(station.getName());
            }
            if (add) {
                stationsInterval.add(station.getName());
            }
        }

        return Converter.getTicketDtos(ticketDAO.getBookedTicketsByTripAndCarriageNum(tripId, stationFromName, stationsInterval, carriageNum));
    }

    @Transactional
    public String buyTicket(Integer tripId, String userName, Integer seatId, String stationFromName, String stationToName, Integer carriageNum) {
        Trip chosenTrip = tripDAO.findById(tripId);

        Instant tripDepartingTime = chosenTrip.getStart_time();
        Instant currentTime = Instant.now();
        long timeDifference = tripDepartingTime.toEpochMilli() - currentTime.toEpochMilli();
        long diffMinutes = timeDifference / (60 * 1000);
        if (diffMinutes > 10) {
            // We can sell ticket because until departing >10 minutes

            // Let's check - has user already bought tickets to this trip?
            if (tripDAO.isTripAvailableForUser(tripId, userName)) {
                // We can sell ticket to him because he hasn't bought ticket yet

                ticketDAO.add(new Ticket(
                        chosenTrip,
                        userDAO.findByUsername(userName),
                        seatId,
                        stationDAO.findByName(stationFromName),
                        stationDAO.findByName(stationToName),
                        carriageNum));
                return "success";
            } else {
                return "alreadybought";
            }
        } else {
            return "notavailable";
        }
    }

    @Transactional
    public Map<Integer, Integer> getBookedTicketsAmountForTrip(Integer tripId, String stationFromName, String stationToName) {
        Trip trip = tripDAO.findById(tripId);

        HashMap<Integer, Integer> resultMap = new LinkedHashMap<>();

        Integer carriageAmount = trip.getTrain().getCarriage_amount();
        for (int i = 1; i <= carriageAmount; i++) {
            resultMap.put(i, getBookedTicketsByTripAndCarriageNum(tripId, stationFromName, stationToName, i).size());
        }

        return resultMap;
    }

}
