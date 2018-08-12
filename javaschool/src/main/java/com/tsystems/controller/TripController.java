package com.tsystems.controller;

import com.tsystems.dto.TripDTO;
import com.tsystems.service.api.*;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;

@Controller
public class TripController {
    private TripService tripService;
    private ScheduleService scheduleService;

    @Autowired
    public TripController(TripService tripService, ScheduleService scheduleService) {
        this.tripService = tripService;
        this.scheduleService = scheduleService;
    }

    private static final Logger log = Logger.getLogger(TripController.class);

    @GetMapping("/trip/find/")
    public ModelAndView findTripPage(
            ModelAndView model,
            @RequestParam(value = "stationFrom", required = false) String stationFromName,
            @RequestParam(value = "stationTo", required = false) String stationToName,
            @RequestParam(value = "dateStart", required = false) String startSearchInterval,
            @RequestParam(value = "dateEnd", required = false) String endSearchInterval) {
        log.info("Searching trips.....");
        if (stationFromName != null) {
            model.addObject("stationFrom", stationFromName);
        }
        if (stationToName != null) {
            model.addObject("stationTo", stationToName);
        }
        if (startSearchInterval != null) {
            model.addObject("dateStart", startSearchInterval);
        }
        if (endSearchInterval != null) {
            model.addObject("dateEnd", endSearchInterval);
        }
        model.setViewName("search_trip");
        return model;
    }

    @PostMapping("/trip/find/")
    public @ResponseBody
    String findTrips(
            @RequestParam("stationFrom") String stationFromName,
            @RequestParam("stationTo") String stationToName,
            @RequestParam("dateStart") String startSearchInterval,
            @RequestParam("dateEnd") String endSearchInterval) {
        List<TripDTO> validTrips = tripService.findValidTrips(stationFromName, stationToName, startSearchInterval, endSearchInterval);
        if (validTrips.size() > 0) {
            return ConverterUtil.parseJson(validTrips);
        } else {
            log.info("There were 0 trips found by user's parameters.");
            return "0";
        }
    }

    @GetMapping("/admin/trip/list")
    public String allRoutesPage() {
        return "admin/trip_list";
    }

    @GetMapping("/admin/trip/list/get")
    public @ResponseBody String getAllTrips() {
        return ConverterUtil.parseJson(tripService.getAll());
    }

    // TODO: Проверка: а существует ли такой маршрут?
    @GetMapping("/admin/trip/create/{routeId}")
    public ModelAndView createTripPageNew(ModelAndView model, @PathVariable("routeId") Integer routeId) {
        model.addObject("routeId", routeId);
        model.setViewName("admin/trip_create");
        return model;
    }

    @PostMapping("/admin/trip/create")
    public String createTripPost(
            @RequestParam("trainId") Integer trainId,
            @RequestParam("routeId") Integer routeId,
            @RequestParam("stationStopTimes") String stationStopTimes,
            @RequestParam("startTime") String tripStartTime) {
        // stationStopTimes = Time duration when train will stop at the station
        // Example of that: 00:05,00:05,00:05,00:05
        log.info("--------- Creating new trip ------------");
        log.info("trainId: " + trainId);
        log.info("routeId: " + routeId);
        log.info("stationStopTimes: " + stationStopTimes);
        log.info("tripStartTime: " + tripStartTime);
        Integer tripId = tripService.createTrip(trainId, routeId, tripStartTime);
        scheduleService.addScheduleForTrip(tripId, routeId, stationStopTimes, tripStartTime, trainId);
        return "redirect:/admin/trip/list";
    }

    @PostMapping("/admin/trip/cancel")
    public String cancelTrip(
            @RequestParam("tripId") Integer tripId
    ) {
        tripService.cancelTrip(tripId);
        return "redirect:/admin/trip/list";
    }

    @PostMapping("/admin/trip/late/add")
    public String addLateTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("timeLate") String timeLate
    ) {
        tripService.addLateTime(tripId, timeLate);
        return "redirect:/admin/trip/list";
    }

    @GetMapping("/trip/departure-time")
    public @ResponseBody Instant getDepartureTime(
            @RequestParam("tripId") Integer tripId
    ) {
        return tripService.getDepartureTime(tripId);
    }

    @GetMapping("/trip/arrival-time")
    public @ResponseBody Instant getArrivalTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationTo") String stationTo
    )
    {
        return tripService.getArrivalTime(tripId, stationTo);
    }

    @GetMapping("/admin/trip/passengers")
    public ModelAndView passengersPage(
            ModelAndView model,
            @RequestParam("tripId") Integer tripId,
            @RequestParam("carriageNum") Integer carriageNum
    ) {
        model.addObject("trip", tripService.findById(tripId));
        model.addObject("carriageNum", carriageNum);
        model.setViewName("admin/passengers_list");
        return model;
    }

    @GetMapping("/admin/trip/passengers/get")
    public @ResponseBody String getTripPassengersByCarriageNum(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("carriageNum") Integer carriageNum
    ) {
        return ConverterUtil.parseJson(tripService.getTicketsByTripAndCarriageNum(tripId, carriageNum));
    }

    @GetMapping("/template")
    public String templatePage() {
        return "template";
    }

}
