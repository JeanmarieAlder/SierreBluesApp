package com.example.sierrebluesappv1.database.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;

/**
 * Currently not used, might be avaliable next version in order to add
 * stage data from an act.
 */
public class ActWithScene {

    @Embedded
    public ActEntity act;

    @Relation(parentColumn = "IdStage", entityColumn = "IdStage", entity = StageEntity.class)
    public StageEntity stage;
}
