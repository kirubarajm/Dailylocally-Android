package com.dailylocally.ui.signup.tandc;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class TermsAndConditionModule {

    @Provides
    TermsAndConditionViewModel provideTandCViewModel(DataManager dataManager) {
        return new TermsAndConditionViewModel(dataManager);
    }
}
