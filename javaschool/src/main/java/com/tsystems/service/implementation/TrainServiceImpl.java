package com.tsystems.service.implementation;

import com.tsystems.entity.converter.Converter;
import com.tsystems.dao.api.TrainDAO;
import com.tsystems.dto.TrainDTO;
import com.tsystems.entity.Train;
import com.tsystems.service.api.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TrainServiceImpl implements TrainService {

    @Autowired
    TrainDAO trainDAO;

    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @Transactional
    public void addTrain(Train train) {
        trainDAO.add(train);
    }

    @Transactional
    public Train findById(Integer id) {
        return trainDAO.findById(id);
    }

    @Transactional
    public void deleteTrain(Train train) {
        trainDAO.delete(train);
    }

    @Transactional
    public List<TrainDTO> getAll() {
        return Converter.getTrainDtos(trainDAO.getAll());
    }

    @Transactional
    public double getTrainSpeedById(Integer id) {
        return trainDAO.getTrainSpeedById(id);
    }

}
