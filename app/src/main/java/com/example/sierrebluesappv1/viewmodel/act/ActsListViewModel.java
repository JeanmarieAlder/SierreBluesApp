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
import com.example.sierrebluesappv1.viewmodel.BaseApp;

import java.util.List;

public class ActsListViewModel extends AndroidViewModel {
    private Application application;
    private ActRepository repository;

    private final MediatorLiveData<List<ActEntity>> observableAct;

    public ActsListViewModel(@NonNull Application application,
                                ActRepository actRepository) {
        super(application);

        this.application = application;

        repository = actRepository;

        observableAct = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableAct.setValue(null);

        LiveData<List<ActEntity>> acts =
                actRepository.getAll(application);

        // observe the changes of the entities from the database and forward them
        observableAct.addSource(acts, observableAct::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ActRepository actRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            actRepository =  BaseApp.getActRepository();
            }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ActsListViewModel(application, actRepository);
        }
    }

    public LiveData<List<ActEntity>> getAllActs(){
        return observableAct;
    }
}
