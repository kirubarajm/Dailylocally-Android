package com.dailylocally.ui.favourites;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class FavViewModel extends BaseViewModel<FavNavigator> {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> noProductsString = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableList<FavResponse.Result> categoryList = new ObservableArrayList<>();
    private MutableLiveData<List<FavResponse.Result>> categoryListLiveData;

    public FavViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        getDataManager().saveFiletrSort(null);
        totalCart();
        fetchSubCategoryList();
    }


    public void goBack() {

        getNavigator().goBack();

    }

    public void addDatatoList(List<FavResponse.Result> results) {
        categoryList.clear();
        categoryList.addAll(results);
    }



    public MutableLiveData<List<FavResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public void setCategoryListLiveData(MutableLiveData<List<FavResponse.Result>> categoryListLiveData) {
        this.categoryListLiveData = categoryListLiveData;
    }

    public void fetchSubCategoryList() {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            FavRequest favRequest = new FavRequest(getDataManager().getCurrentUserId());

            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_FAV_CAT_lIST, FavResponse.class, favRequest, new Response.Listener<FavResponse>() {

                @Override
                public void onResponse(FavResponse response) {

                    if (response != null) {

                        if (getNavigator() != null)
                            getNavigator().createtabs(response);

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

    public void viewCart() {
        getNavigator().viewCart();
    }
    public void totalCart() {
        Gson sGson = new GsonBuilder().create();
        CartRequest CartRequest = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        cart.set(false);
        if (CartRequest == null)
            CartRequest = new CartRequest();
        int count = 0;
        int price = 0;
        if (CartRequest.getOrderitems() != null) {
            if (CartRequest.getOrderitems().size() == 0) {
                cart.set(false);
            } else {

                count = count + CartRequest.getOrderitems().size();

                for (int i = 0; i < CartRequest.getOrderitems().size(); i++) {
                  //  count = count + CartRequest.getOrderitems().get(i).getQuantity();
                    price = price + ((Integer.parseInt(CartRequest.getOrderitems().get(i).getPrice())) * CartRequest.getOrderitems().get(i).getQuantity());
                }

            }
        }


        if (CartRequest.getSubscription() != null) {
            if (CartRequest.getSubscription().size() == 0) {
                cart.set(false);
            } else {
                count = count + CartRequest.getSubscription().size();

                for (int i = 0; i < CartRequest.getSubscription().size(); i++) {
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())) );
                }

            }
        }

        if (count <= 0) {
            cart.set(false);
        } else {
            cart.set(true);

            if (count == 1) {
                cartItems.set(count + " Item");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+" " +String.valueOf(price));
                items.set("Item");
            } else {
                cartItems.set(count + " Items");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+" " +String.valueOf(price));
                items.set("Items");
            }
        }

    }
}
