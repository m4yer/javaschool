package com.tsystems.dao.api;

import com.tsystems.entity.Ticket;
import com.tsystems.entity.Trip;

import java.time.Instant;
import java.util.List;

public interface TripDAO extends GenericDAO<Trip, Integer> {

    Integer getLastId();

    List<Trip> findValidTrips(Integer searchedRouteId, Instant startTime, Instant endTime);

    boolean isTripAvailableForUser(Integer tripId, String userName);

    void cancelTrip(Integer tripId);

    void addLateTime(Integer tripId, String timeLate);

    Instant getDepartureTime(Integer tripId);

    Instant getArrivalTime(Integer tripId, String stationToName);

    List<Ticket> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);

}
