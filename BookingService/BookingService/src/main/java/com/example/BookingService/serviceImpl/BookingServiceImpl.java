package com.example.BookingService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookingService.Repository.BookingRepository;
import com.example.BookingService.client.InventoryClient;
import com.example.BookingService.client.UserClient;
import com.example.BookingService.dto.EmployeeDto;
import com.example.BookingService.entity.Booking;
import com.example.BookingService.entity.dto.BookingRequestDto;
import com.example.BookingService.entity.dto.BookingResponseDto;
import com.example.BookingService.entity.dto.VehicleDetailsDto;
import com.example.BookingService.enums.BookingStatus;
import com.example.BookingService.exception.TimeSlotAlreadyBookedException;
import com.example.BookingService.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private UserClient userClient;

    // ------------------ Schedule Booking ------------------
    @Override
    public BookingResponseDto scheduleBooking(BookingRequestDto request) {
        VehicleDetailsDto vehicle = inventoryClient.getVehicleById(request.getVehicleId());
        if (vehicle == null) throw new RuntimeException("Vehicle not found!");

        EmployeeDto user = userClient.getUserById(request.getUserId());
        if (user == null) throw new RuntimeException("User not found!");

        boolean exists = bookingRepository.existsOverlappingBooking(
                request.getVehicleId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );
        if (exists) {
            throw new TimeSlotAlreadyBookedException("Time slot already booked!");
        }

        Booking booking = new Booking();
        booking.setVehicleId(request.getVehicleId());
        booking.setUserId(request.getUserId());
        booking.setDate(request.getDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        return new BookingResponseDto(
                "Booking confirmed for " + user.getName() +
                " with " + vehicle.getBrand() + " " + vehicle.getModel(),
                booking.getId(),
                booking.getUserId(),
                booking.getVehicleId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }

    // ------------------ Cancel Booking ------------------
    @Override
    public BookingResponseDto cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return new BookingResponseDto(
                "Booking with ID " + bookingId + " has been cancelled.",
                booking.getId(),
                booking.getUserId(),
                booking.getVehicleId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }

    // ------------------ Get Booking Details ------------------
    @Override
    public BookingResponseDto getBookingDetails(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        return new BookingResponseDto(
                booking.getId(),
                booking.getUserId(),
                booking.getVehicleId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }

    // ------------------ Get All Bookings ------------------
    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> new BookingResponseDto(
                        booking.getId(),
                        booking.getUserId(),
                        booking.getVehicleId(),
                        booking.getDate(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getStatus()
                ))
                .toList();
    }

    // ------------------ Reschedule Booking ------------------
    @Override
    public BookingResponseDto rescheduleBooking(Long bookingId, BookingRequestDto request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        boolean exists = bookingRepository.existsOverlappingBooking(
                booking.getVehicleId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (exists && !(booking.getDate().equals(request.getDate())
                && booking.getStartTime().equals(request.getStartTime())
                && booking.getEndTime().equals(request.getEndTime()))) {
            throw new TimeSlotAlreadyBookedException("Time slot already booked!");
        }

        booking.setDate(request.getDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        bookingRepository.save(booking);

        return new BookingResponseDto(
                "Booking with ID " + bookingId + " has been rescheduled.",
                booking.getId(),
                booking.getUserId(),
                booking.getVehicleId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }
}
