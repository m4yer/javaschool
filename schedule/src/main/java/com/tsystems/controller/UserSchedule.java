package com.tsystems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.client.ScheduleBean;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String stationName;

    @PostConstruct
    public void onInit() {
        System.out.println("Session bean was created!!");
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        try {
            System.out.println("Session bean: in TRY{} 140");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            System.out.println("Session bean: after ActiveMQConnectionFactory");
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                System.out.println("Session bean: after createConnection in TRY{}");
            } catch (JMSException e) {
                System.out.println("Session bean: JMSException after createConnection()");
                e.printStackTrace();
            }
            System.out.println("Session bean: after createConnection");
            try {
                connection.start();
                System.out.println("Session bean: after connection.start() in TRY{}");
            } catch (Exception e) {
                System.out.println("Session bean: Exception when connection.start()");
                e.printStackTrace();
            }
            System.out.println("Session bean: after connection.start()");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("Session bean: after createSession()");
            Topic topic = session.createTopic("ScheduleChannel");
            System.out.println("Session bean: after createTopic()");
            MessageConsumer consumer = session.createConsumer(topic);
            System.out.println("Session bean: after createConsumer()");

            System.out.println("Session bean: before new MessageListener()");
            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            ScheduleDTO newScheduleDto = objectMapper.readValue(textMessage.getText(), ScheduleDTO.class);
                            System.out.println("Got message from topic on SESSION USER BEAN.");
                            if (newScheduleDto.getStationDto().getName().equals(getStationName())) {
                                pushContext.send("updateSchedule");
                                System.out.println("pushContext.send(\"updateSchedule\") invoked()");
                            }
                        } catch (JMSException | IOException | NullPointerException e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            };
            System.out.println("Session bean: after new MessageListener()");

            consumer.setMessageListener(listener);
            System.out.println("Session bean: TRY{} finished");
        } catch (JMSException e) {
            System.out.println("Session bean: CATCH{}");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ScheduleDTO> getSchedules() {
        if (stationName == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("/schedule.xhtml");
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Fetching data from Central Schedule Bean");
        System.out.println("For station: " + stationName);
        List<ScheduleDTO> stationSchedules = scheduleBean.getStationSchedule(stationName);
        System.out.println("LIST<SCHEDULE> SIZE FOR " + stationName + ": " + stationSchedules.size());
        return stationSchedules;
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
