package com.dailylocally.ui.favourites.products;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FavProductsModule {

    Context context;

    @Provides
    FavProductsViewModel provideHomeViewModel(DataManager dataManager) {
        return new FavProductsViewModel(dataManager);
    }

    @Provides
    FavProductListAdapter provideProductListAdapter() {
        return new FavProductListAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager( FavProductFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
