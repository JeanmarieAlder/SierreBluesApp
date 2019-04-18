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
import java.util.Comparator;
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

        //Sort acts by start time (ascending)
        acts.sort(new Comparator<ActEntity>() {
            @Override
            public int compare(ActEntity o1, ActEntity o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        return acts;
    }
}
