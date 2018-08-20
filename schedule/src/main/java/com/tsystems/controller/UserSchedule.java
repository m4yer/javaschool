package com.tsystems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.client.ScheduleBean;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

@Named("userSchedule")
@SessionScoped
public class UserSchedule implements Serializable {
    @Inject
    private ScheduleBean scheduleBean;
    @Inject
    @Push(channel = "scheduleChannel")
    private PushContext pushContext;

    private String stationName;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = Logger.getLogger(UserSchedule.class);

    @PostConstruct
    public void onInit() {
        log.info("Session bean was created");
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)?initialReconnectDelay=2000&maxReconnectAttempts=5");
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
            } catch (JMSException e) {
                log.error(e.getMessage(), e);
            }
            try {
                connection.start();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.info("Connection to activemq was successfully established");

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
                            log.info("Got message from topic on SESSION USER BEAN.");
                            if (newScheduleDto.getStationDto().getName().equals(getStationName())) {
                                pushContext.send("updateSchedule");
                                log.info("pushContext: updateSchedule instruction has sent");
                            }
                        } catch (JMSException | IOException | NullPointerException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                }
            };
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            log.error("JMSException inside onInit() USER SESSION BEAN.");
            log.error(e.getMessage(), e);
        }
    }

    public List<ScheduleDTO> getSchedules() {
        if (stationName == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("Fetching data from Central Schedule Bean for: " + stationName);
        return scheduleBean.getStationSchedule(stationName);
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String instantFormat(Instant input) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        return input != null? formatter.format(input) : "-";
    }
}
