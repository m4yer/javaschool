package com.tsystems.dao.implementation;

import com.tsystems.dao.api.ScheduleDAO;
import com.tsystems.entity.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * An implementation of ScheduleDAO api
 */
@Repository
public class ScheduleDAOImpl extends GenericDAOImpl<Schedule, Integer> implements ScheduleDAO {

    /**
     * Gets schedule for specified station and date
     *
     * @param stationId stationId
     * @param date date
     * @return list of schedules
     */
    public List<Schedule> getScheduleByStationIdForToday(Integer stationId, String date) {
        Query findSchedules = entityManager.createQuery("select schedule from Schedule schedule " +
                "where schedule.station.id=:stationId and ((schedule.time_arrival between :desiredDate and :tomorrow) or (schedule.time_departure between :desiredDate and :tomorrow))");
        Instant desiredDate;
        if (date == null) {
            desiredDate = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        } else {
            desiredDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy")).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        }
        Instant tomorrow;
        if (date == null) {
            tomorrow = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        } else {
            tomorrow = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy")).plusDays(1).atStartOfDay().toInstant(ZoneOffset.ofHours(3));
        }
        findSchedules.setParameter("desiredDate", desiredDate);
        findSchedules.setParameter("tomorrow", tomorrow);
        findSchedules.setParameter("stationId", stationId);
        List<Schedule> resultList = (List<Schedule>) findSchedules.getResultList();
        return resultList;
    }

    /**
     * Get schedules for tripId
     *
     * @param tripId tripId
     * @return list of schedules
     */
    public List<Schedule> getSchedulesByTripId(Integer tripId) {
        Query findSchedules = entityManager.createQuery("select schedule from Schedule schedule where schedule.trip.id=:tripId order by schedule.id");
        findSchedules.setParameter("tripId", tripId);
        return (List<Schedule>) findSchedules.getResultList();
    }

    /**
     * Method for editing late time for scheduleId
     *
     * @param scheduleId scheduleId
     * @param time_late time_late
     */
    public void editLateStationSchedule(Integer scheduleId, LocalTime time_late) {
        Query updateTimeLate = entityManager.createQuery("update Schedule schedule set schedule.time_late=:time_late where schedule.id=:scheduleId");
        updateTimeLate.setParameter("scheduleId", scheduleId);
        updateTimeLate.setParameter("time_late", time_late);
        updateTimeLate.executeUpdate();
    }

}
