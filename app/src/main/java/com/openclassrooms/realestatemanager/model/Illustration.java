package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = House.class,
        parentColumns = "id",
        childColumns = "houseId"))

public class Illustration {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private long houseId;
    private String description;
    private String picture;

    public Illustration(long houseId, String description, String picture) {
        this.houseId = houseId;
        this.description = description;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}