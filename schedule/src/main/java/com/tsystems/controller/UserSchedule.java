package com.tsystems.controller;

import com.tsystems.client.ScheduleBean;
import com.tsystems.dto.ScheduleDTO;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.annotation.ManagedProperty;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;
import java.util.List;

@Named("userSchedule")
@SessionScoped
public class UserSchedule implements Serializable {

    private String stationName;

    @Inject
    private ScheduleBean scheduleBean;

//    @ManagedProperty(value = "pushContext")
//    @Push(channel = "scheduleChannel")
//    private PushContext pushContext;

    @Inject
    @Push(channel = "scheduleChannel")
    private PushContext pushContext;

    @EJB
    private UpdateControllerBean updateControllerBean;

    @PostConstruct
    public void onInit() {
        System.out.println("Session bean was created!!");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // JMS messages are sent and received using a Session. We will
            // create here a non-transactional session object. If you want
            // to use transactions you should set the first parameter to 'true'
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("ScheduleChannel");
            MessageConsumer consumer = session.createConsumer(topic);

            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            updateControllerBean.onNewSchedule(textMessage.getText());
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

    public void onNewSchedule(@Observes ScheduleDTO newSchedule) {
        System.out.println("onNewSchedule triggers! @Observer works!");
//        pushContext.send("updateSchedule");
    }

    public List<ScheduleDTO> getSchedules() {
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
