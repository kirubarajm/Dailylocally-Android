package com.dailylocally.ui.home;


import android.content.Context;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    Context context;

    @Provides
    HomeViewModel provideHomeViewModel(DataManager dataManager) {
        return new HomeViewModel(dataManager);
    }

}
