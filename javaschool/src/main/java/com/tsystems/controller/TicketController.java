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

    @GetMapping("/user/ticket/list")
    public String ticketListPage() {
        return "user/ticket_list";
    }

    @GetMapping("/user/ticket/{ticket}.pdf")
    public ModelAndView getTicketPdf(
            @PathVariable("ticket") Integer ticketId)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getId();
        if (ticketService.generatePdf(ticketId, userId)) {
            ModelAndView model = new ModelAndView("pdfView");
            TicketDTO ticket = ticketService.findById(ticketId);
            model.addObject("ticket", ticket);
            model.addObject("schedules", scheduleService.getSchedulesByTripId(ticket.getTrip().getId()));
            return model;
        } else {
            return new ModelAndView("index");
        }
    }

    @GetMapping("/user/ticket/list/get/")
    public @ResponseBody
    String getUserTicketList(@RequestParam("userId") Integer userId) {
        return ConverterUtil.parseJson(ticketService.getUserTicketList(userId));
    }

    @GetMapping("/user/ticket/buy/")
    public ModelAndView buyTicketPage(ModelAndView model,
                                      @RequestParam("tripId") Integer tripId,
                                      @RequestParam("carriageNum") Integer carriageNum,
                                      @RequestParam("stationFrom") String stationFromName,
                                      @RequestParam("stationTo") String stationToName) {
        TripDTO tripDto = tripService.findById(tripId);
        model.addObject("trip", tripDto);
        model.addObject("carriageNum", carriageNum);
        model.addObject("stationFromName", stationFromName);
        model.addObject("stationToName", stationToName);
        model.addObject("bookedTickets", ticketService.getBookedTicketsByTripAndCarriageNum(tripId, stationFromName, stationToName, carriageNum));
        model.addObject("seatsAmountMap", ticketService.getBookedTicketsAmountForTrip(tripId, stationFromName, stationToName));
        model.setViewName("user/ticket_buy");
        return model;
    }

    @PostMapping("/user/ticket/buy/")
    public @ResponseBody String buyTicket(
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
