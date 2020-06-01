package com.dailylocally.ui.account;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MyAccountModule {

    @Provides
    MyAccountViewModel provideTrainingViewModel(DataManager dataManager) {
        return new MyAccountViewModel(dataManager);
    }
}
