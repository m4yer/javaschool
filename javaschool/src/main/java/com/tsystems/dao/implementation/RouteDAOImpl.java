package com.tsystems.dao.implementation;

import com.tsystems.controller.converter.Converter;
import com.tsystems.dao.api.RouteDAO;
import com.tsystems.entity.Route;
import com.tsystems.entity.Station;
import com.tsystems.utils.HaversineUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RouteDAOImpl extends GenericDAOImpl<Route, Integer> implements RouteDAO {

    private static final Logger log = Logger.getLogger(RouteDAOImpl.class);

    public List<Integer> getSingleRoutesId() {
        Query query = entityManager.createQuery("select distinct second.route_id from Route second order by second.route_id asc");
        return query.getResultList();
    }

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

    @Override
    public List<Route> findRouteByRouteId(Integer id) {
        Query query = entityManager.createQuery("select route from Route route where route.route_id=:id order by route.station_order asc");
        query.setParameter("id", id);
        return (List<Route>) query.getResultList();
    }

    public double getRouteDistanceByRouteId(Integer id) {
        double result = 0;
        List<Route> routes = findRouteByRouteId(id);
        for (int i = 0; i < routes.size() - 1; i++) {
            result += HaversineUtil.calculateDistanceInKm(Converter.getStationDto(routes.get(i).getStation()), Converter.getStationDto(routes.get(i + 1).getStation()));
        }
        return result;
    }

    public void deleteRoute(Integer id) {
        List<Route> routes = this.findRouteByRouteId(id);
        for (Route routeToDelete : routes) {
            this.delete(routeToDelete);
        }
    }

    public void updateRouteRow(Integer routeId, Station station, Integer stationOrder) {
        log.info("Updating route row. route_id: " + routeId + ", station: " + station.getName() + ", stationOrder: " + stationOrder);
        Query updateRouteRow = entityManager.createQuery("update Route route set route.station_order=:stationOrder where route.route_id=:routeId and route.station.id=:stationId");
        updateRouteRow.setParameter("routeId", routeId);
        updateRouteRow.setParameter("stationId", station.getId());
        updateRouteRow.setParameter("stationOrder", stationOrder);
        updateRouteRow.executeUpdate();
    }

    public Integer getLastRouteId() {
        Query findLastId = entityManager.createQuery("select route.route_id from Route route order by route.route_id desc");
        return (Integer) findLastId.getResultList().get(0);
    }

}
