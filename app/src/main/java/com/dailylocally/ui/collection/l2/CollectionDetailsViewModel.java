package com.dailylocally.ui.collection.l2;


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

public class CollectionDetailsViewModel extends BaseViewModel<CollectionDetailsNavigator> {

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
    public ObservableList<CollectionDetailsResponse.Result> categoryList = new ObservableArrayList<>();
    private MutableLiveData<List<CollectionDetailsResponse.Result>> categoryListLiveData;

  public ObservableList<CollectionDetailsResponse.GetSubCatImage> sliderList = new ObservableArrayList<>();
    private MutableLiveData<List<CollectionDetailsResponse.GetSubCatImage>> sliderListLiveData;


    public CollectionDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        sliderListLiveData = new MutableLiveData<>();
        serviceable.set(true);
        getDataManager().saveFiletrSort(null);
        totalCart();
    }


    public void goBack() {

        getNavigator().goBack();

    }

    public void addDatatoList(List<CollectionDetailsResponse.Result> results) {
        categoryList.clear();
        categoryList.addAll(results);
    }

 public void addSlidertoList(List<CollectionDetailsResponse.GetSubCatImage> results) {
        sliderList.clear();
     sliderList.addAll(results);
    }


    public MutableLiveData<List<CollectionDetailsResponse.GetSubCatImage>> getSliderListLiveData() {
        return sliderListLiveData;
    }

    public MutableLiveData<List<CollectionDetailsResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public void setCategoryListLiveData(MutableLiveData<List<CollectionDetailsResponse.Result>> categoryListLiveData) {
        this.categoryListLiveData = categoryListLiveData;
    }

    public void fetchSubCategoryList(String cid) {


            if (!DailylocallyApp.getInstance().onCheckNetWork()){loading.set(false); return;}
loading.set(true);
            CollectionDetailsRequest collectionDetailsRequest = new CollectionDetailsRequest();
            collectionDetailsRequest.setUserid(getDataManager().getCurrentUserId());
            collectionDetailsRequest.setLat(getDataManager().getCurrentLat());
            collectionDetailsRequest.setLon(getDataManager().getCurrentLng());
            collectionDetailsRequest.setCid(cid);


            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COLLECTION_L1_list, CollectionDetailsResponse.class, collectionDetailsRequest, new Response.Listener<CollectionDetailsResponse>() {

                @Override
                public void onResponse(CollectionDetailsResponse response) {

                    if (response != null) {
                        // getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                        serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());


                        if (response.getGetSubCatImages()!=null){
                            sliderListLiveData.setValue(response.getGetSubCatImages());
                        }


                        if (getNavigator() != null)
                            getNavigator().createtabs(response);
                        title.set(response.getTitle());

                    }

                    if (getNavigator()!=null){
                        loading.set(false);
                        getNavigator().dataLoaded();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator()!=null){
                        loading.set(false);
                        getNavigator().dataLoaded();
                    }
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
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+"" +String.valueOf(price));
                items.set("Item");
            } else {
                cartItems.set(count + " Items");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+"" +String.valueOf(price));
                items.set("Items");
            }
        }

    }
}
