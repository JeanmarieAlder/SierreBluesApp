package com.example.sierrebluesappv1.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sierrebluesappv1.database.entity.ActEntity;

import java.util.List;

@Dao
public interface ActDao {

    @Query("SELECT * FROM Act WHERE IdAct = :id")
    LiveData<ActEntity> getById(String id);

    @Query("SELECT * FROM Act")
    LiveData<List<ActEntity>> getAll();

    @Insert
    void insert(ActEntity act);

    @Update
    void update(ActEntity act);

    @Delete
    void delete(ActEntity act);

    @Query("DELETE FROM Act")
    void deleteAll();

}
