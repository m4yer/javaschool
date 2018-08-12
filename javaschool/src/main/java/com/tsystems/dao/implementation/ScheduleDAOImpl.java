package com.tsystems.dao.implementation;

import com.tsystems.dao.api.ScheduleDAO;
import com.tsystems.entity.Schedule;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ScheduleDAOImpl extends GenericDAOImpl<Schedule, Integer> implements ScheduleDAO {

    public List<Schedule> findScheduleByStationId(Integer stationId) {
        Query findSchedule = entityManager.createQuery("select schedule from Schedule schedule where schedule.station.id=:stationId order by schedule.time_arrival asc");
        findSchedule.setParameter("stationId", stationId);
        List<Schedule> schedules = findSchedule.getResultList();
        for (Schedule schedule : schedules) {
            Hibernate.initialize(schedule.getTrip());
            Hibernate.initialize(schedule.getTrip().getTrain());
        }
        return schedules;
    }

    public List<Schedule> getScheduleByStationIdForToday(Integer stationId) {
        Query findSchedules = entityManager.createQuery("select schedule from Schedule schedule where schedule.station.id=:stationId");
        findSchedules.setParameter("stationId", stationId);
        return (List<Schedule>) findSchedules.getResultList();
    }

    public List<Schedule> getSchedulesByTripId(Integer tripId) {
        Query findSchedules = entityManager.createQuery("select schedule from Schedule schedule where schedule.trip.id=:tripId order by schedule.id");
        findSchedules.setParameter("tripId", tripId);
        return (List<Schedule>) findSchedules.getResultList();
    }

    public void editLateStationSchedule(Integer scheduleId, String time_late) {
        Query updateTimeLate = entityManager.createQuery("update Schedule schedule set schedule.time_late=:time_late where schedule.id=:scheduleId");
        updateTimeLate.setParameter("scheduleId", scheduleId);
        updateTimeLate.setParameter("time_late", time_late);
        updateTimeLate.executeUpdate();
    }

}
