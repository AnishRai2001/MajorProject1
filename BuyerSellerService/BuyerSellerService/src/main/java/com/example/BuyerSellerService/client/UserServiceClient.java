package com.example.BuyerSellerService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BuyerSellerService.UserResponse;
import com.example.BuyerSellerService.Dto.UserDto;

@FeignClient(name = "UserService", url = "http://localhost:8080")
public interface UserServiceClient {
    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
