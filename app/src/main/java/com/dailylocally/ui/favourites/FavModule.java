package com.dailylocally.ui.favourites;



import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l2.slider.L2SliderAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FavModule {

    DataManager dataManager;

    @Provides
    FavViewModel provideCategoryL1ViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        return new FavViewModel(dataManager);
    }

    @Provides
    L2SliderAdapter provideL2SliderAdapter() {
        return new L2SliderAdapter(new ArrayList<>(),dataManager);
    }
}

