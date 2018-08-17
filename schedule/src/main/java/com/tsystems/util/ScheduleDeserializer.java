package com.tsystems.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.StationDTO;
import com.tsystems.dto.TrainDTO;
import com.tsystems.dto.TripDTO;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScheduleDeserializer extends StdDeserializer<ScheduleDTO> {

    public ScheduleDeserializer() {
        super(ScheduleDTO.class);
    }

    public ScheduleDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Schedule
        int scheduleId = (Integer) (node.get("id")).numberValue();
        long timeArrivalSeconds;
        try {
            timeArrivalSeconds = (node.get("time_arrival").get("epochSecond")).asLong();
        } catch (NullPointerException e) {
            timeArrivalSeconds = 0;
        }
        long timeDepartureSeconds;
        try {
            timeDepartureSeconds = (node.get("time_departure").get("epochSecond")).asLong();
        } catch (NullPointerException e) {
            timeDepartureSeconds = 0;
        }

        // Time stop
        LocalTime time_stop;;
        try {
            Integer timeStopHours = (Integer) node.get("time_stop").get("hour").numberValue();
            Integer timeStopMinutes = (Integer) node.get("time_stop").get("minute").numberValue();
            time_stop = LocalTime.of(timeStopHours, timeStopMinutes);

        } catch (NullPointerException e) {
            time_stop = null;
        }

        // Time late
        LocalTime time_late;
        try {
            Integer timeLateHours = (Integer) node.get("time_late").get("hour").numberValue();
            Integer timeLateMinutes = (Integer) node.get("time_late").get("minute").numberValue();
            time_late = LocalTime.of(timeLateHours, timeLateMinutes);
        } catch (NullPointerException e) {
            time_late = null;
        }

        // Trip
        int tripId = (Integer) (node.get("tripDto").get("id").numberValue());
        int routeId = (Integer) (node.get("tripDto").get("route_id").numberValue());
        long tripStartTimeSeconds = (node.get("tripDto").get("start_time").get("epochSecond").asLong());
        boolean active = node.get("tripDto").get("active").asBoolean();

        // Station
        int stationId = (Integer) (node.get("stationDto").get("id")).numberValue();
        String stationName = node.get("stationDto").get("name").asText();
        double latitude = node.get("stationDto").get("latitude").asDouble();
        double longitude = node.get("stationDto").get("longitude").asDouble();
        StationDTO scheduleStation = new StationDTO(stationId, stationName, latitude, longitude);

        // Train
        int trainId = (Integer) (node.get("tripDto").get("trainDto").get("id").numberValue());
        String trainName = node.get("tripDto").get("trainDto").get("name").asText();
        double trainSpeed = (Double) (node.get("tripDto").get("trainDto").get("speed").numberValue());
        int trainSeatsAmount = (Integer) (node.get("tripDto").get("trainDto").get("seats_amount").numberValue());
        int trainCarriageAmount = (Integer) (node.get("tripDto").get("trainDto").get("carriage_amount").numberValue());

        TrainDTO scheduleTrain = new TrainDTO(trainId, trainName, trainSpeed, trainSeatsAmount, trainCarriageAmount);

        TripDTO scheduleTrip = new TripDTO(tripId,
                scheduleTrain,
                routeId,
                Instant.ofEpochMilli(tripStartTimeSeconds * 1000),
                active);

        Instant arrivalInstant;
        if (timeArrivalSeconds == 0) {
            arrivalInstant = null;
        } else {
            arrivalInstant = Instant.ofEpochMilli(timeArrivalSeconds * 1000);
        }

        Instant departureInstant;
        if (timeDepartureSeconds == 0) {
            departureInstant = null;
        } else {
            departureInstant = Instant.ofEpochMilli(timeDepartureSeconds * 1000);
        }

        return new ScheduleDTO(
                scheduleId,
                scheduleTrip,
                scheduleStation,
                arrivalInstant,
                time_stop,
                departureInstant,
                time_late);
    }
}
