package com.InventoryService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mileage {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public long getId() {
		return id;
	}
	 public void setId(long id) {
		 this.id = id;
	 }
	 public String getMileage() {
		 return mileage;
	 }
	 public void setMileage(String mileage) {
		 this.mileage = mileage;
	 }
	private String mileage;
	
	
}
