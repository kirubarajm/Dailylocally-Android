package com.dailylocally.ui.splash;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager) {
        return new SplashViewModel(dataManager);
    }

}
