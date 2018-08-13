package com.tsystems.controller.rest;

import com.tsystems.service.api.TrainService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainRestController {
    private TrainService trainService;

    @Autowired
    public TrainRestController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/train/list/get")
    public String getAllTrains() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

    @GetMapping("/train/get/speed/{id}")
    public double getTrainSpeed(@PathVariable("id") Integer id) {
        return trainService.getTrainSpeedById(id);
    }

    @GetMapping("/train/get/available")
    public String getAvailableTrainsForTrip() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

}
