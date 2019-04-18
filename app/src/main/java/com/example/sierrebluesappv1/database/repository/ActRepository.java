package com.example.sierrebluesappv1.database.repository;

import android.arch.lifecycle.LiveData;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.firebase.ActListLiveData;
import com.example.sierrebluesappv1.database.firebase.ActLiveData;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Act repository class, holds all acts information from Database
 * and exposes it to ViewModels.
 */
public class ActRepository {
    private static ActRepository instance;

    public ActRepository() {
    }
    //Repository instance must be unique, singleton pattern
    public static ActRepository getInstance() {
        if (instance == null) {
            synchronized (ActRepository.class) {
                if (instance == null) {
                    instance = new ActRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ActEntity> getAct(final String idAct){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("acts")
                .child(idAct);
        return new ActLiveData(reference);
    }

    //Get all acts
    public LiveData<List<ActEntity>> getAll(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("acts");
        return new ActListLiveData(reference);
    }

    /**
     * Retrieves all acts for a certain day (fri, sat, sun)
     * @param day the day of the show
     * @return a list of all acts fo the specified day
     */
    public LiveData<List<ActEntity>> getActsByDay(final String day){

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("acts");
        return new ActListLiveData(ref);
    }

    public void insert(final ActEntity act, OnAsyncEventListener callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("acts");
        String id = ref.push().getKey();
                FirebaseDatabase.getInstance()
                        .getReference("acts")
                        .child(id)
                        .setValue(act, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ActEntity act, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("acts")
                .child(act.getIdAct())
                .updateChildren(act.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ActEntity act, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("acts")
                .child(act.getIdAct())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
