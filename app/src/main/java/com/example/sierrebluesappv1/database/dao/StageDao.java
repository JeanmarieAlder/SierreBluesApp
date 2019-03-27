package com.example.sierrebluesappv1.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;

import java.util.List;

@Dao
public interface StageDao {

    @Query("SELECT * FROM Stage WHERE ids = :id")
    LiveData<StageEntity> getById(String id);

    @Query("SELECT * FROM Stage")
    LiveData<List<StageEntity>> getAll();

    @Insert
    void insert(StageEntity stage);

    @Update
    void update(StageEntity stage);

    @Delete
    void delete(StageEntity stage);

    @Query("DELETE FROM Stage")
    void deleteAll();
}
