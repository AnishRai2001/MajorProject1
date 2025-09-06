package com.example.BookingService.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.example.BookingService.enums.BookingStatus;

public class BookingResponseDto {

    private String message;
    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus status;

    // ✅ Constructor for message only (e.g., booking confirmed)
    public BookingResponseDto(String message, Long bookingId) {
        this.message = message;
        this.bookingId = bookingId;
    }

    // ✅ Constructor for full booking details
    public BookingResponseDto(Long bookingId, Long userId, Long vehicleId,
                              LocalDate date, LocalTime startTime, LocalTime endTime,
                              BookingStatus status) {
        this(null, bookingId, userId, vehicleId, date, startTime, endTime, status);
    }

    // ✅ Constructor with message + full details
    public BookingResponseDto(String message, Long bookingId, Long userId, Long vehicleId,
                              LocalDate date, LocalTime startTime, LocalTime endTime,
                              BookingStatus status) {
        this.message = message;
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // ✅ Default constructor
    public BookingResponseDto() {}

    // Getters & Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
