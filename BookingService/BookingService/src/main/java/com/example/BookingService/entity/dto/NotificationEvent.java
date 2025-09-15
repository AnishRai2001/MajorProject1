package com.example.BookingService.entity.dto;


public class NotificationEvent {
    private String to;
    private String message;
    private String channel; // SMS | WHATSAPP

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

