package com.tsystems.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsAero {

    private RestTemplate restTemplate;

    @Autowired
    public SmsAero() {
        this.restTemplate = new RestTemplate();
    }

    private static final Logger log = Logger.getLogger(SmsAero.class);

    private String apiEmail = "aspid888@gmail.com";
    private String apiKey = "cjdgZOeI2QhzBYtnONthktlrbmrf";

    public void sendSms(String mobileNumber, String messageText) {
        String result = restTemplate.getForObject("https://" + apiEmail + ":" + apiKey + "@gate.smsaero.ru/v2/sms/send?number=" + mobileNumber + "&text=" + messageText + "&sign=SMS Aero&channel=DIRECT", String.class);
        log.info("Response from SMSAero after sending: " + result);
    }

}
