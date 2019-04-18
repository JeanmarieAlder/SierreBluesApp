package com.example.sierrebluesappv1.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class StageEntity {
    private String name;
    private String address;
    private String website;
    private int maxCapacity;
    private boolean seatingPlaces;

    public StageEntity(String name, String address,
                       String website, int maxCapacity, boolean seatingPlaces) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.maxCapacity = maxCapacity;
        this.seatingPlaces = seatingPlaces;
    }

    public StageEntity() { }

    @Exclude
    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {this.website = website;}

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isSeatingPlaces() {
        return seatingPlaces;
    }

    public void setSeatingPlaces(boolean seatingPlaces) {
        this.seatingPlaces = seatingPlaces;
    }

    @Override
    public String toString() {
        return name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("website", website);
        result.put("maxCapacity", maxCapacity);
        result.put("seatingPlaces", seatingPlaces);

        return result;
    }
}
