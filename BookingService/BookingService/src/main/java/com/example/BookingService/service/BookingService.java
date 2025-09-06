
package com.example.BookingService.service;


import java.util.List;

import com.example.BookingService.entity.dto.BookingRequestDto;
import com.example.BookingService.entity.dto.BookingResponseDto;

public interface BookingService {
    
    BookingResponseDto scheduleBooking(BookingRequestDto request);

    // Future methods you may need:
    BookingResponseDto cancelBooking(Long bookingId);

    BookingResponseDto rescheduleBooking(Long bookingId, BookingRequestDto request);

    BookingResponseDto getBookingDetails(Long bookingId);

	List<BookingResponseDto> getAllBookings();
}
