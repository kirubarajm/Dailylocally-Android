package com.dailylocally.ui.pronotion;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AdModule {

    @Provides
    AdViewModel provideTandCViewModel(DataManager dataManager) {
        return new AdViewModel(dataManager);
    }
}
