package com.tsystems.controller;

import com.tsystems.service.api.RouteService;
import com.tsystems.service.api.StationService;
import com.tsystems.service.api.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

/**
 * Dispatches queries related to routes
 */
@Controller
@RequestMapping("/admin")
public class RouteController {
    private RouteService routeService;
    private StationService stationService;
    private TripService tripService;

    @Autowired
    public RouteController(RouteService routeService, StationService stationService, TripService tripService) {
        this.routeService = routeService;
        this.stationService = stationService;
        this.tripService = tripService;
    }

    private static final Logger log = Logger.getLogger(RouteController.class);

    /**
     * Returns routes list page
     *
     * @return admin/route_list.jsp
     */
    @GetMapping("/route/list")
    public String allRoutesPage() {
        return "admin/route_list";
    }

    /**
     * Returns add route page
     *
     * @param model model
     * @return admin/route_add.jsp
     */
    @GetMapping("/route/add")
    public ModelAndView addRoutePage(ModelAndView model) {
        model.addObject("stations", stationService.getAll());
        model.addObject("id", routeService.getLastRouteId());
        model.setViewName("admin/route_add");
        return model;
    }

    /**
     * Processes adding route and returns list of routes page
     *
     * @param stationSequence sequence of stations
     * @return admin/route_list.jsp
     */
    @PostMapping("/route/create")
    public String createRoute(@RequestParam("stationSequence") String stationSequence) {
        log.info("Creating new route. stationSequence: " + stationSequence);
        routeService.createRoute(stationSequence);
        return "redirect:/admin/route/list";
    }

    /**
     * Returns edit route page for specified routeId
     *
     * @param routeId routeId
     * @return admin/route_edit.jsp
     */
    @GetMapping("/route/edit/{id}")
    public ModelAndView editRoutePage(@PathVariable("id") Integer routeId) {
        if (routeService.findRouteByRouteId(routeId) != null) {
            if (tripService.findActiveTripsByRouteId(routeId).size() == 0) {
                return new ModelAndView("admin/route_edit", "routeId", routeId);
            } else {
                ModelAndView model = new ModelAndView("redirect:/admin/route/list", "editAllowed", false);
                model.addObject("routeId", routeId);
                return model;
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Processes editing route and returns route list page
     *
     * @param routeId routeId
     * @param stationSequence new sequence of stations
     * @return admin/route_list.jsp
     */
    @PostMapping("/route/edit")
    public String editRoutePost(
            @RequestParam("routeId") Integer routeId,
            @RequestParam("stationSequence") String stationSequence) {
        routeService.editRoute(routeId, stationSequence);
        return "redirect:/admin/route/list";
    }

    /**
     * Processes deleting of route and returns route list page
     *
     * @param routeId routeId
     * @return admin/route_list.jsp
     */
    @GetMapping("/route/delete/{id}")
    public ModelAndView deleteRouteAndReloadPage(@PathVariable("id") Integer routeId) {
        if (routeService.findRouteByRouteId(routeId) != null) {
            if (tripService.findActiveTripsByRouteId(routeId).size() == 0) {
                routeService.deleteRoute(routeId);
                return new ModelAndView("redirect:/admin/route/list", "routeDeletedResult", true);
            } else {
                ModelAndView model = new ModelAndView("redirect:/admin/route/list", "routeDeletedResult", false);
                model.addObject("routeId", routeId);
                return model;
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

}
