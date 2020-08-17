package com.dailylocally.ui.category.l2;


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

public class CategoryL2ViewModel extends BaseViewModel<CategoryL2Navigator> {

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
    public ObservableBoolean loading = new ObservableBoolean();
    public ObservableList<L2CategoryResponse.Result> categoryList = new ObservableArrayList<>();
    public ObservableList<L2CategoryResponse.GetSubCatImage> sliderList = new ObservableArrayList<>();
    private MutableLiveData<List<L2CategoryResponse.Result>> categoryListLiveData;
    private MutableLiveData<List<L2CategoryResponse.GetSubCatImage>> sliderListLiveData;


    public CategoryL2ViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        sliderListLiveData = new MutableLiveData<>();
        serviceable.set(true);
        loading.set(true);
        getDataManager().saveFiletrSort(null);
        totalCart();
    }


    public void goBack() {

        getNavigator().goBack();

    }

    public void addDatatoList(List<L2CategoryResponse.Result> results) {
        categoryList.clear();
        categoryList.addAll(results);
    }

    public void addSlidertoList(List<L2CategoryResponse.GetSubCatImage> results) {
        sliderList.clear();
        sliderList.addAll(results);
    }


    public MutableLiveData<List<L2CategoryResponse.GetSubCatImage>> getSliderListLiveData() {
        return sliderListLiveData;
    }

    public MutableLiveData<List<L2CategoryResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public void setCategoryListLiveData(MutableLiveData<List<L2CategoryResponse.Result>> categoryListLiveData) {
        this.categoryListLiveData = categoryListLiveData;
    }

    public void fetchSubCategoryList(String catid, String scl1id) {
        loading.set(true);

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }

        L2CategoryRequest l2CategoryRequest = new L2CategoryRequest();
        l2CategoryRequest.setUserid(getDataManager().getCurrentUserId());
        l2CategoryRequest.setLat(getDataManager().getCurrentLat());
        l2CategoryRequest.setLon(getDataManager().getCurrentLng());
        l2CategoryRequest.setCatid(catid);
        l2CategoryRequest.setScl1Id(scl1id);


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CATEGORYL2_LIST, L2CategoryResponse.class, l2CategoryRequest, new Response.Listener<L2CategoryResponse>() {

            @Override
            public void onResponse(L2CategoryResponse response) {

                if (response != null) {
                    // getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                    serviceable.set(response.getServiceablestatus());
                    unserviceableTitle.set(response.getUnserviceableTitle());
                    unserviceableSubTitle.set(response.getUnserviceableSubtitle());


                    if (response.getGetSubCatImages() != null) {
                        sliderListLiveData.setValue(response.getGetSubCatImages());
                    }


                    if (getNavigator() != null)
                        getNavigator().createtabs(response);
                    title.set(response.getCategoryTitle());
                    image.set(response.getGetSubCatImages().get(0).getImageUrl());

                }
                if (getNavigator() != null)
                    getNavigator().dataLoaded();
                loading.set(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getNavigator() != null)
                    getNavigator().dataLoaded();
                loading.set(false);
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


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
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())));
                }

            }
        }

        if (count <= 0) {
            cart.set(false);
        } else {
            cart.set(true);

            if (count == 1) {
                cartItems.set(count + " Item");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + "" + String.valueOf(price));
                items.set("Item");
            } else {
                cartItems.set(count + " Items");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + "" + String.valueOf(price));
                items.set("Items");
            }
        }

    }
}
