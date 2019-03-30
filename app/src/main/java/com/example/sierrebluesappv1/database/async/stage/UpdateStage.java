package com.example.sierrebluesappv1.database.async.stage;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

/**
 * Async task to update stage in DB
 */
public class UpdateStage extends AsyncTask<StageEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateStage(Application application, OnAsyncEventListener callback) {
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
                        .update(stage);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
