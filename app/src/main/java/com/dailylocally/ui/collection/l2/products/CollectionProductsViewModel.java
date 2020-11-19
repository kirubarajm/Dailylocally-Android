package com.dailylocally.ui.collection.l2.products;


import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.BuildConfig;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.ui.community.CommunityUserDetailsResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionProductsViewModel extends BaseViewModel<CollectionProductsNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean loading = new ObservableBoolean();
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


    public ObservableList<CollectionProductsResponse.Result> productsList = new ObservableArrayList<>();
    public ProductsRequest collectionProductsRequest = new ProductsRequest();
    public String scl1id;
    public String cid;
    private MutableLiveData<List<CollectionProductsResponse.Result>> productsListLiveData;

    public CollectionProductsViewModel(DataManager dataManager) {
        super(dataManager);
        productsListLiveData = new MutableLiveData<>();
        getDataManager().saveFiletrSort(null);
//fetchProducts(1);
    }

    public MutableLiveData<List<CollectionProductsResponse.Result>> getProductsListLiveData() {
        return productsListLiveData;
    }

    public void addCategoryToList(List<CollectionProductsResponse.Result> items) {
        productsList.clear();
        productsList.addAll(items);

    }

    public int totalCart() {
        Gson sGson = new GsonBuilder().create();
        CartRequest CartRequest = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);

        if (CartRequest == null)
            CartRequest = new CartRequest();
        int count = 0;
        int price = 0;
        if (CartRequest.getOrderitems() != null) {
            if (CartRequest.getOrderitems().size() == 0) {
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
            } else {
                count = count + CartRequest.getSubscription().size();

                for (int i = 0; i < CartRequest.getSubscription().size(); i++) {
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())));
                }

            }
        }
        return price;

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

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);

            collectionProductsRequest.setUserid(getDataManager().getCurrentUserId());
            collectionProductsRequest.setLat(getDataManager().getCurrentLat());
            collectionProductsRequest.setLon(getDataManager().getCurrentLng());
            collectionProductsRequest.setScl1Id(scl1id);
            collectionProductsRequest.setCid(cid);


            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COLLECTION_PRODUCT_LIST, CollectionProductsResponse.class, collectionProductsRequest, new Response.Listener<CollectionProductsResponse>() {

                @Override
                public void onResponse(CollectionProductsResponse response) {

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
                    if (getNavigator() != null) {
                        getNavigator().DataLoaded();
                        loading.set(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator() != null) {
                        getNavigator().DataLoaded();
                        loading.set(false);
                    }
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);




    }


    public void checkScl1Filter(String sScl1id) {

        if (!scl1id.equals(sScl1id)) {
            return;
        }
        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);

        ProductsRequest fCollectionProductsRequest = new ProductsRequest();

        if (getDataManager().getFilterSort() != null) {

            Gson sGson = new GsonBuilder().create();
            fCollectionProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), ProductsRequest.class);

            if (fCollectionProductsRequest != null) {
                fCollectionProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fCollectionProductsRequest.setLat(getDataManager().getCurrentLat());
                fCollectionProductsRequest.setLon(getDataManager().getCurrentLng());
                fCollectionProductsRequest.setScl1Id(String.valueOf(scl1id));
                fCollectionProductsRequest.setCid(String.valueOf(cid));


            } else {
                fCollectionProductsRequest = new ProductsRequest();
                fCollectionProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fCollectionProductsRequest.setLat(getDataManager().getCurrentLat());
                fCollectionProductsRequest.setLon(getDataManager().getCurrentLng());
                fCollectionProductsRequest.setScl1Id(String.valueOf(scl1id));
                fCollectionProductsRequest.setCid(String.valueOf(cid));
            }

        } else {
            fCollectionProductsRequest = new ProductsRequest();
            fCollectionProductsRequest.setUserid(getDataManager().getCurrentUserId());
            fCollectionProductsRequest.setLat(getDataManager().getCurrentLat());
            fCollectionProductsRequest.setLon(getDataManager().getCurrentLng());
            fCollectionProductsRequest.setScl1Id(String.valueOf(scl1id));
            fCollectionProductsRequest.setCid(String.valueOf(cid));
        }


        Gson gson = new Gson();
        String request = gson.toJson(fCollectionProductsRequest);
        getDataManager().saveFiletrSort(request);

        fetchFilterProducts(fCollectionProductsRequest);

    }


    public void fetchFilterProducts(ProductsRequest request) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);
       /* Gson gson = new Gson();
        String filterRequest = gson.toJson(request);

        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COLLECTION_PRODUCT_LIST, CollectionProductsResponse.class, request, new Response.Listener<CollectionProductsResponse>() {

            @Override
            public void onResponse(CollectionProductsResponse response) {

                if (response != null) {

                    getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                       *//* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());*//*
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
                if (getNavigator() != null) {
                    getNavigator().DataLoaded();
                    loading.set(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getNavigator() != null) {
                    getNavigator().DataLoaded();
                    loading.set(false);
                }
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);*/


        Gson gson = new Gson();
        String filterRequest = gson.toJson(request);

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COLLECTION_PRODUCT_LIST, new JSONObject(filterRequest), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jresponse) {

                    if (jresponse != null) {

                        Gson sGson = new GsonBuilder().create();
                        CollectionProductsResponse response = sGson.fromJson(jresponse.toString(), CollectionProductsResponse.class);

                        getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                    //    unserviceableTitle.set(response.getUnserviceableTitle());
                      //  unserviceableSubTitle.set(response.getUnserviceableSubtitle());
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
                    if (getNavigator() != null) {
                        getNavigator().DataLoaded();
                        loading.set(false);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator() != null) {
                        getNavigator().DataLoaded();
                        loading.set(false);
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    //  headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("accept-version", AppConstants.API_VERSION_ONE);
                    headers.put("app-version", String.valueOf(BuildConfig.VERSION_CODE));
                    headers.put("apptype", AppConstants.APP_TYPE_ANDROID);
                    //  headers.put("Authorization","Bearer");
                    AppPreferencesHelper preferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
                    headers.put("Authorization", "Bearer " + preferencesHelper.getApiToken());



                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception j) {
            j.printStackTrace();
        }


    }









}