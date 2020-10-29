package com.dailylocally.ui.aboutus;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.util.List;

public class AboutUsViewModel extends BaseViewModel<AboutUsNavigator> {

    public ObservableList<AboutUsResponse.Result> faqsItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<AboutUsResponse.Result>> faqsItemsLiveData;

    public AboutUsViewModel(DataManager dataManager) {
        super(dataManager);
        faqsItemsLiveData = new MutableLiveData<>();
    }

    public void addFaqsItemsToList(List<AboutUsResponse.Result> menuProductsItems) {
        faqsItemViewModels.clear();
        faqsItemViewModels.addAll(menuProductsItems);
    }

    public MutableLiveData<List<AboutUsResponse.Result>> getFaqs() {
        return faqsItemsLiveData;
    }

    public void imgBackClick() {
        getNavigator().backClick();
    }

    public void fetchRepos() {
        if(!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_ABOUTUSS, AboutUsResponse.class,null, new Response.Listener<AboutUsResponse>() {
            @Override
            public void onResponse(AboutUsResponse response) {
                if (response != null && response.getResult() != null) {
                    faqsItemsLiveData.setValue(response.getResult());
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
        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
