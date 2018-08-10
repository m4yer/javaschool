package com.tsystems.dao.api;

import com.tsystems.entity.Station;

public interface StationDAO extends GenericDAO<Station, Integer> {

    Station findByName(String name);

}
