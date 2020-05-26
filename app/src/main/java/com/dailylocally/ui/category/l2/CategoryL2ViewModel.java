package com.dailylocally.ui.category.l2;


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

public class CategoryL2ViewModel extends BaseViewModel<CategoryL2Navigator> {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public ObservableList<L2CategoryResponse.Result> categoryList = new ObservableArrayList<>();
    private MutableLiveData<List<L2CategoryResponse.Result>> categoryListLiveData;


    public CategoryL2ViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();

    }


    public void goBack() {

        getNavigator().goBack();

    }

    public void addDatatoList(List<L2CategoryResponse.Result> results) {
        categoryList.clear();
        categoryList.addAll(results);
    }


    public MutableLiveData<List<L2CategoryResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public void setCategoryListLiveData(MutableLiveData<List<L2CategoryResponse.Result>> categoryListLiveData) {
        this.categoryListLiveData = categoryListLiveData;
    }

    public void fetchSubCategoryList(String catid,String scl1id) {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            L2CategoryRequest l2CategoryRequest = new L2CategoryRequest();
            /*l1CategoryRequest.setUserid(getDataManager().getCurrentUserId());
            l1CategoryRequest.setLat(getDataManager().getCurrentLat());
            l1CategoryRequest.setLon(getDataManager().getCurrentLng());*/

            l2CategoryRequest.setUserid("1");
            l2CategoryRequest.setLat("12.979937");
            l2CategoryRequest.setLon("80.218418");
            l2CategoryRequest.setCatid(catid);
            l2CategoryRequest.setScl1Id(scl1id);


            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CATEGORYL2_LIST, L2CategoryResponse.class, l2CategoryRequest, new Response.Listener<L2CategoryResponse>() {

                @Override
                public void onResponse(L2CategoryResponse response) {

                    if (response != null) {
                        // getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                       /* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());
                        categoryTitle.set(response.getCategoryTitle());*/
                        if (getNavigator() != null)
                            getNavigator().createtabs(response);
                        title.set(response.getCategoryTitle());
                        //   image.set(response.get());

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
