package com.example.sierrebluesappv1.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.sierrebluesappv1.database.async.act.CreateAct;
import com.example.sierrebluesappv1.database.async.act.DeleteAct;
import com.example.sierrebluesappv1.database.async.act.UpdateAct;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

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

    public LiveData<ActEntity> getAct(final String idAct, Application application){
        return ((BaseApp) application).getDatabase().actDao().getById(idAct);
    }

    //Get all acts
    public LiveData<List<ActEntity>> getAll(Application application){
        return ((BaseApp)application).getDatabase().actDao().getAll();
    }

    /**
     * Retrieves all acts for a certain day (fri, sat, sun)
     * @param day the day of the show
     * @param application the current application
     * @return a list of all acts fo the specified day
     */
    public LiveData<List<ActEntity>> getActsByDay(final String day,
                                                  Application application){
        if(day.equals("Friday")){
            return ((BaseApp)application).getDatabase().actDao().getFridayActs();
        }else if(day.equals("Saturday")){
            return ((BaseApp)application).getDatabase().actDao().getSaturdayActs();
        }else if(day.equals("Sunday")){
            return ((BaseApp)application).getDatabase().actDao().getSundayActs();
        }else{
            return ((BaseApp)application).getDatabase().actDao().getAll();
        }
    }

    public void insert(final ActEntity act, OnAsyncEventListener callback,
                       Application application) {
        new CreateAct(application, callback).execute(act);
    }

    public void update(final ActEntity act, OnAsyncEventListener callback,
                       Application application) {
        new UpdateAct(application, callback).execute(act);
    }

    public void delete(final ActEntity act, OnAsyncEventListener callback,
                       Application application) {
        new DeleteAct(application, callback).execute(act);
    }
}
