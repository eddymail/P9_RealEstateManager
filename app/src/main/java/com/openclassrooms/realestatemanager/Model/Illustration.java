package com.openclassrooms.realestatemanager.Model;

public class Illustration {
    private int houseId;
    private String description;
    private String url;

    public Illustration(int houseId, String description, String url) {
        this.houseId = houseId;
        this.description = description;
        this.url = url;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
