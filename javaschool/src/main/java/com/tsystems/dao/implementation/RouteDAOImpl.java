package com.tsystems.dao.implementation;

import com.tsystems.dao.api.RouteDAO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.entity.converter.Converter;
import com.tsystems.utils.HaversineUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of RouteDAO api
 */
@Repository
public class RouteDAOImpl extends GenericDAOImpl<Route, Integer> implements RouteDAO {

    private static final Logger log = Logger.getLogger(RouteDAOImpl.class);

    /**
     * Get all route ids
     *
     * @return integer list of all route ids
     */
    public List<Integer> getSingleRoutesId() {
        Query query = entityManager.createQuery("select distinct second.route_id from Route second order by second.route_id asc");
        return query.getResultList();
    }

    /**
     * Gets first and last route rows for specified routeId
     *
     * @param routeId routeId
     * @return list of routes
     */
    @Override
    public List<Route> getFirstAndLastRouteRows(Integer routeId) {
        List<Route> resultList = new ArrayList<>();
        Query first = entityManager.createQuery("select route from Route route where route.route_id=:id order by route.station_order asc");
        first.setMaxResults(1);
        first.setParameter("id", routeId);
        resultList.addAll(first.getResultList());
        // Get the last row by route_id (to get the START station)
        Query last = entityManager.createQuery("select route from Route route where route.route_id=:id order by route.station_order desc");
        last.setMaxResults(1);
        last.setParameter("id", routeId);
        resultList.addAll(last.getResultList());
        return resultList;
    }

    /**
     * Finds and gets route by routeId
     *
     * @param id routeId
     * @return returns list of route rows
     */
    @Override
    public List<Route> findRouteByRouteId(Integer id) {
        Query query = entityManager.createQuery("select route from Route route where route.route_id=:id order by route.station_order asc");
        query.setParameter("id", id);
        return (query.getResultList().size() > 0) ? (List<Route>) query.getResultList() : null;
    }

    /**
     * Gets route distance by it's id
     *
     * @param id routeId
     * @return distance
     */
    public double getRouteDistanceByRouteId(Integer id) {
        double result = 0;
        List<Route> routes = findRouteByRouteId(id);
        for (int i = 0; i < routes.size() - 1; i++) {
            result += HaversineUtil.calculateDistanceInKm(Converter.getStationDto(routes.get(i).getStation()), Converter.getStationDto(routes.get(i + 1).getStation()));
        }
        return result;
    }

    /**
     * Deletes route by id
     *
     * @param id routeId
     */
    public void deleteRoute(Integer id) {
        List<Route> routes = this.findRouteByRouteId(id);
        for (Route routeToDelete : routes) {
            this.delete(routeToDelete);
        }
    }

    /**
     * Updates route's station order by routeId
     *
     * @param routeId routeId
     * @param station station
     * @param stationOrder stationOrder
     */
    public void updateRouteRow(Integer routeId, Station station, Integer stationOrder) {
        log.info("Updating route row. route_id: " + routeId + ", station: " + station.getName() + ", stationOrder: " + stationOrder);
        Query updateRouteRow = entityManager.createQuery("update Route route set route.station_order=:stationOrder where route.route_id=:routeId and route.station.id=:stationId");
        updateRouteRow.setParameter("routeId", routeId);
        updateRouteRow.setParameter("stationId", station.getId());
        updateRouteRow.setParameter("stationOrder", stationOrder);
        updateRouteRow.executeUpdate();
    }

    /**
     * Gets last routeId
     *
     * @return last routeId
     */
    public Integer getLastRouteId() {
        Query findLastId = entityManager.createQuery("select route.route_id from Route route order by route.route_id desc");
        return (Integer) findLastId.getResultList().get(0);
    }

}
