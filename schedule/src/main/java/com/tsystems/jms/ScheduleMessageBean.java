package com.tsystems.jms;

import com.tsystems.controller.UpdateControllerBean;
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

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "testQueue") })
@ResourceAdapter("activemq.rar")
// TODO: Change the title of QUEUE. Make it adequate.
public class ScheduleMessageBean implements MessageListener {

    @Inject
    UpdateControllerBean updateControllerBean;

    private static ClientEndpoint clientEndpoint = new ClientEndpoint();

    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
            try {
                clientEndpoint.sendAll(msg.getText());
            } catch (IOException e) {
                // TODO: Log
                e.printStackTrace();
            }
            // TODO: Log
            System.out.println("Consumed message from ActiveMQ: " + msg.getText());
            updateControllerBean.onNewSchedule();
        } catch (JMSException e) {
            // TODO: Log
            e.printStackTrace();
        }
    }
}
