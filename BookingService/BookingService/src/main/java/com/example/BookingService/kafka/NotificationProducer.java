package com.example.BookingService.kafka;




import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.BookingService.entity.dto.NotificationEvent;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationEvent event) {
        kafkaTemplate.send("notification-topic", event);
        System.out.println("📤 Sent notification event: " + event);
    }
}

