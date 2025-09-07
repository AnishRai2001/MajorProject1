package com.example.BuyerSellerService.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BuyerSellerService.entity.CarEvaluationRequest;
import com.example.BuyerSellerService.entity.SellerProfile;
import com.example.BuyerSellerService.service.SellerService;
@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/{userId}/profile")
    public SellerProfile saveSellerProfile(@PathVariable Long userId, @RequestBody SellerProfile profile) {
        profile.setUserId(userId);
        return sellerService.saveSellerProfile(userId, profile);
    }

    @GetMapping("/{userId}/profile")
    public SellerProfile getSellerProfile(@PathVariable Long userId) {
        return sellerService.getSellerProfile(userId);
    }

    @PostMapping("/{userId}/evaluation/{buyerId}/{vehicleId}")
    public CarEvaluationRequest requestEvaluation(
            @PathVariable Long userId,
            @PathVariable Long buyerId,
            @PathVariable Long vehicleId) {
        return sellerService.requestEvaluation(userId, buyerId, vehicleId);
    }

    @GetMapping("/{userId}/evaluations")
    public List<CarEvaluationRequest> getSellerEvaluations(@PathVariable Long userId) {
        return sellerService.getSellerEvaluations(userId);
    }
}
