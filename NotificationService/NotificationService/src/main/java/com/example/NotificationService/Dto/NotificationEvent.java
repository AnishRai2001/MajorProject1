package com.example.NotificationService.Dto;

import org.springframework.stereotype.Service;

public class NotificationEvent {
    private String to;       // Recipient phone (e.g. +919876543210)
    private String message;  // Message body
    private String channel;  // "SMS" | "WHATSAPP" | "EMAIL"

    public NotificationEvent() {}

    public NotificationEvent(String to, String message, String channel) {
        this.to = to;
        this.message = message;
        this.channel = channel;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
