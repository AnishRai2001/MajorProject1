
package com.InventoryService.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class VehicleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand_id", referencedColumnName = "id")
	private Brand brand;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "model_id", referencedColumnName = "id")
	private Moddel model;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id", referencedColumnName = "id")
	private Price price;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mileage_id", referencedColumnName = "id")
	private Mileage mileage;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "year_id", referencedColumnName = "id")
	private Year year;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transmission_id", referencedColumnName = "id")
	private Transmission_Type transmission_Type;
	
	 @OneToMany(mappedBy = "vehicleDetails", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<VehicleMedia> mediaList = new ArrayList<>();

	 public long getId() {
		 return id;
	 }

	 public void setId(long id) {
		 this.id = id;
	 }

	 public Brand getBrand() {
		 return brand;
	 }

	 public void setBrand(Brand brand) {
		 this.brand = brand;
	 }

	 public Moddel getModel() {
		 return model;
	 }

	 public void setModel(Moddel model) {
		 this.model = model;
	 }

	 public Price getPrice() {
		 return price;
	 }

	 public void setPrice(Price price) {
		 this.price = price;
	 }

	 public Mileage getMileage() {
		 return mileage;
	 }

	 public void setMileage(Mileage mileage) {
		 this.mileage = mileage;
	 }

	 public Year getYear() {
		 return year;
	 }

	 public void setYear(Year year) {
		 this.year = year;
	 }

	 public Transmission_Type getTransmission_Type() {
		 return transmission_Type;
	 }

	 public void setTransmission_Type(Transmission_Type transmission_Type) {
		 this.transmission_Type = transmission_Type;
	 }

	 public List<VehicleMedia> getMediaList() {
		 return mediaList;
	 }

	 public void setMediaList(List<VehicleMedia> mediaList) {
		 this.mediaList = mediaList;
	 }


	
	


}

