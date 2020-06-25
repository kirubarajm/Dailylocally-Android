package com.dailylocally.ui.coupons;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l1.L1CategoriesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CouponsModule {

    @Provides
    CouponsViewModel provideFavoritesViewModel(DataManager dataManager) {
        return new CouponsViewModel(dataManager);
    }

    @Provides
    CouponsAdapter provideCouponsAdapter() {
        return new CouponsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CouponsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
