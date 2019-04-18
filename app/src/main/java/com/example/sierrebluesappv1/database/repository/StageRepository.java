package com.example.sierrebluesappv1.database.repository;

import android.arch.lifecycle.LiveData;

import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.database.firebase.StageListLiveData;
import com.example.sierrebluesappv1.database.firebase.StageLiveData;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Stage repository class that retrievs all stages from database
 * and exposes it to ViewModels.
 */
public class StageRepository {

    private static StageRepository instance;

    public StageRepository() {
    }
    //Repository must be unique, singleton pattern
    public static StageRepository getInstance() {
        if (instance == null) {
            synchronized (ActRepository.class) {
                if (instance == null) {
                    instance = new StageRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<StageEntity> getStage(final String idStage){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("stages")
                .child(idStage);
        return new StageLiveData(reference);
    }

    //get all stages
    public LiveData<List<StageEntity>> getAll(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("stages");
        return new StageListLiveData(reference);
    }

    public void insert(final StageEntity stage, OnAsyncEventListener callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("stages");
        String id = stage.getName();
        FirebaseDatabase.getInstance()
                .getReference("stages")
                .child(id)
                .setValue(stage, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final StageEntity stage, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("stages")
                .child(stage.getName())
                .updateChildren(stage.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final StageEntity stage, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("stages")
                .child(stage.getName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
