package com.tsystems.dao.api;

import com.tsystems.entity.Ticket;

import java.util.List;

public interface TicketDAO extends GenericDAO<Ticket, Integer> {

    List<Ticket> getBookedTicketsByTripAndCarriageNum(Integer tripId, String stationFromName, List<String> stationsInterval, Integer carriageNum);

    Integer getBookedTicketAmountForTripAndCarriageNum(Integer tripId, Integer carriageNum);

    List<Ticket> getUserTicketList(Integer userId);

}
