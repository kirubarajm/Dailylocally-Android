package com.dailylocally.ui.category.l2.products;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l1.L1CategoriesAdapter;
import com.dailylocally.ui.home.CategoriesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsModule {

    Context context;

    @Provides
    ProductsViewModel provideHomeViewModel(DataManager dataManager) {
        return new ProductsViewModel(dataManager);
    }

    @Provides
    ProductListAdapter provideProductListAdapter() {
        return new ProductListAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager( ProductsFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
