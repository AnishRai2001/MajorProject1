package com.example.BuyerSellerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.BuyerSellerService.client")
public class BuyerSellerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuyerSellerServiceApplication.class, args);
    }
}
