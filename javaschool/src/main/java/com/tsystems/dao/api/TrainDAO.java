package com.tsystems.dao.api;

import com.tsystems.entity.Train;

public interface TrainDAO extends GenericDAO<Train, Integer> {

    double getTrainSpeedById(Integer id);

}
