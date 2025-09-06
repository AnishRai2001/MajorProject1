package com.example.BookingService.controller;

import com.example.BookingService.entity.dto.BookingRequestDto;
import com.example.BookingService.entity.dto.BookingResponseDto;
import com.example.BookingService.service.BookingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // ✅ Schedule new booking
    @PostMapping
    public ResponseEntity<BookingResponseDto> scheduleBooking(@RequestBody BookingRequestDto request) {
        BookingResponseDto response = bookingService.scheduleBooking(request);
        return ResponseEntity.ok(response);
    }

    // ✅ Get booking details
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Long id) {
        BookingResponseDto response = bookingService.getBookingDetails(id);
        return ResponseEntity.ok(response);
    }

    // ✅ Cancel booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking with ID " + id + " cancelled successfully.");
    }

    // ✅ Reschedule booking
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> rescheduleBooking(
            @PathVariable Long id,
            @RequestBody BookingRequestDto request
    ) {
        BookingResponseDto response = bookingService.rescheduleBooking(id, request);
        return ResponseEntity.ok(response);
    }
 // ✅ List all bookings
 
 // ✅ List all bookings
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }


    
}

