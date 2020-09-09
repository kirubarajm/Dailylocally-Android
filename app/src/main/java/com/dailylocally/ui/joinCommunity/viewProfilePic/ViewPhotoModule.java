package com.dailylocally.ui.joinCommunity.viewProfilePic;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewPhotoModule {

    @Provides
    ViewPhotoViewModel provideViewPhotoViewModel(DataManager dataManager) {
        return new ViewPhotoViewModel(dataManager);
    }

}
