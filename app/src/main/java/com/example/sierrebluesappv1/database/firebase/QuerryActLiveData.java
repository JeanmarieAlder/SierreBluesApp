package com.example.sierrebluesappv1.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuerryActLiveData extends LiveData<List<ActEntity>> {
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public QuerryActLiveData(Query ref) {
        query = ref;
    }

    @Override
    protected void onActive() {
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() { }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toActs(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    }

    private List<ActEntity> toActs(DataSnapshot snapshot) {
        List<ActEntity> acts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ActEntity entity = childSnapshot.getValue(ActEntity.class);
            entity.setIdAct(childSnapshot.getKey());
            acts.add(entity);
        }
        return acts;
    }
}
