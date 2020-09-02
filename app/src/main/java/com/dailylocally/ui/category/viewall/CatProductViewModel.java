package com.dailylocally.ui.category.viewall;


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
import com.dailylocally.ui.category.l1.L1CategoryRequest;
import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class CatProductViewModel extends BaseViewModel<CatProductNavigator> {

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


    public CatProductViewModel(DataManager dataManager) {
        super(dataManager);

        serviceable.set(true);
        loading.set(true);
        getDataManager().saveFiletrSort(null);
        totalCart();
    }


    public void goBack() {

        getNavigator().goBack();

    }

    public void fetchSubCategoryList(String catid) {

        loading.set(true);

        if (!DailylocallyApp.getInstance().onCheckNetWork()) {
            loading.set(false);
            return;
        }
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
                        serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());

                        title.set(response.getCategoryTitle());
                        //   image.set(response.get());

                        if (response.getGetSubCatImages()!=null&&response.getGetSubCatImages().size()>0){
                            image.set(response.getGetSubCatImages().get(0).getImageUrl());
                        }


                        if (response.getResult() != null && response.getResult().size() > 0) {
                            if (getNavigator() != null)
                                getNavigator().createtabs(response);
                        }
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
