package com.example.sierrebluesappv1.viewmodel.stage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.database.repository.StageRepository;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

import java.util.List;

public class StagesListViewModel extends AndroidViewModel {

    private StageRepository repository;

    private final MediatorLiveData<List<StageEntity>> observableStage;

    public StagesListViewModel(@NonNull Application application, StageRepository stageRepository) {
        super(application);
        this.repository = stageRepository;

        observableStage = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableStage.setValue(null);

        LiveData<List<StageEntity>> stages = stageRepository.getAll();

        // observe the changes of the entities from the database and forward them
        observableStage.addSource(stages, observableStage::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final StageRepository stageRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            stageRepository = ((BaseApp) application).getStageRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StagesListViewModel(application, stageRepository);
        }
    }

    public void deleteStage(StageEntity stage, OnAsyncEventListener callback){
        repository.delete(stage, callback);
    }

    public LiveData<List<StageEntity>> getAllStages(){
            return observableStage;
    }
}
