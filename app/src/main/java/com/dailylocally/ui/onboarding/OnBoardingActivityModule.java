package com.dailylocally.ui.onboarding;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OnBoardingActivityModule {

    @Provides
    OnBoardingActivityViewModel provideSplashViewModel(DataManager dataManager) {
        return new OnBoardingActivityViewModel(dataManager);
    }

}
