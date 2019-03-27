package com.example.sierrebluesappv1.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.sierrebluesappv1.database.async.stage.CreateStage;
import com.example.sierrebluesappv1.database.async.stage.DeleteStage;
import com.example.sierrebluesappv1.database.async.stage.UpdateStage;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

import java.util.List;

public class StageRepository {

    private static StageRepository instance;

    public StageRepository() {
    }

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

    public LiveData<StageEntity> getStage(final String idStage, Application application){
        return ((BaseApp) application).getDatabase().stageDao().getById(idStage);
    }

    public LiveData<List<StageEntity>> getAll(Application application){
        return ((BaseApp) application).getDatabase().stageDao().getAll();
    }

    public void insert(final StageEntity stage, OnAsyncEventListener callback,
                       Application application) {
        new CreateStage(application, callback).execute(stage);
    }

    public void update(final StageEntity stage, OnAsyncEventListener callback,
                       Application application) {
        new UpdateStage(application, callback).execute(stage);
    }

    public void delete(final StageEntity stage, OnAsyncEventListener callback,
                       Application application) {
        new DeleteStage(application, callback).execute(stage);
    }
}
