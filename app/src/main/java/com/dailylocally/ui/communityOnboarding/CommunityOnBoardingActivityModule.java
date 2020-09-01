package com.dailylocally.ui.communityOnboarding;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityOnBoardingActivityModule {

    @Provides
    CommunityOnBoardingActivityViewModel provideCommunityOnBoardingViewModel(DataManager dataManager) {
        return new CommunityOnBoardingActivityViewModel(dataManager);
    }

}
