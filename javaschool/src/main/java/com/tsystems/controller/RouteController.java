package com.tsystems.controller;

import com.tsystems.dto.RouteDTO;
import com.tsystems.service.api.RouteService;
import com.tsystems.service.api.StationService;
import com.tsystems.utils.ConverterUtil;
import com.tsystems.utils.RoundUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class RouteController {
    private RouteService routeService;
    private StationService stationService;

    @Autowired
    public RouteController(RouteService routeService, StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
    }

    private static final Logger log = Logger.getLogger(RouteController.class);

    @GetMapping("/route/list")
    public String allRoutesPage() {
        return "admin/route_list";
    }


    @GetMapping("/route/add")
    public ModelAndView addRoutePage(ModelAndView model) {
        model.addObject("stations", stationService.getAll());
        model.addObject("id", routeService.getLastRouteId());
        model.setViewName("admin/route_add");
        return model;
    }

    @PostMapping("/route/create")
    public String createRoute(@RequestParam("stationSequence") String stationSequence) {
        log.info("Creating new route. stationSequence: " + stationSequence);
        routeService.createRoute(stationSequence);
        return "redirect:/admin/route/list";
    }

    @GetMapping("/route/edit/{id}")
    public ModelAndView editRoutePage(@PathVariable("id") Integer routeId) {
        if (routeService.findRouteByRouteId(routeId) != null) {
            return new ModelAndView("admin/route_edit", "routeId", routeId); }
        else {
            throw new EntityNotFoundException();}
    }

    @PostMapping("/route/edit")
    public String editRoutePost(
            @RequestParam("routeId") Integer routeId,
            @RequestParam("stationSequence") String stationSequence) {
        routeService.editRoute(routeId, stationSequence);
        return "redirect:/admin/route/list";
    }


    @GetMapping("/route/delete/{id}")
    public String deleteRouteAndReloadPage(@PathVariable("id") Integer id) {
        routeService.deleteRoute(id);
        return "redirect:/admin/route/list";
    }

}
