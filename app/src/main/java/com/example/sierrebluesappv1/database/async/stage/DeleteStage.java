package com.example.sierrebluesappv1.database.async.stage;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

/**
 * Async task to delete stage in DB
 */
public class DeleteStage extends AsyncTask<StageEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteStage(Application application, OnAsyncEventListener callback) {
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
    protected Void doInBackground(StageEntity... stageEntities) {
        try {
            for (StageEntity stage : stageEntities)
                ((BaseApp) application).getDatabase().stageDao()
                        .delete(stage);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
