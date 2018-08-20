package com.tsystems.dao.api;

import com.tsystems.entity.Train;

/**
 * CRUD and specific operations
 */
public interface TrainDAO extends GenericDAO<Train, Integer> {

    /**
     * Get train speed by its id
     *
     * @param id trainId
     * @return train speed
     */
    double getTrainSpeedById(Integer id);

}
