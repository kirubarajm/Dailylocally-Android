package com.dailylocally.ui.category.l1;



import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryL1Module {

    DataManager dataManager;

    @Provides
    CategoryL1ViewModel provideCategoryL1ViewModel(DataManager dataManager) {
        this.dataManager = dataManager;

        return new CategoryL1ViewModel(dataManager);
    }
    @Provides
    L1CategoriesAdapter provideL1CategoriesAdapter() {
        return new L1CategoriesAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CategoryL1Activity activity) {
        return new LinearLayoutManager(activity);
    }

}

