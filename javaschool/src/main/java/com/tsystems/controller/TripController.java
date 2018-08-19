package com.tsystems.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.dto.TripDTO;
import com.tsystems.service.api.RouteService;
import com.tsystems.service.api.ScheduleService;
import com.tsystems.service.api.TripService;
import com.tsystems.utils.ConverterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Controller
public class TripController {
    private TripService tripService;
    private ScheduleService scheduleService;
    private RouteService routeService;

    @Autowired
    public TripController(TripService tripService, ScheduleService scheduleService, RouteService routeService) {
        this.tripService = tripService;
        this.scheduleService = scheduleService;
        this.routeService = routeService;
    }

    private static final Logger log = Logger.getLogger(TripController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/user/trip/find/")
    public ModelAndView findTripPage(ModelAndView model,
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

    @GetMapping("/trip/get/")
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

    @GetMapping("/trip/get/partial/")
    public @ResponseBody
    String findPartialTrips(
            @RequestParam("stationFrom") String stationFromName,
            @RequestParam("stationTo") String stationToName,
            @RequestParam("dateStart") String startSearchInterval,
            @RequestParam("dateEnd") String endSearchInterval) {
        Map<String, String> validTrips;
        validTrips = tripService.findValidPartialTrips(stationFromName, stationToName, startSearchInterval, endSearchInterval);
        try {
            return objectMapper.writeValueAsString(validTrips);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping("/admin/trip/list")
    public String allRoutesPage() {
        return "admin/trip_list";
    }

    @GetMapping("/admin/trip/list/get")
    public @ResponseBody
    String getAllTrips() {
        return ConverterUtil.parseJson(tripService.getAll());
    }

    @GetMapping("/admin/trip/create/{routeId}")
    public ModelAndView createTripPageNew(@PathVariable("routeId") Integer routeId) {
        if (routeService.findRouteByRouteId(routeId) != null) {
            return new ModelAndView("admin/trip_create", "routeId", routeId);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping("/admin/trip/create")
    public String createTripPost(
            @RequestParam("trainId") Integer trainId,
            @RequestParam("routeId") Integer routeId,
            @RequestParam("stationStopTimes") String stationStopTimes,
            @RequestParam("startTime") String tripStartTime) {
        Integer tripId = tripService.createTrip(trainId, routeId, tripStartTime);
        scheduleService.addScheduleForTrip(tripId, routeId, stationStopTimes, tripStartTime, trainId);
        return "redirect:/admin/trip/list";
    }

    @PostMapping("/admin/trip/cancel")
    public String cancelTrip(@RequestParam("tripId") Integer tripId) {
        tripService.cancelTrip(tripId);
        return "redirect:/admin/trip/list";
    }

    @GetMapping("/trip/departure-time")
    public @ResponseBody
    Instant getDepartureTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationFrom") String stationFrom) {
        return tripService.getDepartureTime(tripId, stationFrom);
    }

    @GetMapping("/trip/arrival-time")
    public @ResponseBody
    Instant getArrivalTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationTo") String stationTo) {
        return tripService.getArrivalTime(tripId, stationTo);
    }

    @GetMapping("/trip/partial-time")
    public @ResponseBody
    List<Instant> getPartialTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationFrom") String stationFrom,
            @RequestParam("stationTo") String stationTo) {
        log.info("@GetMapping(\"/trip/partial-time\"):");
        log.info("@RequestParam(\"tripId\") Integer tripId: " + tripId);
        log.info("@RequestParam(\"stationFrom\") String stationFrom: " + stationFrom);
        log.info("@RequestParam(\"stationTo\") String stationTo: " + stationTo);
        List<Instant> resultList = tripService.getPartialTime(tripId, stationFrom, stationTo);
        log.info("resultList: " + resultList);
        return tripService.getPartialTime(tripId, stationFrom, stationTo);
    }

    @GetMapping("/admin/trip/passengers")
    public ModelAndView passengersPage(
            ModelAndView model,
            @RequestParam("tripId") Integer tripId,
            @RequestParam("carriageNum") Integer carriageNum) {
        model.addObject("trip", tripService.findById(tripId));
        model.addObject("carriageNum", carriageNum);
        model.setViewName("admin/passengers_list");
        return model;
    }

    @GetMapping("/admin/trip/passengers/get")
    public @ResponseBody
    String getTripPassengersByCarriageNum(
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
