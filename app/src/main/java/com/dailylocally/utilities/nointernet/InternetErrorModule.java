package com.dailylocally.utilities.nointernet;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class InternetErrorModule {

    @Provides
    InternetErrorViewModel provideInternetErrorViewModel(DataManager dataManager) {
        return new InternetErrorViewModel(dataManager);
    }


}
