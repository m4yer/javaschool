package com.tsystems.service.api;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    /**
     * Returns schedule by id
     *
     * @param id scheduleId
     * @return schedule
     */
    Schedule findById(Integer id);

    /**
     * Returns all schedules
     *
     * @return list of schedules
     */
    List<Schedule> getAll();

    /**
     * Returns schedule of specified station for specified date
     *
     * @param stationName stationName
     * @param date date
     * @return list of schedules
     */
    List<ScheduleDTO> getScheduleByStationNameForDate(String stationName, String date);

    /**
     * Method for adding schedule for just created trip
     *
     * @param tripId tripId
     * @param routeId routeId
     * @param stationStopTimes stationStopTimes
     * @param tripStartTime tripStartTime
     * @param trainId trainId
     */
    void addScheduleForTrip(Integer tripId, Integer routeId, String stationStopTimes, String tripStartTime, Integer trainId);

    /**
     * Method for getting schedule by tripId
     *
     * @param tripId tripId
     * @return schedule
     */
    List<ScheduleDTO> getSchedulesByTripId(Integer tripId);

    /**
     * Method for managing lateness for specified scheduleId
     *
     * @param scheduleId scheduleId
     * @param time_late time_late
     */
    void editLateStationSchedule(Integer scheduleId, String time_late);
}
