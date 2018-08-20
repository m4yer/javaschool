package com.tsystems.service.implementation;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Performs Email sending operations
 */
public class MailService {

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * method for sending email
     *
     * @param from from
     * @param to to
     * @param subject subject
     * @param msg msg
     */
    public void sendMail(String from, String to, String subject, String msg) {

        Thread sendMail = new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);

            mailSender.send(message);
        });
        sendMail.start();

    }

}
