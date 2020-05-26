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

        if (getDataManager().getCurrentLat() == null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            L1CategoryRequest l1CategoryRequest = new L1CategoryRequest();
            /*l1CategoryRequest.setUserid(getDataManager().getCurrentUserId());
            l1CategoryRequest.setLat(getDataManager().getCurrentLat());
            l1CategoryRequest.setLon(getDataManager().getCurrentLng());*/

            l1CategoryRequest.setUserid("1");
            l1CategoryRequest.setLat("12.979937");
            l1CategoryRequest.setLon("80.218418");
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


        /* HomePageRequest homePageRequest = new HomePageRequest();
         *//* homePageRequest.setUserid(getDataManager().getCurrentUserId());
        homePageRequest.setLat(getDataManager().getCurrentLat());
        homePageRequest.setLon(getDataManager().getCurrentLng());*//*

        homePageRequest.setUserid("1");
        homePageRequest.setLat("12.979937");
        homePageRequest.setLon( "80.218418");
        Gson gson = new Gson();
        String  json = gson.toJson(homePageRequest);
        //  getDataManager().setFilterSort(json);


        try {
            setIsLoading(true);
            //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"http://192.168.1.102/tovo/infinity_kitchen.json", new JSONObject(json), new Response.Listener<JSONObject>() {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CATEGORY_LIST, new JSONObject(json), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject homepageResponse) {

                    HomepageResponse response;
                    Gson sGson = new GsonBuilder().create();
                    response = sGson.fromJson(homepageResponse.toString(), HomepageResponse.class);



                    if (response != null) {

                        getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                        serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());
                        emptyImageUrl.set(response.getEmptyUrl());
                        emptyContent.set(response.getEmptyContent());
                        emptySubContent.set(response.getEmptySubconent());
                        headerContent.set(response.getHeaderContent());
                        headerSubContent.set(response.getHeaderSubconent());
                        categoryTitle.set(response.getCategoryTitle());


                        if (getNavigator() != null)
                            getNavigator().changeHeaderText(response.getHeaderContent());

                        if (response.getResult() != null && response.getResult().size() > 0) {
                            fullEmpty.set(false);
                            categoryListLiveData.setValue(response.getResult());
                            if (getNavigator() != null)
                                getNavigator().kitchenLoaded();

                        } else {
                            fullEmpty.set(true);
                            if (getNavigator() != null)
                                getNavigator().kitchenLoaded();
                        }


                    } else {
                        fullEmpty.set(true);
                        if (getNavigator() != null)
                            getNavigator().kitchenLoaded();

                    }

                }





            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("", ""+error.getMessage());
                    if (getNavigator() != null)
                        getNavigator().kitchenLoaded();
                }
            }) {

                *//**
         * Passing some request headers
         *//*
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }*/

    }
}
