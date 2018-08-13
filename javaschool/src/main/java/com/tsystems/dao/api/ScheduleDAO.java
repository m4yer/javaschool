package com.tsystems.dao.api;

import com.tsystems.entity.Schedule;

import java.util.List;

public interface ScheduleDAO extends GenericDAO<Schedule, Integer> {

    List<Schedule> getScheduleByStationIdForToday(Integer stationId);

    List<Schedule> getSchedulesByTripId(Integer tripId);

    void editLateStationSchedule(Integer scheduleId, String time_late);
}
