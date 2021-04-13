package com.openclassrooms.realestatemanager.Model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.content.ContentValues;

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
    private boolean available;
    private String dateOfEntry;
    @Nullable
    private String dateOfSale;
    private String realEstateAgent;

    //Constructor
    @Ignore
    public House() {};

    public House(String category, String district, boolean isEuro, int price, int area, int numberOfRooms, int numberOfBathrooms, int numberOfBedrooms, String pointOfInterest, String description, String illustration, String address, boolean available, String dateOfEntry, @Nullable String dateOfSale, String realEstateAgent) {
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

    //Constructor for Test
    @Ignore
    public House(long houseId, String maison, boolean b, String hourton, int i, int i1, int i2, int i3, int i4, String s, String s1, String s2, String s3, boolean b1, String s4, Object o, String eddy) {
    }


    //Getter
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

    public boolean isAvailable() {
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

    public void setAvailable(boolean available) {
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

    //Utils
    public static House fromContentValues(ContentValues values) {
        final House house = new House();
        if(values.containsKey("category")) house.setCategory(values.getAsString("category"));
        if(values.containsKey("district")) house.setDistrict(values.getAsString("district"));
        if(values.containsKey("isEuro")) house.setEuro(values.getAsBoolean("isEuro"));
        if(values.containsKey("price")) house.setCategory(values.getAsString("price"));
        if(values.containsKey("area")) house.setArea(values.getAsInteger("area"));
        if(values.containsKey("numberOfRooms")) house.setNumberOfRooms(values.getAsInteger("numberOfRooms"));
        if(values.containsKey("numberOfBathrooms")) house.setNumberOfBathrooms(values.getAsInteger("numberOfBathrooms"));
        if(values.containsKey("numberOfBedrooms")) house.setNumberOfBedrooms(values.getAsInteger("numberOfBedrooms"));
        if(values.containsKey("pointOfInterest")) house.setPointOfInterest(values.getAsString("pointOfInterest"));
        if(values.containsKey("description")) house.setDescription(values.getAsString("description"));
        if(values.containsKey("illustration")) house.setIllustration(values.getAsString("illustration"));
        if(values.containsKey("address")) house.setAddress(values.getAsString("address"));
        if(values.containsKey("dateOfEntry")) house.setDateOfEntry(values.getAsString("dateOfEntry"));
        if(values.containsKey("available")) house.setAvailable(values.getAsBoolean("available"));
        if(values.containsKey("dateOfSale")) house.setDateOfSale(values.getAsString("dateOfSale"));
        if(values.containsKey("realEstateAgent")) house.setRealEstateAgent(values.getAsString("realEstateAgent"));
        return house;
    }
}

