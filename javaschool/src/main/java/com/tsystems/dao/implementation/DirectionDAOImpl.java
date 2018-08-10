package com.tsystems.dao.implementation;

import com.tsystems.dao.api.DirectionDAO;
import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DirectionDAOImpl extends GenericDAOImpl<Direction, Integer> implements DirectionDAO {

    public List<Station> getDepartures(String stationName) {
        Query getDepartures = entityManager.createQuery("select direction.station_to from Direction direction where direction.station_from.name=:stationName");
        getDepartures.setParameter("stationName", stationName);
        return (List<Station>) getDepartures.getResultList();
    }

    public void removeDirectionBetweenStations(String stationFromName, String stationToName) {
        Query deleteDirection = entityManager.createQuery("" +
                "delete from Direction direction " +
                "where direction.station_from in (select station from Station station where station.name=:stationFromName)" +
                " and " +
                "direction.station_to in (select station from Station station where station.name=:stationToName)");
        deleteDirection.setParameter("stationFromName", stationFromName);
        deleteDirection.setParameter("stationToName", stationToName);
        deleteDirection.executeUpdate();
    }

}
