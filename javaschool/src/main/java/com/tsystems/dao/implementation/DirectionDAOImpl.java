package com.tsystems.dao.implementation;

import com.tsystems.dao.api.DirectionDAO;
import com.tsystems.entity.Direction;
import com.tsystems.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * An implementation of DirectionDAO api
 */
@Repository
public class DirectionDAOImpl extends GenericDAOImpl<Direction, Integer> implements DirectionDAO {

    /**
     * Get all stations which departing from specified Station
     *
     * @param stationName stationName
     * @return list of departing stations
     */
    public List<Station> getDepartures(String stationName) {
        Query getDepartures = entityManager.createQuery("select direction.station_to from Direction direction where direction.station_from.name=:stationName");
        getDepartures.setParameter("stationName", stationName);
        return (List<Station>) getDepartures.getResultList();
    }

    /**
     * Method for removing directions between two stations
     *
     * @param stationFromName stationFromName
     * @param stationToName stationToName
     */
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
