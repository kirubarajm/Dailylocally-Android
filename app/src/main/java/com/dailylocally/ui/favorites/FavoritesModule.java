package com.dailylocally.ui.favorites;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {

    @Provides
    FavoritesViewModel provideFavoritesViewModel(DataManager dataManager) {
        return new FavoritesViewModel(dataManager);
    }

}
