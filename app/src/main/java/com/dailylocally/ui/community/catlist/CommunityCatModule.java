package com.dailylocally.ui.community.catlist;


import android.content.Context;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.home.CategoriesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityCatModule {

    Context context;

    @Provides
    CommunityCatViewModel provideHomeViewModel(DataManager dataManager) {
        return new CommunityCatViewModel(dataManager);
    }
    @Provides
    CategoriesAdapter provideCategoriesAdapter() {
        return new CategoriesAdapter(new ArrayList<>());
    }

}
