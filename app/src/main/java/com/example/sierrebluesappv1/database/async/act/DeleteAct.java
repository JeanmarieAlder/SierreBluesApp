package com.example.sierrebluesappv1.database.async.act;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

/**
 * Async task to delete act in DB
 */
public class DeleteAct extends AsyncTask<ActEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteAct(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
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

    @Override
    protected Void doInBackground(ActEntity... actEntities) {
        try {
            for (ActEntity account : actEntities)
                ((BaseApp) application).getDatabase().actDao()
                        .delete(account);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
