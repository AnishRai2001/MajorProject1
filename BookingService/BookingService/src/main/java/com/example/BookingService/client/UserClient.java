package com.example.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BookingService.dto.EmployeeDto;
import com.example.BookingService.dto.UserDto;


@FeignClient(name = "USER-SERVICE", url = "http://localhost:8080/api/users")
public interface UserClient {
    @GetMapping("/{id}")
    EmployeeDto getUserById(@PathVariable("id") Long id);
}


