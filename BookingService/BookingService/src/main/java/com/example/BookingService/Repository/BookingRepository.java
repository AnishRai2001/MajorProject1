package com.example.BookingService.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.BookingService.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	 @Query("SELECT COUNT(b) > 0 FROM Booking b " +
	           "WHERE b.vehicleId = :vehicleId " +
	           "AND b.date = :date " +
	           "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
	    boolean existsOverlappingBooking(Long vehicleId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
