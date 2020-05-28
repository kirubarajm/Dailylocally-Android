package com.dailylocally.ui.subscription;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SubscriptionModule {

    @Provides
    SubscriptionViewModel provideFeedbackAndSupportViewModel(DataManager dataManager) {
        return new SubscriptionViewModel(dataManager);
    }

}
