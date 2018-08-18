package com.tsystems.dao.api;

import com.tsystems.entity.Ticket;
import com.tsystems.entity.Trip;

import java.time.Instant;
import java.util.List;

public interface TripDAO extends GenericDAO<Trip, Integer> {

    Integer getLastId();

    List<Trip> findValidTrips(Integer searchedRouteId, Instant startTime, Instant endTime);

    boolean isTripAvailableForUser(Integer tripId, String userName);

    Instant cancelTrip(Integer tripId);

    Instant getDepartureTime(Integer tripId);

    Instant getArrivalTime(Integer tripId, String stationToName);

    List<Ticket> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);

}
