package com.dailylocally.ui.account.referrals;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ReferralsActivityModule {


    @Provides
    ReferralsActivityViewModel provideReferralsViewModel(DataManager dataManager) {
        return new ReferralsActivityViewModel(dataManager);
    }

}
