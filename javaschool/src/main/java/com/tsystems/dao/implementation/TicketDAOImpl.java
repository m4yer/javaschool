package com.tsystems.dao.implementation;

import com.tsystems.dao.api.TicketDAO;
import com.tsystems.entity.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * An implementation of TicketDAO api
 */
@Repository
public class TicketDAOImpl extends GenericDAOImpl<Ticket, Integer> implements TicketDAO {

    /**
     * Method for getting all booked ticket by tripId and carriageNum
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @param stationsInterval stationsInterval
     * @param carriageNum carriageNum
     * @return list of booked tickets
     */
    public List<Ticket> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, List<String> stationsInterval, Integer carriageNum) {
        Query findTickets = entityManager.createQuery("select ticket from Ticket ticket where ticket.carriage_num=:carriageNum " +
                "and ticket.trip.id=:tripId " +
                "and ticket.station_from.name in (:stationsInterval) " +
                "and ticket.station_to.name in (:stationsInterval)");
        findTickets.setParameter("carriageNum", carriageNum);
        findTickets.setParameter("tripId", tripId);
        findTickets.setParameter("stationsInterval", stationsInterval);
        return (List<Ticket>) findTickets.getResultList();
    }

    /**
     * Method for getting all booked ticket AMOUNT for tripId and CarriageNum
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return amount of booked tickets
     */
    public Integer getBookedTicketAmountForTripAndCarriageNum(Integer tripId, Integer carriageNum) {
        Query getAmount = entityManager.createQuery("select ticket from Ticket ticket where ticket.trip.id=:tripId and ticket.carriage_num=:carriageNum");
        getAmount.setParameter("tripId", tripId);
        getAmount.setParameter("carriageNum", carriageNum);
        return getAmount.getResultList().size();
    }

    /**
     * Method for getting all tickets for userId
     *
     * @param userId userId
     * @return list of tickets
     */
    public List<Ticket> getUserTicketList(Integer userId) {
        Query getTickets = entityManager.createQuery("select ticket from Ticket ticket where ticket.user.id=:userId");
        getTickets.setParameter("userId", userId);
        return (List<Ticket>) getTickets.getResultList();
    }

}
