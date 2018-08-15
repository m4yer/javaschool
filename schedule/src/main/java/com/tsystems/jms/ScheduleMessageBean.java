package com.tsystems.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.client.ScheduleBean;
import com.tsystems.controller.UpdateControllerBean;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.util.ScheduleDeserializer;
import com.tsystems.websocket.ClientEndpoint;
import org.jboss.annotation.ejb.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

//@MessageDriven(activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationType",
//                propertyValue = "javax.jms.Topic"),
//        @ActivationConfigProperty(propertyName = "destination",
//                propertyValue = "ScheduleChannel"),
//        @ActivationConfigProperty(propertyName = "subscriptionDurability",
//                propertyValue = "NonDurable")})
//@ResourceAdapter("activemq.rar")
public class ScheduleMessageBean implements MessageListener {

    @Inject
    UpdateControllerBean updateControllerBean;

    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static ClientEndpoint clientEndpoint = new ClientEndpoint();

    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
//            try {
//                clientEndpoint.sendAll(msg.getText());
//            } catch (IOException e) {
//                // TODO: Log
//                e.printStackTrace();
//            }
            System.out.println("Consumed message from ActiveMQ: " + msg.getText());
            SimpleModule module = new SimpleModule();
            module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
            objectMapper.registerModule(module);
            try {
                ScheduleDTO newSchedule = objectMapper.readValue(msg.getText(), ScheduleDTO.class);
                System.out.println("AFTER DESERIALIZING FROM MQ: scheduleDTO: " + newSchedule.toString());
//                updateControllerBean.onNewSchedule(newSchedule);
            } catch (IOException e) {
                System.out.println("SOMETHING WENT WRONG AFTER TRYING TO DESERIALIZE IN onMessage() messageBean");
            }
        } catch (JMSException e) {
            // TODO: Log
            e.printStackTrace();
        }
    }
}
