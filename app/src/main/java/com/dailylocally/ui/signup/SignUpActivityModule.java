package com.dailylocally.ui.signup;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SignUpActivityModule {

    @Provides
    SignUpActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new SignUpActivityViewModel(dataManager);
    }
}
