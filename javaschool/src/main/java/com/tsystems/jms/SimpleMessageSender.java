package com.tsystems.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class SimpleMessageSender {
    private JmsTemplate jmsTemplate;

    @Autowired
    public SimpleMessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(final String message) {
        jmsTemplate.send(session -> session.createTextMessage(message));
    }
}

