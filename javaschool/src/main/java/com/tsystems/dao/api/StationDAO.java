package com.tsystems.dao.api;

import com.tsystems.entity.Station;

/**
 * CRUD and specific operations
 */
public interface StationDAO extends GenericDAO<Station, Integer> {

    /**
     * Find and returns station by name
     *
     * @param name name
     * @return station found by name
     */
    Station findByName(String name);

}
