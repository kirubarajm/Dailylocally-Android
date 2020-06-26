package com.dailylocally.ui.collection.l2.products;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CollectionProductsModule {

    Context context;

    @Provides
    CollectionProductsViewModel provideHomeViewModel(DataManager dataManager) {
        return new CollectionProductsViewModel(dataManager);
    }

    @Provides
    CollectionProductListAdapter provideProductListAdapter() {
        return new CollectionProductListAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager( CollectionProductFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
