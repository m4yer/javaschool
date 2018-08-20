package com.tsystems.dao.implementation;

import com.tsystems.dao.api.TrainDAO;
import com.tsystems.entity.Train;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

/**
 * An implementation of TrainDAO api
 */
@Repository
public class TrainDAOImpl extends GenericDAOImpl<Train, Integer> implements TrainDAO {

    /**
     * Get train speed by its id
     *
     * @param id trainId
     * @return train speed
     */
    @Transactional
    public double getTrainSpeedById(Integer id) {
        Query getSpeed = entityManager.createQuery("select train.speed from Train train where train.id=:id");
        getSpeed.setParameter("id", id);
        double speed = (double) getSpeed.getResultList().get(0);
        return speed;
    }


}
