package com.InventoryService.dto;

import java.util.List;

public class VehicleDetailsDto {

	 private Long id;
	    private String brand;
	    private String model;
	    private double price;
	    private String transmissionType;
	    private String year;
		public String Mileage;
		private List<String> mediaUrls;

	 
		public List<String> getMediaUrls() {
			return mediaUrls;
		}
		public void setMediaUrls(List<String> mediaUrls) {
			this.mediaUrls = mediaUrls;
		}
		public String getMileage() {
			return Mileage;
		}
		public void setMileage(String mileage) {
			Mileage = mileage;
		}
		// Getters & Setters
	    public Long getId() {
	        return id;
	    }
	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getBrand() {
	        return brand;
	    }
	    public void setBrand(String brand) {
	        this.brand = brand;
	    }

	    public String getModel() {
	        return model;
	    }
	    public void setModel(String model) {
	        this.model = model;
	    }

	    public double getPrice() {
	        return price;
	    }
	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public String getTransmissionType() {
	        return transmissionType;
	    }
	    public void setTransmissionType(String transmissionType) {
	        this.transmissionType = transmissionType;
	    }

	    public String getYear() {
	        return year;
	    }
	    public void setYear(String year) {
	        this.year = year;
	    }
}
