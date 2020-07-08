package com.dailylocally.ui.promotion.bottom;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PromotionModule {


    DataManager dataManager;
    @Provides
    PromotionViewModel provideFilterViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new PromotionViewModel(dataManager);
    }



}
