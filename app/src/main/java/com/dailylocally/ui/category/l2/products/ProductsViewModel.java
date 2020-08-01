package com.dailylocally.ui.category.l2.products;


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
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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
    public ProductsRequest productsRequest = new ProductsRequest();
    public String scl2id;
    public String scl1id;
    public Integer page = 0;
    private MutableLiveData<List<ProductsResponse.Result>> productsListLiveData;

    public ProductsViewModel(DataManager dataManager) {
        super(dataManager);
        productsListLiveData = new MutableLiveData<>();
        getDataManager().saveFiletrSort(null);
//fetchProducts(1);
    }

    public MutableLiveData<List<ProductsResponse.Result>> getProductsListLiveData() {
        return productsListLiveData;
    }

    public void addCategoryToList(List<ProductsResponse.Result> items) {
        if (page == 0)
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


    public void loadMoreProducts() {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


            productsRequest.setUserid(getDataManager().getCurrentUserId());
            productsRequest.setLat(getDataManager().getCurrentLat());
            productsRequest.setLon(getDataManager().getCurrentLng());
            productsRequest.setScl2Id(scl2id);
            productsRequest.setScl1Id(scl1id);
            productsRequest.setPage(page + 1);


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

    }

    public void fetchProducts() {
        page = 1;
        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
            productsRequest.setUserid(getDataManager().getCurrentUserId());
            productsRequest.setLat(getDataManager().getCurrentLat());
            productsRequest.setLon(getDataManager().getCurrentLng());
            productsRequest.setScl2Id(scl2id);
            productsRequest.setScl1Id(scl1id);
          //  productsRequest.setPage(0);

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


    public void checkScl2Filter(String sScl2id) {

        if (!scl2id.equals(sScl2id)) {
            return;
        }
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

        ProductsRequest fProductsRequest = new ProductsRequest();

        page = 0;

        if (getDataManager().getFilterSort() != null) {

            Gson sGson = new GsonBuilder().create();
            fProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), ProductsRequest.class);

            if (fProductsRequest != null) {
                fProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fProductsRequest.setLat(getDataManager().getCurrentLat());
                fProductsRequest.setLon(getDataManager().getCurrentLng());
                fProductsRequest.setScl2Id(String.valueOf(scl2id));
                fProductsRequest.setScl1Id(String.valueOf(scl1id));
                productsRequest.setPage(page);


            } else {
                fProductsRequest = new ProductsRequest();
                fProductsRequest.setUserid(getDataManager().getCurrentUserId());
                fProductsRequest.setLat(getDataManager().getCurrentLat());
                fProductsRequest.setLon(getDataManager().getCurrentLng());
                fProductsRequest.setScl2Id(String.valueOf(scl2id));
                fProductsRequest.setScl1Id(String.valueOf(scl1id));
                productsRequest.setPage(page);
            }

        } else {
            fProductsRequest = new ProductsRequest();
            fProductsRequest.setUserid(getDataManager().getCurrentUserId());
            fProductsRequest.setLat(getDataManager().getCurrentLat());
            fProductsRequest.setLon(getDataManager().getCurrentLng());
            fProductsRequest.setScl2Id(String.valueOf(scl2id));
            fProductsRequest.setScl1Id(String.valueOf(scl1id));
            productsRequest.setPage(page);
        }


        Gson gson = new Gson();
        String request = gson.toJson(fProductsRequest);
        getDataManager().saveFiletrSort(request);

        fetchFilterProducts(fProductsRequest);

    }


    public void fetchFilterProducts(ProductsRequest request) {

        if (getDataManager().getCurrentLat() != null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


            /*GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PRODUCT_LIST, ProductsResponse.class, request, new Response.Listener<ProductsResponse>() {

                @Override
                public void onResponse(ProductsResponse response) {

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

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);*/


            Gson gson = new Gson();
            String filterRequest = gson.toJson(request);

            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_PRODUCT_LIST, new JSONObject(filterRequest), new Response.Listener<JSONObject>() {
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
                                    productsListLiveData.setValue(productsResponse.getResult());
                                } else {
                                    fullEmpty.set(true);
                                }

                            } else {
                                fullEmpty.set(true);
                            }


                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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


}