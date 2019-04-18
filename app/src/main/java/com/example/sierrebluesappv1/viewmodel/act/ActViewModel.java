package com.example.sierrebluesappv1.viewmodel.act;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.repository.ActRepository;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

public class ActViewModel extends AndroidViewModel {

    private ActRepository repository;
    private final MediatorLiveData<ActEntity> observableAct;

    public ActViewModel(@NonNull Application application,
                        final String actId, ActRepository actRepository) {
        super(application);

        repository = actRepository;
        observableAct = new MediatorLiveData<>();
        observableAct.setValue(null); //Null by default until we get data from DB
        if(actId != null)
        {
            LiveData<ActEntity> act = repository.getAct(actId);

            //observer changes from db and forward them
            observableAct.addSource(act, observableAct::setValue);
        }

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String actId;

        private final ActRepository repository;

        public Factory(@NonNull Application application, String actId) {
            this.application = application;
            this.actId = actId;
            repository = ((BaseApp) application).getActRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ActViewModel(application, actId, repository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<ActEntity> getAct() {
        return observableAct;
    }

    public void createAct(ActEntity act, OnAsyncEventListener callback) {
        repository.insert(act, callback);
    }

    public void deleteAct(ActEntity act, OnAsyncEventListener callback){
        repository.delete(act, callback);
    }

    public void updateAct(ActEntity act, OnAsyncEventListener callback) {
        repository.update(act, callback);
    }


}
