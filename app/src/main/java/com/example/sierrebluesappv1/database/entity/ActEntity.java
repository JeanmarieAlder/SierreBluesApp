package com.example.sierrebluesappv1.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Act",
        foreignKeys =
        @ForeignKey(
                entity = StageEntity.class,
                parentColumns = "StageName",
                childColumns = "IdStage",
                onDelete = ForeignKey.CASCADE
        ), indices = {
        @Index(
                value = {"IdStage"}
        )})
public class ActEntity {

    @ColumnInfo(name = "IdAct")
    @PrimaryKey(autoGenerate = true)
    private Long idAct;

    @ColumnInfo(name = "ArtistName")
    private String artistName;

    @ColumnInfo(name = "ArtistCountry")
    private String artistCountry;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "Genre")
    private String genre;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "StartTime")
    private String startTime;

    @ColumnInfo(name = "Price")
    private float price;

    @ColumnInfo(name = "ArtistWebsite")
    private String artistWebsite;

    @ColumnInfo(name = "IdStage")
    private String idStage;

    public ActEntity(String artistName, String artistCountry,
                     String description, String genre, String startTime,
                     String date, float price, String artistWebsite, String idStage) {
        this.artistName = artistName;
        this.artistCountry = artistCountry;
        this.description = description;
        this.genre = genre;
        this.startTime = startTime;
        this.date = date;
        this.price = price;
        this.artistWebsite = artistWebsite;
        this.idStage = idStage;
    }

    public Long getIdAct() {
        return idAct;
    }

    public void setIdAct(Long idAct) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getArtistWebsite() {
        return artistWebsite;
    }

    public void setArtistWebsite(String artistWebsite) {
        this.artistWebsite = artistWebsite;
    }

    public String getIdStage() {
        return idStage;
    }

    public void setIdStage(String idStage) {
        this.idStage = idStage;
    }
}
