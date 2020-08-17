package com.dailylocally.ui.favourites.products;


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
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class FavProductsViewModel extends BaseViewModel<FavProductsNavigator> {

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


    public ObservableList<FavProductsResponse.Result> productsList = new ObservableArrayList<>();
    public ProductsRequest favProductsRequest = new ProductsRequest();
    public String catid;
    private MutableLiveData<List<FavProductsResponse.Result>> productsListLiveData;

    public FavProductsViewModel(DataManager dataManager) {
        super(dataManager);
        productsListLiveData = new MutableLiveData<>();
        getDataManager().saveFiletrSort(null);
//fetchProducts(1);
        serviceable.set(true);
    }

    public MutableLiveData<List<FavProductsResponse.Result>> getProductsListLiveData() {
        return productsListLiveData;
    }

    public void addCategoryToList(List<FavProductsResponse.Result> items) {
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


    public void fetchProducts(String sCatid) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);


        favProductsRequest.setUserid(getDataManager().getCurrentUserId());
        favProductsRequest.setLat(getDataManager().getCurrentLat());
        favProductsRequest.setLon(getDataManager().getCurrentLng());
        favProductsRequest.setCatid(sCatid);


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_FAV_PRODUCT_LIST, FavProductsResponse.class, favProductsRequest, new Response.Listener<FavProductsResponse>() {

            @Override
            public void onResponse(FavProductsResponse response) {

                if (response != null) {

                    //  getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
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
                    getNavigator().DataLoaded(response);
                    loading.set(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getNavigator() != null) {
                    loading.set(false);
                    getNavigator().dataLoaded();
                }
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);

    }


    public void checkScl1Filter(String sCatid) {

        if (!catid.equals(sCatid)) {
            return;
        }

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);

        ProductsRequest fFavProductsRequest = new ProductsRequest();

        if (getDataManager().getFilterSort() != null) {

            Gson sGson = new GsonBuilder().create();
            fFavProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), ProductsRequest.class);

            if (fFavProductsRequest != null) {
                fFavProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fFavProductsRequest.setLat(getDataManager().getCurrentLat());
                fFavProductsRequest.setLon(getDataManager().getCurrentLng());
                fFavProductsRequest.setCatid(String.valueOf(catid));


            } else {
                fFavProductsRequest = new ProductsRequest();
                fFavProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fFavProductsRequest.setLat(getDataManager().getCurrentLat());
                fFavProductsRequest.setLon(getDataManager().getCurrentLng());
                fFavProductsRequest.setCatid(String.valueOf(catid));
            }

        } else {
            fFavProductsRequest = new ProductsRequest();
            fFavProductsRequest.setUserid(getDataManager().getCurrentUserId());
            fFavProductsRequest.setLat(getDataManager().getCurrentLat());
            fFavProductsRequest.setLon(getDataManager().getCurrentLng());
            fFavProductsRequest.setCatid(String.valueOf(catid));
        }


        Gson gson = new Gson();
        String request = gson.toJson(fFavProductsRequest);
        getDataManager().saveFiletrSort(request);

        fetchFilterProducts(fFavProductsRequest);

    }


    public void fetchFilterProducts(ProductsRequest request) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);


        Gson gson = new Gson();
        String filterRequest = gson.toJson(request);

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_FAV_PRODUCT_LIST, new JSONObject(filterRequest), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (response != null) {

                        Gson gson = new Gson();
                        FavProductsResponse favProductsResponse = gson.fromJson(response.toString(), FavProductsResponse.class);


                        if (response != null) {

                            getDataManager().saveServiceableStatus(false, favProductsResponse.getUnserviceableTitle(), favProductsResponse.getUnserviceableSubtitle());
                       /* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());*/
                            emptyImageUrl.set(favProductsResponse.getEmptyUrl());
                            emptyContent.set(favProductsResponse.getEmptyContent());
                            emptySubContent.set(favProductsResponse.getEmptySubconent());
                            headerContent.set(favProductsResponse.getHeaderContent());
                            headerSubContent.set(favProductsResponse.getHeaderSubconent());
                            categoryTitle.set(favProductsResponse.getCategoryTitle());

                            if (favProductsResponse.getResult() != null && favProductsResponse.getResult().size() > 0) {
                                fullEmpty.set(false);
                                productsListLiveData.setValue(favProductsResponse.getResult());
                            } else {
                                fullEmpty.set(true);
                            }

                        } else {
                            fullEmpty.set(true);
                        }


                    }

                    if (getNavigator() != null) {
                        loading.set(false);
                        getNavigator().dataLoaded();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator() != null) {
                        loading.set(false);
                        getNavigator().dataLoaded();
                    }
                }
            }) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
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