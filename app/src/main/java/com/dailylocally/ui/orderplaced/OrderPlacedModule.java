package com.dailylocally.ui.orderplaced;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderPlacedModule {


    @Provides
    OrderPlacedViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new OrderPlacedViewModel(dataManager);
    }

}
