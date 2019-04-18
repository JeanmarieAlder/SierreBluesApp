package com.example.sierrebluesappv1.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ActLiveData extends LiveData<ActEntity> {

    private final DatabaseReference reference;
    private final ActLiveData.MyValueEventListener listener = new ActLiveData.MyValueEventListener();

    public ActLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ActEntity entity = dataSnapshot.getValue(ActEntity.class);
            if(entity == null){
                return;
            }else{
                entity.setIdAct(dataSnapshot.getKey());
                setValue(entity);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }
}
