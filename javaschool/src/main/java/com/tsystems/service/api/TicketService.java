package com.tsystems.service.api;

import com.tsystems.dto.TicketDTO;
import com.tsystems.entity.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketService {

    /**
     * Finds and returns ticket by id
     *
     * @param id ticketId
     * @return ticket
     */
    TicketDTO findById(Integer id);

    /**
     * Returns all tickets
     *
     * @return list of tickets
     */
    List<Ticket> getAll();

    /**
     * Method for getting all booked tickets by tripId and carriageNum
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param carriageNum carriageNum
     * @return list of booked tickets
     */
    List<TicketDTO> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, String stationToName, Integer carriageNum);

    /**
     * Method for processing buying tickets and returning result of buying
     *
     * @param tripId tripId
     * @param userName userName
     * @param seatId seatId
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param carriageNum carriageNum
     * @return result of buying
     */
    String buyTicket(Integer tripId, String userName, Integer seatId, String stationFromName, String stationToName, Integer carriageNum);

    /**
     * Method for getting amount of booked tickets for trip
     *
     * @param tripId tripId
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @return map of <carriageNum, amount of booked tickets>
     */
    Map<Integer, Integer> getBookedTicketsAmountForTrip(Integer tripId, String stationFromName, String stationToName);

    /**
     * Returns all user tickets
     *
     * @param userId userId
     * @return list of user tickets
     */
    List<TicketDTO> getUserTicketList(Integer userId);

    /**
     * Checks if ticket belongs to user
     *
     * @param ticketId ticketId
     * @param userId userId
     * @return true if belongs, false otherwise
     */
    boolean isTripBelongsUser(Integer ticketId, Integer userId);

}
