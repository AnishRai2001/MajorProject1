package com.example.BookingService.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	  @ExceptionHandler(TimeSlotAlreadyBookedException.class)
	    public ResponseEntity<Map<String, String>> handleTimeSlotAlreadyBooked(TimeSlotAlreadyBookedException ex) {
	        Map<String, String> error = new HashMap();
	        error.put("status", "400");
	        error.put("error", ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }
	
	
}
