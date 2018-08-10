package com.tsystems.service.api;

import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.entity.Trip;

import java.time.Instant;
import java.util.List;

public interface TripService {
    void addTrip(Trip trip);

    TripDTO findById(Integer id);

    void deleteTrip(Trip trip);

    List<TripDTO> getAll();

    Integer getLastId();

    boolean isTripAvailableForUser(Integer tripId, String userName);

    List<TripDTO> findValidTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval);

    Integer createTrip(Integer trainId, Integer routeId, String tripStartTime);

    void cancelTrip(Integer tripId);

    void addLateTime(Integer tripId, String timeLate);

    Instant getDepartureTime(Integer tripId);

    Instant getArrivalTime(Integer tripId, String stationToName);

    List<TicketDTO> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);
}
