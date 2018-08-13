package com.tsystems.service.api;

import com.tsystems.dto.TicketDTO;
import com.tsystems.entity.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TicketService {

    TicketDTO findById(Integer id);

    List<Ticket> getAll();

    List<TicketDTO> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, String stationToName, Integer carriageNum);

    String buyTicket(Integer tripId, String userName, Integer seatId, String stationFromName, String stationToName, Integer carriageNum);

    Map<Integer, Integer> getBookedTicketsAmountForTrip(Integer tripId, String stationFromName, String stationToName);

    List<TicketDTO> getUserTicketList(Integer userId);

    boolean isTripBelongsUser(Integer ticketId, Integer userId);

}
