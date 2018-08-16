package com.tsystems.controller;

import com.tsystems.client.ScheduleBean;
import com.tsystems.dto.ScheduleDTO;
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
import java.util.List;

@Named("userSchedule")
@SessionScoped
public class UserSchedule implements Serializable {

    @Inject
    private ScheduleBean scheduleBean;
    @Inject
    @Push(channel = "scheduleChannel")
    private PushContext pushContext;
    @EJB
    private UpdateControllerBean updateControllerBean;

    private String stationName;

    @PostConstruct
    public void onInit() {
        System.out.println("Session bean was created!!");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            Connection connection = connectionFactory.createConnection();
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
                            try {
                                updateControllerBean.onNewSchedule(textMessage.getText());
                            } catch (NullPointerException e) {
                                return;
                            }
                            System.out.println("GOT MESSAGE FROM TOPIC:!!!!!!!!!");
                            System.out.println("MESSAGE IS: " + textMessage.getText());
                        } catch (JMSException e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    pushContext.send("updateSchedule");
                }
            };

            consumer.setMessageListener(listener);
        } catch (JMSException e) {
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
        return stationSchedules;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
