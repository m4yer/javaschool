package com.tsystems.dao.api;

import com.tsystems.entity.Ticket;

import java.util.List;

/**
 * CRUD and specific operations
 */
public interface TicketDAO extends GenericDAO<Ticket, Integer> {

    /**
     * Method for getting all booked ticket by tripId and carriageNum
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @param stationsInterval stationsInterval
     * @param carriageNum carriageNum
     * @return list of booked tickets
     */
    List<Ticket> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, List<String> stationsInterval, Integer carriageNum);

    /**
     * Method for getting all booked ticket AMOUNT for tripId and CarriageNum
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return amount of booked tickets
     */
    Integer getBookedTicketAmountForTripAndCarriageNum(Integer tripId, Integer carriageNum);

    /**
     * Method for getting all tickets for userId
     *
     * @param userId userId
     * @return list of tickets
     */
    List<Ticket> getUserTicketList(Integer userId);

}
