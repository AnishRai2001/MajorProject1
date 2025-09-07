package com.example.BuyerSellerService.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.BuyerSellerService.entity.BuyerProfile;
import com.example.BuyerSellerService.entity.Wishlist;
import com.example.BuyerSellerService.service.BuyerService;

@RestController
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // Save buyer preferences
    @PostMapping("/{userId}/preferences")
    public BuyerProfile savePreferences(@PathVariable Long userId,
                                        @RequestBody BuyerProfile profile) {
        return buyerService.savePreferences(userId, profile);
    }

    // Add vehicle to wishlist
    @PostMapping("/{userId}/wishlist/{vehicleId}")
    public Wishlist addToWishlist(@PathVariable Long userId,
                                  @PathVariable Long vehicleId) {
        return buyerService.addToWishlist(userId, vehicleId);
    }

    // Get wishlist
    @GetMapping("/{userId}/wishlist")
    public List<Wishlist> getWishlist(@PathVariable Long userId) {
        return buyerService.getWishlist(userId);
    }
}
