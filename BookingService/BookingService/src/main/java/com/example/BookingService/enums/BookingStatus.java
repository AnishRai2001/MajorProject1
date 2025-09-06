package com.example.BookingService.enums;

import org.springframework.web.servlet.support.BindStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum BookingStatus {
    CONFIRMED,
    CANCELLED,
    COMPLETED
}

