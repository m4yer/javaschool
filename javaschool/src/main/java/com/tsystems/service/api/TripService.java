package com.tsystems.service.api;

import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface TripService {

    /**
     * Finds and returns trip by id
     *
     * @param id tripId
     * @return trip
     */
    TripDTO findById(Integer id);

    /**
     * Returns all trips
     *
     * @return list of trips
     */
    List<TripDTO> getAll();

    /**
     * For checking if user can buy ticket for this trip
     *
     * @param tripId tripId
     * @param userName userName
     * @return true if he can, false otherwise
     */
    boolean isTripAvailableForUser(Integer tripId, String userName);

    /**
     * Method for finding valid trips by specified parameters
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param startSearchInterval startSearchInterval
     * @param endSearchInterval endSearchInterval
     * @return list of valid trips
     */
    List<TripDTO> findValidTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval);

    /**
     * Method for finding valid partial-trips by specified parameters
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param startSearchInterval startSearchInterval
     * @param endSearchInterval endSearchInterval
     * @return list of valid partial-trips
     */
    Map<String, String> findValidPartialTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval);

    /**
     * Creates new trip with specified parameters
     *
     * @param trainId trainId
     * @param routeId routeId
     * @param tripStartTime tripStartTime
     * @return id of just created trip
     */
    Integer createTrip(Integer trainId, Integer routeId, String tripStartTime);

    /**
     * For cancelling trip by id
     *
     * @param tripId tripId
     */
    void cancelTrip(Integer tripId);

    /**
     * For getting departure time by tripId and stationFrom
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @return departure time
     */
    Instant getDepartureTime(Integer tripId, String stationFromName);

    /**
     * For getting arrival time by tripId and stationTo
     *
     * @param tripId tripId
     * @param stationToName stationToName
     * @return arrival time
     */
    Instant getArrivalTime(Integer tripId, String stationToName);

    /**
     * For getting [departure time, arrival time] specified by trip id, station from and station to
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @return list: [departure time, arrival time]
     */
    List<Instant> getPartialTime(Integer tripId, String stationFromName, String stationToName);

    /**
     * For getting all tickets by tripId and carriageNum
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return list of tickets
     */
    List<TicketDTO> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);

    /**
     * For getting all active trips by routeId
     *
     * @param routeId routeId
     * @return list of active trips
     */
    List<TripDTO> findActiveTripsByRouteId(Integer routeId);
}
