package com.dailylocally.ui.splash;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {

    @Provides
    SplashActivityViewModel provideSplashViewModel(DataManager dataManager) {
        return new SplashActivityViewModel(dataManager);
    }

}
