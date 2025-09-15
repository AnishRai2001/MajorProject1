package com.example.NotificationService.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.NotificationService.Dto.NotificationEvent;
import com.example.NotificationService.service.NotificationService;

@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consume(NotificationEvent event) {
        System.out.println("📩 Received Event: " + event);
        notificationService.send(event);
    }
}
