package com.example.BuyerSellerService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehicleId; // from InventoryService

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonBackReference
    private BuyerProfile buyer;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public BuyerProfile getBuyer() { return buyer; }
    public void setBuyer(BuyerProfile buyer) { this.buyer = buyer; }
}
