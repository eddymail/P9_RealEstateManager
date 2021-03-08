package com.openclassrooms.realestatemanager.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = House.class,
parentColumns = "id",
childColumns = "houseId"))

public class Illustration {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long houseId;
    private String description;
    private String url;

    public Illustration(long id, long houseId, String description, String url) {
        this.id = id;
        this.houseId = houseId;
        this.description = description;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public long getHouseId() {
        return houseId;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
