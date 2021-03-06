package com.tsystems.dao.implementation;

import com.tsystems.dao.api.TripDAO;
import com.tsystems.entity.Ticket;
import com.tsystems.entity.Trip;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

/**
 * An implementation of TripDAO api
 */
@Repository
public class TripDAOImpl extends GenericDAOImpl<Trip, Integer> implements TripDAO {

    /**
     * Get last trip id
     *
     * @return last trip id
     */
    @Transactional
    public Integer getLastId() {
        Query getLastId = entityManager.createQuery("select trip.id from Trip trip order by trip.id desc");
        getLastId.setMaxResults(1);
        Integer lastId = (Integer) getLastId.getResultList().get(0);
        return lastId;
    }

    /**
     * Finds and returns valid trips for specified parameters
     *
     * @param searchedRouteId searchedRouteId
     * @param startTime startTime
     * @param endTime endTime
     * @return list of valid trips
     */
    @Transactional
    public List<Trip> findValidTrips(Integer searchedRouteId, Instant startTime, Instant endTime) {
        Query findTrips = entityManager.createQuery("select trip from Trip trip where (trip.route_id=:routeId) " +
                "and (trip.start_time between :startTime and :endTime) order by trip.route_id asc");
        findTrips.setParameter("routeId", searchedRouteId);
        findTrips.setParameter("startTime", startTime);
        findTrips.setParameter("endTime", endTime);
        return findTrips.getResultList();
    }

    /**
     * Checks if trip available for user
     *
     * @param tripId tripId
     * @param userName userName
     * @return true if available, false otherwise
     */
    public boolean isTripAvailableForUser(Integer tripId, String userName) {
        Query findUserInTrip = entityManager.createQuery("select ticket from Ticket ticket where ticket.user.username=:userName and ticket.trip.id=:tripId");
        findUserInTrip.setParameter("userName", userName);
        findUserInTrip.setParameter("tripId", tripId);
        if (findUserInTrip.getResultList().size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Cancels trip by tripId and returns its startTime
     *
     * @param tripId tripId
     * @return cancelled trip startTime
     */
    public Instant cancelTrip(Integer tripId) {
        Query setTripInactive = entityManager.createQuery("update Trip trip set trip.active=:bool where trip.id=:tripId");
        setTripInactive.setParameter("bool", false);
        setTripInactive.setParameter("tripId", tripId);
        setTripInactive.executeUpdate();
        Query getTripStartTime = entityManager.createQuery("select trip.start_time from Trip trip where trip.id=:tripId");
        getTripStartTime.setParameter("tripId", tripId);
        return (Instant) getTripStartTime.getSingleResult();
    }

    /**
     * Gets departure time for tripId and stationName
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @return arrival time
     */
    public Instant getDepartureTime(Integer tripId, String stationFromName) {
        Query getDepartureTime = entityManager.createQuery("select schedule.time_departure from Schedule schedule where schedule.trip.id=:tripId and schedule.station.name=:stationFromName");
        getDepartureTime.setParameter("tripId", tripId);
        if (!stationFromName.equals("LAST")) {
            getDepartureTime.setParameter("stationFromName", stationFromName);
        } else {
            Query getFirstTripStation = entityManager.createQuery("select schedule.station.name from Schedule schedule where schedule.trip.id=:tripId order by schedule.id asc");
            getFirstTripStation.setParameter("tripId", tripId);
            getFirstTripStation.setMaxResults(1);
            getDepartureTime.setParameter("stationFromName", getFirstTripStation.getResultList().get(0));
        }
        try {
            return (Instant) getDepartureTime.getSingleResult();
        } catch (EntityNotFoundException | NoResultException e) {
            getDepartureTime = entityManager.createQuery("select trip.start_time from Trip trip where trip.id=:tripId");
            getDepartureTime.setParameter("tripId", tripId);
            return (Instant) getDepartureTime.getSingleResult();
        }
    }

    /**
     * Gets arrival time for tripId and stationName
     *
     * @param tripId tripId
     * @param stationToName stationToName
     * @return arrival time
     */
    public Instant getArrivalTime(Integer tripId, String stationToName) {
        Query getArrivalTime = entityManager.createQuery("select schedule.time_arrival from Schedule schedule where schedule.trip.id=:tripId and schedule.station.name=:stationToName");
        getArrivalTime.setParameter("tripId", tripId);
        if (!stationToName.equals("LAST")) {
            getArrivalTime.setParameter("stationToName", stationToName);
        } else {
            Query getLastTripStation = entityManager.createQuery("select schedule.station.name from Schedule schedule where schedule.trip.id=:tripId order by schedule.id desc");
            getLastTripStation.setParameter("tripId", tripId);
            getLastTripStation.setMaxResults(1);
            getArrivalTime.setParameter("stationToName", getLastTripStation.getResultList().get(0));
        }
        try {
            return (Instant) getArrivalTime.getSingleResult();
        } catch (EntityNotFoundException | NoResultException e) {
            return null;
        }
    }

    /**
     * Gets tickets by tripId and carriageNum
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return list of tickets
     */
    public List<Ticket> getTicketsByTripAndCarriageNum(Integer tripId, Integer carriageNum) {
        Query getTickets = entityManager.createQuery("select ticket from Ticket ticket where ticket.trip.id=:tripId and ticket.carriage_num=:carriageNum");
        getTickets.setParameter("tripId", tripId);
        getTickets.setParameter("carriageNum", carriageNum);
        return (List<Ticket>) getTickets.getResultList();
    }

    /**
     * Finds and returns active trips by routeId
     *
     * @param routeId routeId
     * @return list of activeTrips
     */
    public List<Trip> findActiveTripsByRouteId(Integer routeId) {
        Query findActiveTrips = entityManager.createQuery("select trip from Trip trip where trip.route_id=:routeId and trip.active=:active");
        findActiveTrips.setParameter("routeId", routeId);
        findActiveTrips.setParameter("active", true);
        return (List<Trip>) findActiveTrips.getResultList();
    }

}
