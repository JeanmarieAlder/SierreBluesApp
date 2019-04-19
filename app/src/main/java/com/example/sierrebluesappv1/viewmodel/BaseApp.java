package com.example.sierrebluesappv1.viewmodel;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.sierrebluesappv1.database.repository.ActRepository;
import com.example.sierrebluesappv1.database.repository.StageRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Base application, used to retreive an instance of the database,
 * Act and Stage table.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("Act_Added");

    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    public ActRepository getActRepository() {
        return ActRepository.getInstance();
    }

    public StageRepository getStageRepository() {
        return StageRepository.getInstance();
    }
}
