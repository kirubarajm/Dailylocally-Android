package com.dailylocally.ui.video;

import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoModule {


    @Provides
    VideoViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new VideoViewModel(dataManager);
    }

}
