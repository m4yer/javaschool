package com.tsystems.dao.implementation;

import com.tsystems.dao.api.StationDAO;
import com.tsystems.entity.Station;
import org.springframework.stereotype.Repository;

/**
 * An implementation of StationDAO api
 */
@Repository
public class StationDAOImpl extends GenericDAOImpl<Station, Integer> implements StationDAO {

    /**
     * Find and returns station by name
     *
     * @param name name
     * @return station found by name
     */
    public Station findByName(String name) {
        Station station = (Station) entityManager.createNamedQuery("Station.findByName").setParameter("name", name).getResultList().get(0);
        return station;
    }

}
