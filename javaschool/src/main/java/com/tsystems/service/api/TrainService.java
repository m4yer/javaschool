package com.tsystems.service.api;

import com.tsystems.dto.TrainDTO;
import com.tsystems.entity.Train;

import java.util.List;

public interface TrainService {

    /**
     * Adds new train
     *
     * @param train train
     */
    void addTrain(Train train);

    /**
     * Finds and return train by id
     *
     * @param id trainId
     * @return train found by id
     */
    Train findById(Integer id);

    /**
     * Deletes train
     *
     * @param train train
     */
    void deleteTrain(Train train);

    /**
     * Method for getting all trains
     *
     * @return list of trains
     */
    List<TrainDTO> getAll();

    /**
     * Method for getting train speed by id
     *
     * @param id trainId
     * @return train speed
     */
    double getTrainSpeedById(Integer id);

}
