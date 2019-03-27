package com.example.sierrebluesappv1.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Stage")
public class StageEntity {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ids")
    private long id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Location")
    private String location;

    @ColumnInfo(name = "LocationWebsite")
    private String locationWebsite;

    @ColumnInfo(name = "MaxCapacity")
    private int maxCapacity;

    @ColumnInfo(name = "SeatingPlaces")
    private boolean seatingPlaces;

    public StageEntity(String name, String location,
                       String locationWebsite, int maxCapacity, boolean seatingPlaces) {
        this.name = name;
        this.location = location;
        this.locationWebsite = locationWebsite;
        this.maxCapacity = maxCapacity;
        this.seatingPlaces = seatingPlaces;
    }

    public long getId() {
        return id;
    }

    public void setId(long idStage) {
        this.id = idStage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationWebsite() {
        return locationWebsite;
    }

    public void setLocationWebsite(String locationWebsite) {
        this.locationWebsite = locationWebsite;
    }

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
}
