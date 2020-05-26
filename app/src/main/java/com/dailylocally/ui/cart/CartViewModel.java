package com.dailylocally.ui.cart;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.dailylocally.utilities.CartRequestPojo;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartViewModel extends BaseViewModel<CartNavigator> {


    public final ObservableField<String> makeit_image = new ObservableField<>();

    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> grand_total = new ObservableField<>();
    public final ObservableField<String> refundFare = new ObservableField<>();
    public final ObservableField<String> couponFare = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> makeit_brand_name = new ObservableField<>();
    public final ObservableField<String> buttonText = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> current_address = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> localityname = new ObservableField<>();
    public final ObservableField<String> promocode = new ObservableField<>();
    public final ObservableField<String> grandTotalTitle = new ObservableField<>();
    public final ObservableField<String> lowCost = new ObservableField<>();
    public final ObservableField<String> lowCostShort = new ObservableField<>();
    public final ObservableField<Integer> refundBalance = new ObservableField<>();
    public final ObservableField<String> statusMessage = new ObservableField<>();
    public final ObservableField<String> cuisines = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> changeAddress = new ObservableField<>();
    public final ObservableBoolean payment = new ObservableBoolean();
    public final ObservableBoolean lowCostStatus = new ObservableBoolean();
    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean emptyCart = new ObservableBoolean();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean suggestedProduct = new ObservableBoolean();
    public final ObservableBoolean available = new ObservableBoolean();
    public final ObservableBoolean refunds = new ObservableBoolean();
    public final ObservableBoolean refundSelected = new ObservableBoolean();
    public final ObservableBoolean couponSelected = new ObservableBoolean();
    public final ObservableBoolean refundChecked = new ObservableBoolean();
    public final ObservableBoolean instructionClicked = new ObservableBoolean();
    public final ObservableBoolean xfactorClick = new ObservableBoolean();
    public final ObservableField<String> previousPage = new ObservableField<>();

    public MutableLiveData<List<CartResponse.Item>> ordernowLiveData;
    public ObservableList<CartResponse.Item> ordernowItemViewModels = new ObservableArrayList<>();

    public MutableLiveData<List<CartResponse.SubscriptionItem>> subscribeLiveData;
    public ObservableList<CartResponse.SubscriptionItem> subscribeItemViewModels = new ObservableArrayList<>();


    public MutableLiveData<List<CartResponse.Cartdetail>> cartBillLiveData;
    public ObservableList<CartResponse.Cartdetail> cartdetails = new ObservableArrayList<>();
    public int funnelStatus = 0;
    int favId;
    Long makeitId;
    int totalAmount;
    int minCartValue = 0;
    String isFav;


    public CartViewModel(DataManager dataManager) {
        super(dataManager);
        grand_total.set("0");

        xfactorClick.set(true);
        lowCostStatus.set(true);
        serviceable.set(true);

        cartBillLiveData = new MutableLiveData<>();
        ordernowLiveData = new MutableLiveData<>();
        subscribeLiveData = new MutableLiveData<>();

    }


    public MutableLiveData<List<CartResponse.Item>> getOrdernowLiveData() {
        return ordernowLiveData;
    }

    public MutableLiveData<List<CartResponse.SubscriptionItem>> getSubscribeLiveData() {
        return subscribeLiveData;
    }

    public MutableLiveData<List<CartResponse.Cartdetail>> getCartBillLiveData() {
        return cartBillLiveData;
    }


    public void addBillItemsToList(List<CartResponse.Cartdetail> results) {
        cartdetails.clear();
        cartdetails.addAll(results);

    }

 public void addOrderNowItemsToList(List<CartResponse.Item> results) {
        ordernowItemViewModels.clear();
     ordernowItemViewModels.addAll(results);

    }
 public void addSubscriptionItemsToList(List<CartResponse.SubscriptionItem> results) {
        subscribeItemViewModels.clear();
     subscribeItemViewModels.addAll(results);

    }


    public void setAddressTitle() {

        address.set(getDataManager().getCurrentAddressTitle());
        current_address.set(getDataManager().getCurrentAddress());

        if (getDataManager().getAddressId() == null) {
            changeAddress.set("Add Address");
        } else {

            changeAddress.set("Change");
        }

    }


    public void saveToCartPojo(String cartJsonString) {

        getDataManager().setCartDetails(cartJsonString);

        if (getDataManager().getCartDetails() == null) {
            getNavigator().emptyCart();
            emptyCart.set(true);
        }


    }


    public String getCartPojoDetails() {

        if (getNavigator() != null)
            getNavigator().clearToolTips();


        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            emptyCart.set(true);
            return null;

        } else {
            if (cartRequestPojo.getCartitems() != null) {
                if (cartRequestPojo.getCartitems().size() == 0) {
                    getDataManager().setCartDetails(null);
                    emptyCart.set(true);
                    return null;
                } else {
                    emptyCart.set(false);
                }
            } else {
                emptyCart.set(true);
            }
        }

        return getDataManager().getCartDetails();
    }




    public void goHome() {
        getNavigator().redirectHome();

    }

    public void changeAddress() {

        getNavigator().changeAddress();
    }

    public void proceedtopay() {


    }


    public void fetchRepos() {
        if (getNavigator() != null)
            getNavigator().clearToolTips();



        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

        if (getCartPojoDetails() != null) {

            Gson sGson = new GsonBuilder().create();
            CartRequest cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);

            cartRequestPojo.setUserid(getDataManager().getCurrentUserId());
            cartRequestPojo.setLat(getDataManager().getCurrentLat());
            cartRequestPojo.setLon(getDataManager().getCurrentLng());

            Gson gson = new Gson();
            String carts = gson.toJson(cartRequestPojo);

            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CART_DETAILS, new JSONObject(carts), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            Gson gson = new Gson();
                            CartResponse cartPageResponse = gson.fromJson(response.toString(), CartResponse.class);

                            //    if (cartPageResponse.getStatus()) {

                            statusMessage.set(cartPageResponse.getMessage());
                            available.set(cartPageResponse.getStatus());

                            if (!cartPageResponse.getStatus()) {

                                Toast.makeText(DailylocallyApp.getInstance(), cartPageResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            if (cartPageResponse.getResult() != null) {
                                if (cartPageResponse.getResult().get(0).getItem().size() == 0) {
                                    if (getNavigator() != null) {
                                        getNavigator().emptyCart();
                                        getNavigator().clearToolTips();
                                    }
                                 //   getDataManager().setCartDetails(null);

                                    emptyCart.set(true);


                                } else {
                                    ordernowLiveData.setValue(cartPageResponse.getResult().get(0).getItem());
                                   subscribeLiveData.setValue(cartPageResponse.getResult().get(0).getSubscriptionItem());
                                    emptyCart.set(false);

                                    cartBillLiveData.setValue(cartPageResponse.getResult().get(0).getCartdetails());


                                    totalAmount = cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal();


                                    total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getProductOrginalPrice()));

                                    grand_total.set(String.valueOf(totalAmount));

                                    grandTotalTitle.set(cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotaltitle());


                                    gst.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                                    delivery_charge.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));




                                    if (cartPageResponse.getResult().get(0).getAmountdetails() != null) {
                                        lowCostStatus.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitStatus());
                                        lowCost.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitMessage());
                                        lowCostShort.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitShortMessage());
                                    }


                                }


                                setAddressTitle();

                                serviceable.set(cartPageResponse.getResult().get(0).getIsAvaliablekitchen());

                                try {
                                    if (getNavigator() != null)
                                        getNavigator().cartLoaded();
                                } catch (Exception re) {
                                    re.printStackTrace();
                                }
                            }
                        }



                        if (getNavigator() != null)
                            getNavigator().cartLoaded();

                        if (getNavigator() != null) {
                            getNavigator().metricsCartOpen();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emptyCart.set(true);
                        if (getNavigator() != null)
                            getNavigator().cartLoaded();
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
            } catch (JSONException j) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                j.printStackTrace();
            } catch (NullPointerException n) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                n.printStackTrace();
            } catch (Exception ee) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                ee.printStackTrace();
            }

        } else {
            emptyCart.set(true);
        }
    }


}
