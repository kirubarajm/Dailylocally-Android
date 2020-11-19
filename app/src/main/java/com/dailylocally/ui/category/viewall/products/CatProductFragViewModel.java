package com.dailylocally.ui.category.viewall.products;


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
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.category.l2.products.ProductListAdapter;
import com.dailylocally.ui.category.l2.products.ProductsNavigator;
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.ui.category.l2.products.ProductsResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CatProductFragViewModel extends BaseViewModel<ProductsNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean fullEmpty = new ObservableBoolean();
    public final ObservableBoolean loading = new ObservableBoolean();
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
    public boolean loadingMore;
    public ObservableList<ProductsResponse.Result> productsList = new ObservableArrayList<>();
    public ProductsRequest productsRequest = new ProductsRequest();
    public String catid;
    public String scl1id;
    public Integer page = 0;
    private MutableLiveData<List<ProductsResponse.Result>> productsListLiveData;

    public CatProductFragViewModel(DataManager dataManager) {
        super(dataManager);
        productsListLiveData = new MutableLiveData<>();
        getDataManager().saveFiletrSort(null);

    }

    public MutableLiveData<List<ProductsResponse.Result>> getProductsListLiveData() {
        return productsListLiveData;
    }

    public void addCategoryToList(List<ProductsResponse.Result> items) {
       // if (page == 1)
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
    public void openSort() {

        getNavigator().openSort();

    }


    public void loadMoreProducts(ProductListAdapter productListAdapter) {

        if (page > 0)
            if (!loadingMore) {
                if (!DailylocallyApp.getInstance().onCheckNetWork()) {
                    loadingMore = false;
                    loading.set(false);
                    return;
                }
                loadingMore = true;
                productListAdapter.addLoader();
                page = page + 1;
                productsRequest.setUserid(getDataManager().getCurrentUserId());
                productsRequest.setLat(getDataManager().getCurrentLat());
                productsRequest.setLon(getDataManager().getCurrentLng());
                productsRequest.setCatid(catid);
                productsRequest.setScl1Id(scl1id);
                productsRequest.setPage(page);


                GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CAT_PRODUCT_LIST, ProductsResponse.class, productsRequest, new Response.Listener<ProductsResponse>() {

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
                                productsListLiveData.postValue(response.getResult());
                            } else {
                                // fullEmpty.set(true);
                                page = page - 1;
                            }
                        } else {
                            // fullEmpty.set(true);
                            page = page - 1;
                        }
                        if (getNavigator() != null) {
                            loadingMore = false;
                            loading.set(false);
                            getNavigator().moreDataLoaded();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingMore = false;
                        loading.set(false);
                        page = page - 1;
                        getNavigator().moreDataLoaded();
                    }
                }, AppConstants.API_VERSION_ONE);
                DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);

            }

    }

    public void fetchProducts() {
        page = 1;
        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        loading.set(true);
        productsRequest = new ProductsRequest();
        productsRequest.setUserid(getDataManager().getCurrentUserId());
        productsRequest.setLat(getDataManager().getCurrentLat());
        productsRequest.setLon(getDataManager().getCurrentLng());
        productsRequest.setCatid(catid);
        productsRequest.setScl1Id(scl1id);
     //   productsRequest.setPage(page);

        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CAT_PRODUCT_LIST, ProductsResponse.class, productsRequest, new Response.Listener<ProductsResponse>() {

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
                        page = page - 1;
                    }
                    loading.set(false);
                } else {
                    fullEmpty.set(true);
                    page = page - 1;
                }
                if (getNavigator() != null) {
                    loading.set(false);
                    getNavigator().dataLoaded();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                page = page - 1;
                if (getNavigator() != null) {
                    loading.set(false);
                    getNavigator().dataLoaded();
                }
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


    }


    public void checkScl2Filter(String sScl1id) {

        if (!scl1id.equals(sScl1id)) {
            loading.set(false);
            return;
        }

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }

        ProductsRequest fProductsRequest = new ProductsRequest();

        page = 1;

        if (getDataManager().getFilterSort() != null) {

            Gson sGson = new GsonBuilder().create();
            fProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), ProductsRequest.class);

            if (fProductsRequest != null) {
                fProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fProductsRequest.setLat(getDataManager().getCurrentLat());
                fProductsRequest.setLon(getDataManager().getCurrentLng());
                fProductsRequest.setCatid(String.valueOf(catid));
                fProductsRequest.setScl1Id(String.valueOf(scl1id));
             //   fProductsRequest.setPage(page);

            } else {
                fProductsRequest = new ProductsRequest();
                fProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fProductsRequest.setLat(getDataManager().getCurrentLat());
                fProductsRequest.setLon(getDataManager().getCurrentLng());
                fProductsRequest.setCatid(String.valueOf(catid));
                fProductsRequest.setScl1Id(String.valueOf(scl1id));
              //  fProductsRequest.setPage(page);
            }

        } else {
            fProductsRequest = new ProductsRequest();
            fProductsRequest.setUserid(getDataManager().getCurrentUserId());
            fProductsRequest.setLat(getDataManager().getCurrentLat());
            fProductsRequest.setLon(getDataManager().getCurrentLng());
            fProductsRequest.setCatid(String.valueOf(catid));
            fProductsRequest.setScl1Id(String.valueOf(scl1id));
           // fProductsRequest.setPage(page);
        }


        Gson gson = new Gson();
        String request = gson.toJson(fProductsRequest);
        getDataManager().saveFiletrSort(request);
        productsRequest = fProductsRequest;
        fetchFilterProducts(fProductsRequest);

    }


    public void fetchFilterProducts(ProductsRequest request) {


        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
        Gson gson = new Gson();
        String filterRequest = gson.toJson(request);

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CAT_PRODUCT_LIST, new JSONObject(filterRequest), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (response != null) {

                        Gson gson = new Gson();
                        ProductsResponse productsResponse = gson.fromJson(response.toString(), ProductsResponse.class);


                        if (response != null) {

                            getDataManager().saveServiceableStatus(false, productsResponse.getUnserviceableTitle(), productsResponse.getUnserviceableSubtitle());
                       /* serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());*/
                            emptyImageUrl.set(productsResponse.getEmptyUrl());
                            emptyContent.set(productsResponse.getEmptyContent());
                            emptySubContent.set(productsResponse.getEmptySubconent());
                            headerContent.set(productsResponse.getHeaderContent());
                            headerSubContent.set(productsResponse.getHeaderSubconent());
                            categoryTitle.set(productsResponse.getCategoryTitle());

                            if (productsResponse.getResult() != null && productsResponse.getResult().size() > 0) {
                                fullEmpty.set(false);
                                productsListLiveData.postValue(productsResponse.getResult());
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