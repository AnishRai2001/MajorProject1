package com.example.BuyerSellerService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehicleId; // from InventoryService

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public BuyerProfile getBuyer() {
		return buyer;
	}

	public void setBuyer(BuyerProfile buyer) {
		this.buyer = buyer;
	}
 
    
}
