package com.tsystems.service.api;

import com.tsystems.dto.TrainDTO;
import com.tsystems.entity.Train;

import java.util.List;

public interface TrainService {

    void addTrain(Train train);

    Train findById(Integer id);

    void deleteTrain(Train train);

    List<TrainDTO> getAll();

    double getTrainSpeedById(Integer id);

}
