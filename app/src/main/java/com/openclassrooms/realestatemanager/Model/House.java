package com.openclassrooms.realestatemanager.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity
public class House {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String category;
    private String district;
    private boolean isEuro;
    private int price;
    private int area;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private int numberOfBedrooms;
    private String pointOfInterest;
    private String description;
    private String illustration;
    private String address;
    private Boolean available;
    private String dateOfEntry;
    @Nullable
    private String dateOfSale;
    private String realEstateAgent;

    //Constructor
    public House(String category, String district, boolean isEuro, int price, int area, int numberOfRooms, int numberOfBathrooms, int numberOfBedrooms, String pointOfInterest, String description, String illustration, String address, Boolean available, String dateOfEntry, @Nullable String dateOfSale, String realEstateAgent) {
        this.category = category;
        this.district = district;
        this.isEuro = isEuro;
        this.price = price;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.pointOfInterest = pointOfInterest;
        this.description = description;
        this.illustration = illustration;
        this.address = address;
        this.available = available;
        this.dateOfEntry = dateOfEntry;
        this.dateOfSale = dateOfSale;
        this.realEstateAgent = realEstateAgent;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDistrict() {
        return district;
    }

    public int getPrice() {
        return price;
    }

    public boolean isEuro() {
        return isEuro;
    }

    public int getArea() {
        return area;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public String getPointOfInterest() {
        return pointOfInterest;
    }

    public String getDescription() {
        return description;
    }

    public String getIllustration() {
        return illustration;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public String getRealEstateAgent() {
        return realEstateAgent;
    }

    //Setter

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setEuro(boolean euro) {
        isEuro = euro;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setPointOfInterest(String pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public void setDateOfSale(@Nullable String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public void setRealEstateAgent(String realEstateAgent) {
        this.realEstateAgent = realEstateAgent;
    }
}

