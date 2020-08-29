package com.dailylocally.ui.community;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l2.products.ProductsFragment;
import com.dailylocally.ui.home.CategoriesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityModule {

    Context context;

    @Provides
    CommunityViewModel provideHomeViewModel(DataManager dataManager) {
        return new CommunityViewModel(dataManager);
    }
    @Provides
    CommunityPostListAdapter provideCommunityPostListAdapter() {
        return new CommunityPostListAdapter(new ArrayList<>());
    }

}
