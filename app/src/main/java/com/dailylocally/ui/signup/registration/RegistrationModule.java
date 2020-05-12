package com.dailylocally.ui.signup.registration;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationModule {

    @Provides
    RegistrationViewModel provideNameGenderViewModel(DataManager dataManager) {
        return new RegistrationViewModel(dataManager);
    }
}
