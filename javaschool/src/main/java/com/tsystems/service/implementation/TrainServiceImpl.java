package com.tsystems.service.implementation;

import com.tsystems.dao.api.TrainDAO;
import com.tsystems.dto.TrainDTO;
import com.tsystems.entity.Train;
import com.tsystems.entity.converter.Converter;
import com.tsystems.service.api.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {
    private TrainDAO trainDAO;

    @Autowired
    public TrainServiceImpl(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    /**
     * Adds new train
     *
     * @param train train
     */
    @Transactional
    public void addTrain(Train train) {
        trainDAO.add(train);
    }

    /**
     * Finds and return train by id
     *
     * @param id trainId
     * @return train found by id
     */
    @Transactional
    public Train findById(Integer id) {
        return trainDAO.findById(id);
    }

    /**
     * Deletes train
     *
     * @param train train
     */
    @Transactional
    public void deleteTrain(Train train) {
        trainDAO.delete(train);
    }

    /**
     * Method for getting all trains
     *
     * @return list of trains
     */
    @Transactional
    public List<TrainDTO> getAll() {
        return Converter.getTrainDtos(trainDAO.getAll());
    }

    /**
     * Method for getting train speed by id
     *
     * @param id trainId
     * @return train speed
     */
    @Transactional
    public double getTrainSpeedById(Integer id) {
        return trainDAO.getTrainSpeedById(id);
    }

}
