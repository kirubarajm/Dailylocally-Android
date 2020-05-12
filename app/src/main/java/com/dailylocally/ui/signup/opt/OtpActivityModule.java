package com.dailylocally.ui.signup.opt;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OtpActivityModule {

    @Provides
    OtpActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new OtpActivityViewModel(dataManager);
    }
}
