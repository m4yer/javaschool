package com.tsystems.dao.api;

import com.tsystems.entity.Schedule;

import java.time.LocalTime;
import java.util.List;

/**
 * CRUD and specific operations
 */
public interface ScheduleDAO extends GenericDAO<Schedule, Integer> {

    /**
     * Gets schedule for specified station and date
     *
     * @param stationId stationId
     * @param date date
     * @return list of schedules
     */
    List<Schedule> getScheduleByStationIdForToday(Integer stationId, String date);

    /**
     * Get schedules for tripId
     *
     * @param tripId tripId
     * @return list of schedules
     */
    List<Schedule> getSchedulesByTripId(Integer tripId);

    /**
     * Method for editing late time for scheduleId
     *
     * @param scheduleId scheduleId
     * @param time_late time_late
     */
    void editLateStationSchedule(Integer scheduleId, LocalTime time_late);
}
