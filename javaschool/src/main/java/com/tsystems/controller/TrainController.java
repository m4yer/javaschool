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
    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/train/list")
    public String allTrainsPage() {
        return "admin/train_list";
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

}
