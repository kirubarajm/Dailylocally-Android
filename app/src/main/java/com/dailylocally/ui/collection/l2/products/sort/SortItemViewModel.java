package com.dailylocally.ui.collection.l2.products.sort;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.collection.l2.products.CollectionProductsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class SortItemViewModel {


    public final ObservableField<String> sortTitle = new ObservableField<>();

    public final ObservableBoolean isClicked = new ObservableBoolean();


    public final FilterItemViewModelListener mListener;
    private final SortItems.Result result;

    int id;

    public SortItemViewModel(FilterItemViewModelListener mListener, SortItems.Result result) {


        this.mListener = mListener;
        this.result = result;

        id = result.getSortid();

        sortTitle.set(result.getSortname());



        /*AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        Gson sGson = new GsonBuilder().create();
        ProductsRequest productsRequest = sGson.fromJson(appPreferencesHelper.getFilterSort(), ProductsRequest.class);
        if (productsRequest != null) {
            if (productsRequest.getSortid()==result.getSortid()){
                mListener.itemClick();
            }

        }*/




    }


    public void onItemClick() {

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);


        List<CollectionProductsRequest.Brandlist> brandlists = new ArrayList<>();
        Gson sGson = new GsonBuilder().create();
        CollectionProductsRequest collectionProductsRequest = sGson.fromJson(appPreferencesHelper.getFilterSort(), CollectionProductsRequest.class);


        if (collectionProductsRequest != null) {
          collectionProductsRequest.setSortid(result.getSortid());

        }else {
            collectionProductsRequest =new CollectionProductsRequest();
            collectionProductsRequest.setSortid(result.getSortid());
        }

        Gson gson = new Gson();
        String request = gson.toJson(collectionProductsRequest);

        appPreferencesHelper.setFilterSort(request);



        mListener.itemClick();
    }


    interface FilterItemViewModelListener {


        void itemClick();



    }


}
