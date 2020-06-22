package com.dailylocally.ui.category.l2.products;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
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

public class ProductsViewModel extends BaseViewModel<ProductsNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean fullEmpty = new ObservableBoolean();
    public final ObservableBoolean kitchenListLoading = new ObservableBoolean();
    public final ObservableField<String> emptyImageUrl = new ObservableField<>();
    public final ObservableField<String> emptyContent = new ObservableField<>();
    public final ObservableField<String> headerContent = new ObservableField<>();
    public final ObservableField<String> emptySubContent = new ObservableField<>();
    public final ObservableField<String> headerSubContent = new ObservableField<>();
    public final ObservableField<String> categoryTitle = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();


    public ObservableList<ProductsResponse.Result> productsList = new ObservableArrayList<>();
    private MutableLiveData<List<ProductsResponse.Result>> productsListLiveData;

    public  ProductsRequest productsRequest = new ProductsRequest();
    public int scl2id;
    public ProductsViewModel(DataManager dataManager) {
        super(dataManager);
        productsListLiveData = new MutableLiveData<>();
//fetchProducts(1);
    }

    public MutableLiveData<List<ProductsResponse.Result>> getProductsListLiveData() {
        return productsListLiveData;
    }

    public void addCategoryToList(List<ProductsResponse.Result> items) {
        productsList.clear();
        productsList.addAll(items);

    }


    public String updateAddressTitle() {
        addressTitle.set(getDataManager().getCurrentAddressTitle());
        return getDataManager().getCurrentAddressTitle();

    }

    public boolean isAddressAdded() {

        return getDataManager().getCurrentLat() != null;

    }

    public void openFilter() {

        getNavigator().openFilter();

    }

    public void openSort() {

        getNavigator().openSort();

    }

    public void fetchProducts() {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


            if (getDataManager().getFilterSort()!=null){

            }else {
                productsRequest.setUserid(getDataManager().getCurrentUserId());
                productsRequest.setLat(getDataManager().getCurrentLat());
                productsRequest.setLon(getDataManager().getCurrentLng());
                productsRequest.setScl2Id(scl2id);






            }




            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PRODUCT_LIST, ProductsResponse.class, productsRequest, new Response.Listener<ProductsResponse>() {

                @Override
                public void onResponse(ProductsResponse response) {

                    if (response != null) {

                        getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                       /* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());*/
                        emptyImageUrl.set(response.getEmptyUrl());
                        emptyContent.set(response.getEmptyContent());
                        emptySubContent.set(response.getEmptySubconent());
                        headerContent.set(response.getHeaderContent());
                        headerSubContent.set(response.getHeaderSubconent());
                        categoryTitle.set(response.getCategoryTitle());

                        if (response.getResult() != null && response.getResult().size() > 0) {
                            fullEmpty.set(false);
                            productsListLiveData.setValue(response.getResult());
                        } else {
                            fullEmpty.set(true);
                        }

                    } else {
                        fullEmpty.set(true);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);

        }


        /*  ProductsPageRequest productsPageRequest = new ProductsPageRequest();
         *//* homePageRequest.setUserid(getDataManager().getCurrentUserId());
        homePageRequest.setLat(getDataManager().getCurrentLat());
        homePageRequest.setLon(getDataManager().getCurrentLng());*//*

        productsPageRequest.setUserid("1");
        productsPageRequest.setLat("12.979937");
        productsPageRequest.setLon( "80.218418");
        Gson gson = new Gson();
      String  json = gson.toJson(productsPageRequest);
        //  getDataManager().setFilterSort(json);


        try {
            setIsLoading(true);
            //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"http://192.168.1.102/tovo/infinity_kitchen.json", new JSONObject(json), new Response.Listener<JSONObject>() {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CATEGORY_LIST, new JSONObject(json), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject homepageResponse) {

                    ProductsResponse response;
                    Gson sGson = new GsonBuilder().create();
                    response = sGson.fromJson(homepageResponse.toString(), ProductsResponse.class);



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



                    }

                }





            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("", ""+error.getMessage());

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