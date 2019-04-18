package com.example.sierrebluesappv1.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ActEntity {
    private String idAct;
    private String artistName;
    private String artistCountry;
    private String artistImage;
    private String genre;
    private String date;
    private String startTime;
    private float price;
    private String idStage;

    public ActEntity(String artistName, String artistCountry,
                     String artistImage, String genre, String startTime,
                     String date, float price, String idStage) {
        this.artistName = artistName;
        this.artistCountry = artistCountry;
        this.artistImage = artistImage;
        this.genre = genre;
        this.startTime = startTime;
        this.date = date;
        this.price = price;
        this.idStage = idStage;
    }
    public ActEntity() {}

    @Exclude
    public String getIdAct() {
        return idAct;
    }

    public void setIdAct(String idAct) {
        this.idAct = idAct;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public String getArtistImage() {
        return artistImage;
    }

    public void setArtistImage(String artistImage) {
        this.artistImage = artistImage;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIdStage() {
        return idStage;
    }

    public void setIdStage(String idStage) {
        this.idStage = idStage;
    }

    @Override
    public String toString() { return artistName; }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("artistName", artistName);
        result.put("artistCountry", artistCountry);
        result.put("artistImage", artistImage);
        result.put("genre", genre);
        result.put("startTime", startTime);
        result.put("date", date);
        result.put("price", price);
        result.put("idStage", idStage);

        return result;
    }
}
