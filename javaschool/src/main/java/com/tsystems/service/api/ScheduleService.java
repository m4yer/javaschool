package com.tsystems.service.api;

import com.tsystems.dto.ScheduleDTO;
import com.tsystems.entity.Schedule;

import java.util.List;

public interface ScheduleService {


    Schedule findById(Integer id);

    List<Schedule> getAll();

    List<ScheduleDTO> getScheduleByStationNameForToday(String stationName);

    void addScheduleForTrip(Integer tripId, Integer routeId, String stationStopTimes, String tripStartTime, Integer trainId);

    List<ScheduleDTO> getSchedulesByTripId(Integer tripId);

    void editLateStationSchedule(Integer scheduleId, String time_late);
}
