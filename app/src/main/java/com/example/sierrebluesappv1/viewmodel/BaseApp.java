package com.example.sierrebluesappv1.viewmodel;

import android.app.Application;

import com.example.sierrebluesappv1.database.AppDatabase;
import com.example.sierrebluesappv1.database.repository.ActRepository;
import com.example.sierrebluesappv1.database.repository.StageRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() { super.onCreate(); }

    public AppDatabase getDatabase() { return (AppDatabase) AppDatabase.getInstance(this);}

    public ActRepository getActRepository() {
        return ActRepository.getInstance();
    }

    public StageRepository getStageRepository() {
        return StageRepository.getInstance();
    }
}
