package com.dailylocally.ui.category.viewall;



import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CatProductModule {

    DataManager dataManager;

    @Provides
    CatProductViewModel provideCategoryL1ViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        return new CatProductViewModel(dataManager);
    }


}

