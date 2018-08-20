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

/**
 * Dispatches queries related to trips
 */
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

    /**
     * Returns find trip page
     *
     * @param model model
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param startSearchInterval startSearchInterval
     * @param endSearchInterval endSearchInterval
     * @return search_trip.jsp
     */
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

    /**
     * Returns valid trips for specified parameters
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param startSearchInterval startSearchInterval
     * @param endSearchInterval endSearchInterval
     * @return valid trips matched parameters in JSON
     */
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

    /**
     * Returns valid partial-trips for specified parameters
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     * @param startSearchInterval startSearchInterval
     * @param endSearchInterval endSearchInterval
     * @return valid partial trips matched parameters in JSON
     */
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

    /**
     * Returns trip list page
     *
     * @return admin/trip_list.jsp
     */
    @GetMapping("/admin/trip/list")
    public String allRoutesPage() {
        return "admin/trip_list";
    }

    /**
     * Returns all trips in JSON
     *
     * @return list of trips in JSON
     */
    @GetMapping("/admin/trip/list/get")
    public @ResponseBody
    String getAllTrips() {
        return ConverterUtil.parseJson(tripService.getAll());
    }

    /**
     * Returns create trip page for specified routeId
     *
     * @param routeId routeId
     * @return admin/trip_create.jsp
     */
    @GetMapping("/admin/trip/create/{routeId}")
    public ModelAndView createTripPageNew(@PathVariable("routeId") Integer routeId) {
        if (routeService.findRouteByRouteId(routeId) != null) {
            return new ModelAndView("admin/trip_create", "routeId", routeId);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Processes creating new trip and returns trip list page
     *
     * @param trainId trainId
     * @param routeId routeId
     * @param stationStopTimes list of stop times for stations
     * @param tripStartTime tripStartTime
     * @return admin/trip_list.jsp
     */
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

    /**
     * Processes cancelling of specified tripId
     *
     * @param tripId tripId
     * @return admin/trip_list.jsp
     */
    @PostMapping("/admin/trip/cancel")
    public String cancelTrip(@RequestParam("tripId") Integer tripId) {
        tripService.cancelTrip(tripId);
        return "redirect:/admin/trip/list";
    }

    /**
     * Returns departure time for specified tripId and station
     *
     * @param tripId tripId
     * @param stationFrom stationFrom
     * @return departure time in JSON
     */
    @GetMapping("/trip/departure-time")
    public @ResponseBody
    Instant getDepartureTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationFrom") String stationFrom) {
        return tripService.getDepartureTime(tripId, stationFrom);
    }

    /**
     * Returns arrival time for specified tripId and station
     *
     * @param tripId tripId
     * @param stationTo stationTo
     * @return arrival time in JSON
     */
    @GetMapping("/trip/arrival-time")
    public @ResponseBody
    Instant getArrivalTime(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("stationTo") String stationTo) {
        return tripService.getArrivalTime(tripId, stationTo);
    }

    /**
     * Returns [departure-time, arrival-time] for specified tripId, stationFrom and stationTo
     *
     * @param tripId tripId
     * @param stationFrom stationFrom
     * @param stationTo stationTo
     * @return [departure-time, arrival-time] in JSON
     */
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

    /**
     * Returns passengers page for specified tripId and carriageNum
     *
     * @param model model
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return admin/passengers_list.jsp
     */
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

    /**
     * Returns list of passengers for specified tripId and carriageNum in JSON
     *
     * @param tripId tripId
     * @param carriageNum carriageNum
     * @return list of passengers for specified tripId and carriageNum in JSON
     */
    @GetMapping("/admin/trip/passengers/get")
    public @ResponseBody
    String getTripPassengersByCarriageNum(
            @RequestParam("tripId") Integer tripId,
            @RequestParam("carriageNum") Integer carriageNum
    ) {
        return ConverterUtil.parseJson(tripService.getTicketsByTripAndCarriageNum(tripId, carriageNum));
    }

}
