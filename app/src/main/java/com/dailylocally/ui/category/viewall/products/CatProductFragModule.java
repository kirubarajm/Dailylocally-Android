package com.dailylocally.ui.category.viewall.products;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l2.products.ProductListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CatProductFragModule {

    Context context;

    @Provides
    CatProductFragViewModel provideHomeViewModel(DataManager dataManager) {
        return new CatProductFragViewModel(dataManager);
    }

    @Provides
    ProductListAdapter provideProductListAdapter() {
        return new ProductListAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager( CatProductFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
