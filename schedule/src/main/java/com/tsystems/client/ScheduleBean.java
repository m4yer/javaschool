package com.tsystems.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Central bean that store schedule
 */
@Named("scheduleBean")
@ApplicationScoped
public class ScheduleBean {

    @Inject
    private ScheduleService scheduleService;

    private List<String> stations = new ArrayList<>();
    private static ConcurrentMap<String, List<ScheduleDTO>> schedules = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = Logger.getLogger(ScheduleBean.class);

    /**
     * Invoked immediately after creating bean
     * Creating message topic consumer which send instructions to frontend when schedule was updated on the first application
     *
     */
    @PostConstruct
    public void init() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        log.info("init() ScheduleBean was invoked!");
        try {
            stations = scheduleService.getAllStations();
        } catch (IOException e) {
            log.info("FAIL WHILE TRYING TO FETCH STATIONS");
            e.printStackTrace();
        }
        stations.forEach(station -> {
            try {
                log.info("Get scheduleForToday from 1st app for: " + station);
                List<ScheduleDTO> stationSchedules = scheduleService.getScheduleForToday(station);
                if (stationSchedules != null) schedules.put(station, stationSchedules);
            } catch (IOException e) {
                log.info("SOME TROUBLES WHILE TRYING TO GET SCHEDULE FOR: " + station);
            }
        });
        log.info("After fetсhing data list<ScheduleDto>: " + schedules);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)?initialReconnectDelay=2000&maxReconnectAttempts=5");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("ScheduleChannel");
            MessageConsumer consumer = session.createConsumer(topic);

            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            ScheduleDTO newScheduleDto = objectMapper.readValue(textMessage.getText(), ScheduleDTO.class);
                            log.info("Got message from topic on CENTRAL SCHEDULE BEAN.");
                            onNewSchedule(newScheduleDto);
                        } catch (JMSException | IOException | NullPointerException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                }
            };

            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked every time when there is new message was consumed by bean message consumer
     * Adds new schedule to central map
     *
     * @param newScheduleDto
     */
    private void onNewSchedule(ScheduleDTO newScheduleDto) {
        synchronized (schedules) {
            log.info("CENTRAL BEAN: invoked onNewSchedule()");
            String stationName = newScheduleDto.getStationDto().getName();
            List<ScheduleDTO> stationSchedule = schedules.get(stationName);
            if (stationSchedule != null) {
                for (ScheduleDTO schedule : stationSchedule) {
                    if (newScheduleDto.getId().equals(schedule.getId())) {
                        stationSchedule.set(stationSchedule.indexOf(schedule), newScheduleDto);
                        setScheduleListForMap(stationName, stationSchedule);
                        log.info("CENTRAL BEAN: NewSchedule is presented in list. We just change old object to new.");
                        return;
                    }
                    if (newScheduleDto.getId() < schedule.getId()) {
                        stationSchedule.add(stationSchedule.indexOf(schedule) - 1, newScheduleDto);
                        setScheduleListForMap(stationName, stationSchedule);
                        log.info("CENTRAL BEAN: Next schedule ID is larger. We just add after previous.");
                        return;
                    }
                }
            }
            if (stationSchedule != null) {
                stationSchedule.add(newScheduleDto);
            } else {
                stationSchedule = new ArrayList<>();
                stationSchedule.add(newScheduleDto);
            }
            setScheduleListForMap(stationName, stationSchedule);
            log.info("CENTRAL BEAN: NewSchedule is not presented, we just add it to the end of list!");
        }
    }

    /**
     * Invokes when there is new scheduleDTO came in activeMq
     *
     * @param key
     * @param scheduleList
     */
    private void setScheduleListForMap(String key, List<ScheduleDTO> scheduleList) {
        log.info("setScheduleListForMap() invoked. Schedule was updated.");
        schedules.remove(key);
        schedules.put(key, scheduleList);
    }

    public List<ScheduleDTO> getStationSchedule(String stationName) {
        return schedules.get(stationName);
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public static ConcurrentMap<String, List<ScheduleDTO>> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ConcurrentMap<String, List<ScheduleDTO>> schedules) {
        ScheduleBean.schedules = schedules;
    }
}
