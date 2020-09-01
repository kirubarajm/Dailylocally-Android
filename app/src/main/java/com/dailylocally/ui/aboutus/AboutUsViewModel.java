package com.dailylocally.ui.signup.faqs;

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

public class FaqFragmentViewModel extends BaseViewModel<FaqFragmentNavigator> {

    public ObservableList<FaqResponse.Result> faqsItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<FaqResponse.Result>> faqsItemsLiveData;

    public FaqFragmentViewModel(DataManager dataManager) {
        super(dataManager);
        faqsItemsLiveData = new MutableLiveData<>();
       // fetchRepos();
    }

    public void addFaqsItemsToList(List<FaqResponse.Result> menuProductsItems) {
        faqsItemViewModels.clear();
        faqsItemViewModels.addAll(menuProductsItems);
    }



    public MutableLiveData<List<FaqResponse.Result>> getFaqs() {
        return faqsItemsLiveData;
    }

    public void imgBackClick() {
        getNavigator().backClick();
    }





    public void fetchRepos() {
        if(!DailylocallyApp.getInstance().onCheckNetWork()) return;

        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_FAQS + 4, FaqResponse.class, new Response.Listener<FaqResponse>() {
            @Override
            public void onResponse(FaqResponse response) {
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
