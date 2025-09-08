package com.example.BuyerSellerService.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class BuyerProfile {

    @Id
    private Long userId; // same as UserService userId

    private String preferredBrand;
    private String preferredFuelType;
    private Double minBudget;
    private Double maxBudget;
    private String location;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Wishlist> wishlist = new ArrayList<>();

    // Getters and Setters

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPreferredBrand() { return preferredBrand; }
    public void setPreferredBrand(String preferredBrand) { this.preferredBrand = preferredBrand; }

    public String getPreferredFuelType() { return preferredFuelType; }
    public void setPreferredFuelType(String preferredFuelType) { this.preferredFuelType = preferredFuelType; }

    public Double getMinBudget() { return minBudget; }
    public void setMinBudget(Double minBudget) { this.minBudget = minBudget; }

    public Double getMaxBudget() { return maxBudget; }
    public void setMaxBudget(Double maxBudget) { this.maxBudget = maxBudget; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Wishlist> getWishlist() { return wishlist; }
    public void setWishlist(List<Wishlist> wishlist) { this.wishlist = wishlist; }
}
