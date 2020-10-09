package com.dailylocally.ui.address.type;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressTypeModule {

    @Provides
    AddressTypeViewModel provideSplashViewModel(DataManager dataManager) {
        return new AddressTypeViewModel(dataManager);
    }

}
