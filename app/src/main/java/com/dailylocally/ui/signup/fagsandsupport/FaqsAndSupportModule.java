package com.dailylocally.ui.signup.fagsandsupport;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqsAndSupportModule {

    @Provides
    FaqsAndSupportViewModel provideFeedbackAndSupportViewModel(DataManager dataManager) {
        return new FaqsAndSupportViewModel(dataManager);
    }

}
