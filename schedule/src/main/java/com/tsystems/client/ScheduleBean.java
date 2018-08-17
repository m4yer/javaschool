package com.tsystems.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named("scheduleBean")
@ApplicationScoped
public class ScheduleBean {

    @Inject
    private ScheduleService scheduleService;

    private List<String> stations = new ArrayList<>();
    private static ConcurrentMap<String, List<ScheduleDTO>> schedules = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @PostConstruct
    public void init() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        System.out.println("init() ScheduleBean was invoked!");
        try {
            stations = scheduleService.getAllStations();
        } catch (IOException e) {
            System.out.println("FAIL WHILE TRYING TO FETCH STATIONS");
            e.printStackTrace();
        }
        stations.forEach(station -> {
            try {
                List<ScheduleDTO> stationSchedules = scheduleService.getScheduleForToday(station);
                if (stationSchedules.size() > 0) schedules.put(station, stationSchedules);
            } catch (IOException e) {
                System.out.println("SOME TROUBLES WHILE TRYING TO GET SCHEDULE FOR: " + station);
            }
        });
        System.out.println("After fetсhing data list<ScheduleDto>: " + schedules);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
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
                            System.out.println("Got message from topic on CENTRAL SCHEDULE BEAN.");
                            onNewSchedule(newScheduleDto);
                        } catch (JMSException | IOException | NullPointerException e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            };

            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void onNewSchedule(ScheduleDTO newScheduleDto) {
            synchronized (schedules) {
                System.out.println("CENTRAL BEAN: invoked onNewSchedule()");
                String stationName = newScheduleDto.getStationDto().getName();
                List<ScheduleDTO> stationSchedule = schedules.get(stationName);
                System.out.println("CENTRAL BEAN: Go into FOR LOOP");
                for (ScheduleDTO schedule : stationSchedule) {
                    if (newScheduleDto.getId().equals(schedule.getId())) {
                        stationSchedule.set(stationSchedule.indexOf(schedule), newScheduleDto);
                        setScheduleListForMap(stationName, stationSchedule);
                        System.out.println("CENTRAL BEAN: NewSchedule is presented in list. We just change old object to new.");
                        return;
                    }
                    if (newScheduleDto.getId() < schedule.getId()) {
                        stationSchedule.add(stationSchedule.indexOf(schedule) - 1, newScheduleDto);
                        setScheduleListForMap(stationName, stationSchedule);
                        System.out.println("CENTRAL BEAN: Next schedule ID is larger. We just add after previous.");
                        return;
                    }
                }
                stationSchedule.add(newScheduleDto);
                setScheduleListForMap(stationName, stationSchedule);
                System.out.println("CENTRAL BEAN: NewSchedule is not presented, we just add it to the end of list!");
            }
    }

    private void setScheduleListForMap(String key, List<ScheduleDTO> scheduleList) {
        System.out.println("setScheduleListForMap() invoked. Schedule was updated.");
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
}
