package com.dailylocally.ui.update;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateModule {

    @Provides
    UpdateViewModel provideSplashViewModel(DataManager dataManager) {
        return new UpdateViewModel(dataManager);
    }

}
