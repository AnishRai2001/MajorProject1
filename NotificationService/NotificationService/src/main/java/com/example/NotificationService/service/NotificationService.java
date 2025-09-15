package com.example.NotificationService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.NotificationService.Dto.NotificationEvent;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class NotificationService {
	
	@Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.sms-from}")
    private String smsFrom;
    
    public void send(NotificationEvent event) {
        if ("SMS".equalsIgnoreCase(event.getChannel())) {
            sendSms(event.getTo(), event.getMessage());
        }
       

}

    private void sendSms(String to, String body) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(smsFrom),
                body
        ).create();

        System.out.println("✅ SMS sent to " + to + " SID: " + message.getSid());
    }
	}
