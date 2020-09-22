package com.dailylocally.utilities.gsnotification;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class GetSocialNotificationModule {


    @Provides
    GetSocialNotificationViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new GetSocialNotificationViewModel(dataManager);
    }

}
