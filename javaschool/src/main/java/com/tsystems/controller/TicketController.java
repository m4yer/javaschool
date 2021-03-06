package com.tsystems.controller;

import com.tsystems.dto.TicketDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.service.api.TicketService;
import com.tsystems.service.api.TripService;
import com.tsystems.service.implementation.UserDetails;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Dispatches queries related to tickets
 */
@Controller
public class TicketController {
    private TicketService ticketService;
    private TripService tripService;
    private ScheduleService scheduleService;

    @Autowired
    public TicketController(TicketService ticketService, TripService tripService, ScheduleService scheduleService) {
        this.ticketService = ticketService;
        this.tripService = tripService;
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(TicketController.class);

    /**
     * Returns user tickets page
     *
     * @return user/ticket_list.jsp
     */
    @GetMapping("/user/ticket/list")
    public String ticketListPage() {
        return "user/ticket_list";
    }

    /**
     * Returns PDF for user ticket
     *
     * @param ticketId ticketId
     * @return pdfView
     */
    @GetMapping("/user/ticket/{ticket}.pdf")
    public ModelAndView getTicketPdf(
            @PathVariable("ticket") Integer ticketId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getId();
        if (ticketService.isTripBelongsUser(ticketId, userId)) {
            ModelAndView model = new ModelAndView("pdfView");
            TicketDTO ticket = ticketService.findById(ticketId);
            model.addObject("ticket", ticket);
            model.addObject("schedules", scheduleService.getSchedulesByTripId(ticket.getTrip().getId()));
            return model;
        } else {
            return new ModelAndView("index");
        }
    }

    /**
     * Returns all ticket of specified userId in JSON
     *
     * @param userId userId
     * @return list of user tickets in JSON
     */
    @GetMapping("/user/ticket/list/get/")
    public @ResponseBody
    String getUserTicketList(@RequestParam("userId") Integer userId) {
        return ConverterUtil.parseJson(ticketService.getUserTicketList(userId));
    }

    /**
     * Returns ticket buy page
     *
     * @param model model
     * @param tripId tripId
     * @param tripIds tripIds if it is partial trip
     * @param carriageNum carriageNum
     * @param stationFromName stationFrom name
     * @param lastStationName lastStationName if it is partial trip
     * @param stationToName stationTo name
     * @return user/ticket_buy.jsp
     */
    @GetMapping("/user/ticket/buy/")
    public ModelAndView buyTicketPage(ModelAndView model,
                                      @RequestParam(value = "tripId") Integer tripId,
                                      @RequestParam(value = "tripIds", required = false) String tripIds,
                                      @RequestParam("carriageNum") Integer carriageNum,
                                      @RequestParam("stationFrom") String stationFromName,
                                      @RequestParam(value = "lastStation", required = false) String lastStationName,
                                      @RequestParam("stationTo") String stationToName) {
        TripDTO tripDto = tripService.findById(tripId);
        model.addObject("trip", tripDto);
        model.addObject("carriageNum", carriageNum);
        model.addObject("stationFromName", stationFromName);
        model.addObject("stationToName", stationToName);
        model.addObject("lastStation", lastStationName);
        model.addObject("bookedTickets", ticketService.getBookedTicketsByTripAndCarriageNum(tripId, stationFromName, stationToName, carriageNum));
        model.addObject("seatsAmountMap", ticketService.getBookedTicketsAmountForTrip(tripId, stationFromName, stationToName));
        model.setViewName("user/ticket_buy");
        return model;
    }

    /**
     * Processes ticket buying and returns result of buying
     *
     * @param tripId tripId
     * @param seatId seatId
     * @param stationFromName stationFrom name
     * @param stationToName stationTo name
     * @param carriageNum carriageName
     * @return result of buying
     */
    @PostMapping("/user/ticket/buy/")
    public @ResponseBody
    String buyTicket(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("seatId") Integer seatId,
            @RequestParam("stationFromName") String stationFromName,
            @RequestParam("stationToName") String stationToName,
            @RequestParam("carriageNum") Integer carriageNum) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        log.info("User: " + name + " bought a ticket!");
        return ticketService.buyTicket(tripId, name, seatId, stationFromName, stationToName, carriageNum);
    }

}
