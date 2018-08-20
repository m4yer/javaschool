package com.tsystems.controller;

import com.tsystems.entity.Train;
import com.tsystems.service.api.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Dispatches queries related to trains
 */
@Controller
@RequestMapping("/admin")
public class TrainController {
    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    /**
     * Returns list of trains page
     *
     * @return admin/train_list.jsp
     */
    @GetMapping("/train/list")
    public String allTrainsPage() {
        return "admin/train_list";
    }

    /**
     * Returns add train page
     *
     * @return admin/train_add.jsp
     */
    @GetMapping("/train/add")
    public String addTrainPage(@ModelAttribute("train") Train train) {
        return "admin/train_add";
    }

    /**
     * Processes of adding new train and returns train list page
     *
     * @param train train
     * @param result result
     * @return admin/train_list.jsp
     */
    @PostMapping("/train/add")
    public String addTrainPost(@ModelAttribute("train") Train train, BindingResult result) {
        trainService.addTrain(train);
        return "redirect:/admin/train/list";
    }

}
