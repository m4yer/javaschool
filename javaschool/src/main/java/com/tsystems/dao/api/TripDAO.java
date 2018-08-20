package com.tsystems.dao.api;

import com.tsystems.entity.Ticket;
import com.tsystems.entity.Trip;

import java.time.Instant;
import java.util.List;

/**
 * CRUD and specific operations
 */
public interface TripDAO extends GenericDAO<Trip, Integer> {

    /**
     * Get last trip id
     *
     * @return last trip id
     */
    Integer getLastId();

    /**
     * Finds and returns valid trips for specified parameters
     *
     * @param searchedRouteId searchedRouteId
     * @param startTime startTime
     * @param endTime endTime
     * @return list of valid trips
     */
    List<Trip> findValidTrips(Integer searchedRouteId, Instant startTime, Instant endTime);

    /**
     * Checks if trip available for user
     *
     * @param tripId tripId
     * @param userName userName
     * @return true if available, false otherwise
     */
    boolean isTripAvailableForUser(Integer tripId, String userName);

    /**
     * Cancels trip by tripId and returns its startTime
     *
     * @param tripId tripId
     * @return cancelled trip startTime
     */
    Instant cancelTrip(Integer tripId);

    /**
     * Gets departure time for tripId and stationName
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @return arrival time
     */
    Instant getDepartureTime(Integer tripId, String stationFromName);

    /**
     * Gets arrival time for tripId and stationName
     *
     * @param tripId tripId
     * @param stationToName stationToName
     * @return arrival time
     */
    Instant getArrivalTime(Integer tripId, String stationToName);

    /**
     * Gets tickets by tripId and carriageNum
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return list of tickets
     */
    List<Ticket> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);

    /**
     * Finds and returns active trips by routeId
     *
     * @param routeId routeId
     * @return list of activeTrips
     */
    List<Trip> findActiveTripsByRouteId(Integer routeId);

}
