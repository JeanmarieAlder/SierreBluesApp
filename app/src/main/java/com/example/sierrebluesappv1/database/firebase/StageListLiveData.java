package com.example.sierrebluesappv1.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StageListLiveData extends LiveData<List<StageEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public StageListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }
    @Override
    protected void onInactive() { }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toStages(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    }

    private List<StageEntity> toStages(DataSnapshot snapshot) {
        List<StageEntity> stages = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            StageEntity entity = childSnapshot.getValue(StageEntity.class);
            entity.setName(childSnapshot.getKey());
            stages.add(entity);
        }
        return stages;
    }
}
