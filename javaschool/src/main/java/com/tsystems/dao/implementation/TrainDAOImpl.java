package com.tsystems.dao.implementation;

import com.tsystems.dao.api.TrainDAO;
import com.tsystems.entity.Train;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Repository
public class TrainDAOImpl extends GenericDAOImpl<Train, Integer> implements TrainDAO {

    @Transactional
    public double getTrainSpeedById(Integer id) {
        Query getSpeed = entityManager.createQuery("select train.speed from Train train where train.id=:id");
        getSpeed.setParameter("id", id);
        double speed = (double) getSpeed.getResultList().get(0);
        return speed;
    }

    @Transactional
    public Integer getTrainSeatsAmountById(Integer id) {
        Query getSeatsAmount = entityManager.createQuery("select train.seats_amount from Train train where train.id=:id");
        getSeatsAmount.setParameter("id", id);
        getSeatsAmount.setMaxResults(1);
        return (Integer) getSeatsAmount.getResultList().get(0);
    }

    @Transactional
    public Integer getTrainCarriageAmountById(Integer id) {
        Query getCarriageAmount = entityManager.createQuery("select train.carriage_amount from Train train where train.id=:id");
        getCarriageAmount.setParameter("id", id);
        getCarriageAmount.setMaxResults(1);
        return (Integer) getCarriageAmount.getResultList().get(0);
    }


}
