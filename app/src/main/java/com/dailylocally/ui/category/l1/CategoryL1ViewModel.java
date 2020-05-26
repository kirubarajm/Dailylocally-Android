package com.dailylocally.ui.category.l1;


import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
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

public class CategoryL1ViewModel extends BaseViewModel<CategoryL1Navigator> {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> categoriesCount = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public ObservableList<L1CategoryResponse.Result> categoryList = new ObservableArrayList<>();
    private MutableLiveData<List<L1CategoryResponse.Result>> categoryListLiveData;


    public CategoryL1ViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData=new MutableLiveData<>();
    }


    public void goBack(){

        getNavigator().goBack();

    }

    public void addDatatoList(List<L1CategoryResponse.Result> results) {
        categoryList.clear();
        categoryList.addAll(results);
    }


    public MutableLiveData<List<L1CategoryResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public void setCategoryListLiveData(MutableLiveData<List<L1CategoryResponse.Result>> categoryListLiveData) {
        this.categoryListLiveData = categoryListLiveData;
    }

    public void fetchSubCategoryList(String catid) {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            L1CategoryRequest l1CategoryRequest = new L1CategoryRequest();
            l1CategoryRequest.setUserid(getDataManager().getCurrentUserId());
            l1CategoryRequest.setLat(getDataManager().getCurrentLat());
            l1CategoryRequest.setLon(getDataManager().getCurrentLng());
            l1CategoryRequest.setCatid(catid);


            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CATEGORYL1_LIST, L1CategoryResponse.class, l1CategoryRequest, new Response.Listener<L1CategoryResponse>() {

                @Override
                public void onResponse(L1CategoryResponse response) {

                    if (response != null) {
                        // getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                       /* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());
                        categoryTitle.set(response.getCategoryTitle());*/


                        title.set(response.getCategoryTitle());
                     //   image.set(response.get());

                        if (response.getResult() != null && response.getResult().size() > 0) {
                            categoryListLiveData.setValue(response.getResult());

                            categoriesCount.set(String.valueOf(response.getResult().size()) + " Categories");


                        } else {


                        }

                    } else {


                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("ERROR","Failed");

                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);

        }



    }
}
