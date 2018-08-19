package com.tsystems.service.api;

import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.entity.Trip;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface TripService {

    TripDTO findById(Integer id);

    List<TripDTO> getAll();

    boolean isTripAvailableForUser(Integer tripId, String userName);

    List<TripDTO> findValidTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval);

    Map<String, String> findValidPartialTrips(String stationFromName, String stationToName, String startSearchInterval, String endSearchInterval);

    Integer createTrip(Integer trainId, Integer routeId, String tripStartTime);

    void cancelTrip(Integer tripId);

    Instant getDepartureTime(Integer tripId, String stationFromName);

    Instant getArrivalTime(Integer tripId, String stationToName);

    List<Instant> getPartialTime(Integer tripId, String stationFromName, String stationToName);

    List<TicketDTO> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum);

    List<TripDTO> findActiveTripsByRouteId(Integer routeId);
}
