package com.tsystems.controller;

import com.tsystems.entity.Train;
import com.tsystems.service.api.TrainService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class TrainController {
    TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/train/list")
    public String allTrainsPage() {
        return "admin/train_list";
    }

    @GetMapping("/train/list/get")
    public @ResponseBody String getAllTrains() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

    @GetMapping("/train/get/speed/{id}")
    public @ResponseBody double getTrainSpeed(@PathVariable("id") Integer id) {
        return trainService.getTrainSpeedById(id);
    }

    @GetMapping("/train/add")
    public String addTrainPage(@ModelAttribute("train") Train train) {
        return "admin/train_add";
    }

    @PostMapping("/train/add")
    public String addTrainPost(@ModelAttribute("train") Train train, BindingResult result) {
        trainService.addTrain(train);
        return "redirect:/admin/train/list";
    }

    @GetMapping("/train/delete/{id}")
    public String deleteTrain(@PathVariable("id") Integer id) {
        Train train = trainService.findById(id);
        trainService.deleteTrain(train);
        return "redirect:/admin/train/list";
    }

    // TODO: Add @PostMapping delete train and implementation.

    @GetMapping("/train/get/available")
    public @ResponseBody String getAvailableTrainsForTrip() {
        return ConverterUtil.parseJson(trainService.getAll());
    }

}
