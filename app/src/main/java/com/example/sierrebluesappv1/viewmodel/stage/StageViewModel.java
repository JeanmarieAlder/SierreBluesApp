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
import com.example.sierrebluesappv1.database.repository.ActRepository;
import com.example.sierrebluesappv1.database.repository.StageRepository;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.BaseApp;

public class StageViewModel extends AndroidViewModel {

    private Application application;
    private StageRepository repository;
    private final MediatorLiveData<StageEntity> observableStage;

    public StageViewModel(@NonNull Application application,
                        final Long stageId, StageRepository stageRepository) {
        super(application);

        this.application = application;
        repository = stageRepository;
        observableStage = new MediatorLiveData<>();
        observableStage.setValue(null); //Null by default until we get data from DB
        LiveData<StageEntity> stage = repository.getStage(stageId.toString(), application);

        //observer changes from db and forward them
        observableStage.addSource(stage, observableStage::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long stageId;

        private final StageRepository repository;

        public Factory(@NonNull Application application, Long stageId) {
            this.application = application;
            this.stageId = stageId;
            repository = ((BaseApp) application).getStageRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StageViewModel(application, stageId, repository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<StageEntity> getStage() {
        return observableStage;
    }

    public void createStage(StageEntity stage, OnAsyncEventListener callback) {
        repository.insert(stage, callback, application);
    }

    public void updateAccount(StageEntity stage, OnAsyncEventListener callback) {
        repository.update(stage, callback, application);
    }
}
