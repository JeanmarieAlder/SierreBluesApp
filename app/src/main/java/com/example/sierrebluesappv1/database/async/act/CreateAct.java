package com.example.sierrebluesappv1.database.async.act;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

public class CreateAct extends AsyncTask<ActEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateAct(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ActEntity... actEntities) {
        try {
            for (ActEntity acts : actEntities)
                ((BaseApp) application).getDatabase().actDao()
                        .insert(acts);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
