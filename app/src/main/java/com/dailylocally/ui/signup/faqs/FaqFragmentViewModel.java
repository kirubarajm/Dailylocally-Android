package com.dailylocally.ui.signup.faqs;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.MvvmApp;

import java.util.List;

public class FaqFragmentViewModel extends BaseViewModel<FaqFragmentNavigator> {

    public ObservableList<FaqResponse.ProductList> faqsItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<FaqResponse.ProductList>> faqsItemsLiveData;

    public FaqFragmentViewModel(DataManager dataManager) {
        super(dataManager);
        faqsItemsLiveData = new MutableLiveData<>();
       // fetchRepos();
    }

    public void addFaqsItemsToList(List<FaqResponse.ProductList> menuProductsItems) {
        faqsItemViewModels.clear();
        faqsItemViewModels.addAll(menuProductsItems);
    }

    public ObservableList<FaqResponse.ProductList> getFaqsItemViewModels() {
        return faqsItemViewModels;
    }

    public MutableLiveData<List<FaqResponse.ProductList>> getFaqs() {
        return faqsItemsLiveData;
    }

    public void imgBackClick() {
        getNavigator().backClick();
    }





    public void fetchRepos() {
        if(!MvvmApp.getInstance().onCheckNetWork()) return;

        //if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_FAQS + AppConstants.EAT, FaqResponse.class, new Response.Listener<FaqResponse>() {
        //GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_FAQ+"/1", FaqResponse.class, new Response.Listener<FaqResponse>() {
            @Override
            public void onResponse(FaqResponse response) {
                if (response != null && response.getData() != null) {
                    faqsItemsLiveData.setValue(response.getData());
                    Log.e("", response.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("", error.getMessage());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, AppConstants.API_VERSION_ONE);
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
