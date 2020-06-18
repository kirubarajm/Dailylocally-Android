package com.dailylocally.ui.category.l2.products.filter;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class FilterViewModel extends BaseViewModel<FilterNavigator> {


    public ObservableList<FilterItems> filterItems = new ObservableArrayList<>();




    public FilterViewModel(DataManager dataManager) {
        super(dataManager);

    }


    public void addToFilter(Integer id) {



    }

    public void removeFromFilter(Integer id) {


    }


    public void apply() {
        getDataManager().saveIsFilterApplied(true);
        getNavigator().applyFilter();

    }




    public void clearAll() {
        getDataManager().saveIsFilterApplied(false);

        getNavigator().clearFilters();


    }



}
