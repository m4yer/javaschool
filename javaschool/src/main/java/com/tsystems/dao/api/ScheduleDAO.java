package com.tsystems.dao.api;

import com.tsystems.entity.Schedule;

import java.time.LocalTime;
import java.util.List;

public interface ScheduleDAO extends GenericDAO<Schedule, Integer> {

    List<Schedule> getScheduleByStationIdForToday(Integer stationId, String date);

    List<Schedule> getSchedulesByTripId(Integer tripId);

    void editLateStationSchedule(Integer scheduleId, LocalTime time_late);
}
