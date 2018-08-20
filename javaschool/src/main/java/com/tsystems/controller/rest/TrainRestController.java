package com.tsystems.controller.rest;

import com.tsystems.service.api.TrainService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dispatches the train related rest-queries
 */
@RestController
public class TrainRestController {
    private TrainService trainService;

    @Autowired
    public TrainRestController(TrainService trainService) {
        this.trainService = trainService;
    }

    /**
     * Returns list of all trains
     *
     * @return list of all trains in JSON
     */
    @GetMapping("/train/list/get")
    public String getAllTrains() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

    /**
     * Returns speed of specified trainId
     *
     * @param id trainId
     * @return speed of train in JSON
     */
    @GetMapping("/train/get/speed/{id}")
    public double getTrainSpeed(@PathVariable("id") Integer id) {
        return trainService.getTrainSpeedById(id);
    }

    /**
     * Return all available trains
     *
     * @return all available trains for trip in JSON
     */
    @GetMapping("/train/get/available")
    public String getAvailableTrainsForTrip() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

}
