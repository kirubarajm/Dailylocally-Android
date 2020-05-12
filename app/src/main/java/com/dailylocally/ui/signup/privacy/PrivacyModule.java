package com.dailylocally.ui.signup.privacy;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PrivacyModule {

    @Provides
    PrivacyViewModel provideTandCViewModel(DataManager dataManager) {
        return new PrivacyViewModel(dataManager);
    }
}
