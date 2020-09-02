package com.dailylocally.ui.cart;

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
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.community.CommunityUserDetailsResponse;
import com.dailylocally.ui.subscription.StartDateResponse;
import com.dailylocally.ui.subscription.SubscriptionRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartViewModel extends BaseViewModel<CartNavigator> {


    public final ObservableField<String> makeit_image = new ObservableField<>();

    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> grand_total = new ObservableField<>();
    public final ObservableField<String> bookDeliveryText = new ObservableField<>();
    public final ObservableField<String> refundFare = new ObservableField<>();
    public final ObservableField<String> couponFare = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> delivery_charge_text = new ObservableField<>();
    public final ObservableField<String> exclusiveTag = new ObservableField<>();
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
    public final ObservableField<String> couponName = new ObservableField<>();
    public final ObservableBoolean payment = new ObservableBoolean();
    public final ObservableBoolean lowCostStatus = new ObservableBoolean();
    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean emptyCart = new ObservableBoolean();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean suggestedProduct = new ObservableBoolean();
    public final ObservableBoolean available = new ObservableBoolean();
    public final ObservableBoolean communityUser = new ObservableBoolean();
    public final ObservableBoolean showFreeDelivery = new ObservableBoolean();
    public final ObservableBoolean showPlaceOrderButton = new ObservableBoolean();
    public final ObservableBoolean showSubscription = new ObservableBoolean();
    public final ObservableBoolean refunds = new ObservableBoolean();
    public final ObservableBoolean refundSelected = new ObservableBoolean();
    public final ObservableBoolean couponSelected = new ObservableBoolean();
    public final ObservableBoolean refundChecked = new ObservableBoolean();
    public final ObservableBoolean instructionClicked = new ObservableBoolean();
    public final ObservableBoolean xfactorClick = new ObservableBoolean();
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableBoolean couponApplied = new ObservableBoolean();
    public final ObservableBoolean showWarningNote = new ObservableBoolean();
    public final ObservableField<String> previousPage = new ObservableField<>();
    private final List<CartRequest.Subscription> results = new ArrayList<>();
    public MutableLiveData<List<CartResponse.Item>> ordernowLiveData;
    public ObservableList<CartResponse.Item> ordernowItemViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<CartResponse.SubscriptionItem>> subscribeLiveData;
    public ObservableList<CartResponse.SubscriptionItem> subscribeItemViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<CartResponse.Cartdetail>> cartBillLiveData;
    public ObservableList<CartResponse.Cartdetail> cartdetails = new ObservableArrayList<>();
    public int funnelStatus = 0;
    public String availableDate;

    int favId;
    Long makeitId;
    int totalAmount;
    int minCartValue = 0;
    String isFav;
    private CartRequest cartRequestPojo = new CartRequest();


    public CartViewModel(DataManager dataManager) {
        super(dataManager);
        //  getStartDate();
        grand_total.set("0");

        xfactorClick.set(true);
        lowCostStatus.set(true);
        serviceable.set(true);

        cartBillLiveData = new MutableLiveData<>();
        ordernowLiveData = new MutableLiveData<>();
        subscribeLiveData = new MutableLiveData<>();


    }


    public void getStartDate() {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_GET_START_DATE, StartDateResponse.class, new SubscriptionRequest(getDataManager().getCurrentUserId()), new Response.Listener<StartDateResponse>() {

            @Override
            public void onResponse(StartDateResponse response) {
                if (response != null) {

                    availableDate = response.getOrderDeliveryDay();
                    fetchRepos();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


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

        if (communityUser.get()) {
            if (getDataManager().getUserDetails() != null) {

                Gson sGson = new GsonBuilder().create();
                CommunityUserDetailsResponse communityUserDetailsResponse = sGson.fromJson(getDataManager().getUserDetails(), CommunityUserDetailsResponse.class);
                if (communityUserDetailsResponse != null) {
                    if (communityUserDetailsResponse.getResult() != null) {
                        if (communityUserDetailsResponse.getResult().size() > 0) {
                            CommunityUserDetailsResponse.Result result = communityUserDetailsResponse.getResult().get(0);

                            current_address.set(result.getCommunityName() + ", " + result.getCommunityArea());


                        }
                    }

                }

            }
        } else {
            address.set(getDataManager().getCurrentAddressTitle());
            current_address.set(getDataManager().getCurrentAddress());
        }




       /* if (getDataManager().getAddressId() == null) {
            changeAddress.set("Add Address");
        } else {

            changeAddress.set("Change");
        }*/

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
        CartRequest cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null) {
            emptyCart.set(true);
            return null;
        } else {

           /* if (cartRequestPojo.getCartitems() != null) {
                if (cartRequestPojo.getCartitems().size() == 0) {
                    getDataManager().setCartDetails(null);
                    emptyCart.set(true);
                    return null;
                } else {
                    emptyCart.set(false);
                }
            } else {
                emptyCart.set(true);
            }*/


            if (cartRequestPojo.getSubscription() == null && cartRequestPojo.getOrderitems() == null) {
                emptyCart.set(true);
                return null;
            } else if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {
                if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {
                    emptyCart.set(true);
                    return null;
                } else {
                    emptyCart.set(false);
                }

            } else {
                emptyCart.set(false);
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

    public void applyCoupon() {

        getNavigator().applyCoupon();
    }

    public void clearCoupon() {
        getDataManager().setCouponId(0);
        fetchRepos();
    }

    public void proceedtopay() {

        if (available.get()) {


            if (getNavigator() != null)
                getNavigator().clearToolTips();


            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            if (!loading.get()) {

                if (getCartPojoDetails() != null) {

                    loading.set(true);
                    Gson sGson = new GsonBuilder().create();
                    CartRequest cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);

                    cartRequestPojo.setUserid(getDataManager().getCurrentUserId());
                    cartRequestPojo.setLat(getDataManager().getCurrentLat());
                    cartRequestPojo.setLon(getDataManager().getCurrentLng());
                    cartRequestPojo.setAid(getDataManager().getAddressId());
                    cartRequestPojo.setPayment_type(1);
                    cartRequestPojo.setCid(getDataManager().getCouponId());

                    Gson gson = new Gson();
                    String carts = gson.toJson(cartRequestPojo);

                    try {
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_PROCEED_TO_PAY, new JSONObject(carts), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (response != null) {
                                    loading.set(false);
                                    Gson gson = new Gson();
                                    OrderCreateResponse orderCreateResponse = gson.fromJson(response.toString(), OrderCreateResponse.class);

                                    if (orderCreateResponse.getStatus()) {
                                        if (getNavigator() != null)
                                            getNavigator().orderGenerated(orderCreateResponse.getOrderid(), orderCreateResponse.getRazerCustomerid(), orderCreateResponse.getPrice());
                                    } else {
                                        Toast.makeText(DailylocallyApp.getInstance(), orderCreateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.set(false);
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
                        loading.set(false);
                        if (getNavigator() != null)
                            getNavigator().cartLoaded();
                        emptyCart.set(true);
                        j.printStackTrace();
                    } catch (NullPointerException n) {
                        loading.set(false);
                        if (getNavigator() != null)
                            getNavigator().cartLoaded();
                        emptyCart.set(true);
                        n.printStackTrace();
                    } catch (Exception ee) {
                        loading.set(false);
                        if (getNavigator() != null)
                            getNavigator().cartLoaded();
                        emptyCart.set(true);
                        ee.printStackTrace();
                    }

                } else {
                    loading.set(false);
                    emptyCart.set(true);
                }

            }

        } else {
            loading.set(false);
            getNavigator().showToast(statusMessage.get());
        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void fetchRepos() {

        String cc = getDataManager().getCartDetails();
        if (cc == null) {
            emptyCart.set(true);
            return;
        }

        List<CartRequest.Orderitem> results = new ArrayList<>();

        CartRequest cartRequestPojo = new CartRequest();

        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequest();
       /* if (cartRequestPojo.getOrderitems() != null) {
            results.clear();
           // results.addAll(cartRequestPojo.getOrderitems());
        }*/
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {

                    CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();

                    if (cartRequestPojo.getOrderitems().get(i).getDayorderdate() != null) {
                        //avilable date format
                        SimpleDateFormat availableDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date availableCompareDate = null;
                        try {
                            availableCompareDate = availableDateFormat.parse(availableDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                        Date date = null;
                        try {
                            date = availableDateFormat.parse(cartRequestPojo.getOrderitems().get(i).getDayorderdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        assert date != null;
                        assert availableCompareDate != null;
                        if (date.getTime() < availableCompareDate.getTime()) {
                            // if (date.after(availableCompareDate)) {
                            cartRequestPojoResult.setDayorderdate(availableDate);
                        } else {
                            cartRequestPojoResult.setDayorderdate(cartRequestPojo.getOrderitems().get(i).getDayorderdate());
                        }

                    } else {

                        cartRequestPojoResult.setDayorderdate(availableDate);
                    }

                    cartRequestPojoResult.setPid(cartRequestPojo.getOrderitems().get(i).getPid());
                    cartRequestPojoResult.setQuantity(cartRequestPojo.getOrderitems().get(i).getQuantity());

                    cartRequestPojoResult.setPrice(String.valueOf(cartRequestPojo.getOrderitems().get(i).getPrice()));
                    // results.set(i, cartRequestPojoResult);
                    results.add(cartRequestPojoResult);
                }
            }
        }
        cartRequestPojo.setOrderitems(results);

        cartRequestPojo.setCid(getDataManager().getCouponId());

        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        getDataManager().setCartDetails(json);


        if (getNavigator() != null)
            getNavigator().clearToolTips();


        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

        if (getCartPojoDetails() != null) {

            Gson ssGson = new GsonBuilder().create();
            CartRequest rCartRequest = ssGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);

            rCartRequest.setUserid(getDataManager().getCurrentUserId());
            rCartRequest.setLat(getDataManager().getCurrentLat());
            rCartRequest.setLon(getDataManager().getCurrentLng());

            Gson srgson = new Gson();
            String carts = srgson.toJson(rCartRequest);

            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CART_DETAILS, new JSONObject(carts), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            Gson gson = new Gson();
                            CartResponse cartPageResponse = gson.fromJson(response.toString(), CartResponse.class);

                            //  if (cartPageResponse.getStatus()) {

                            statusMessage.set(cartPageResponse.getMessage());
                            available.set(cartPageResponse.getStatus());

                            couponApplied.set(cartPageResponse.getResult().get(0).getAmountdetails().getCouponstatus());
                            couponName.set("Code: " + getDataManager().getCouponCode());

                            if (!cartPageResponse.getStatus()) {
                                Toast.makeText(DailylocallyApp.getInstance(), cartPageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            if (cartPageResponse.getResult() != null) {


                                showFreeDelivery.set(cartPageResponse.getResult().get(0).getAmountdetails().getShowDeliverText());
                                delivery_charge_text.set(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryText());
                                delivery_charge.set(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge());

                                communityUser.set(cartPageResponse.getResult().get(0).getCommunityUser());
                                exclusiveTag.set(cartPageResponse.getResult().get(0).getAmountdetails().getExclusiveTag());




                                if (cartPageResponse.getStatus()) {
                                    bookDeliveryText.set("Place order");
                                    showWarningNote.set(false);
                                } else {
                                    if (!cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitStatus()) {
                                        showWarningNote.set(false);
                                        bookDeliveryText.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitShortMessage());
                                    } else if (cartPageResponse.getResult().get(0).getIsAvaliablezone()) {
                                        bookDeliveryText.set("Unserviceable location");
                                        showWarningNote.set(true);
                                    } else {
                                        showWarningNote.set(true);
                                        bookDeliveryText.set("Place order");
                                    }
                                }

                                if (cartPageResponse.getResult().get(0).getItem().size() == 0 && cartPageResponse.getResult().get(0).getSubscriptionItem().size() == 0) {

                                    if (getNavigator() != null) {
                                        getNavigator().emptyCart();
                                        getNavigator().clearToolTips();
                                    }
                                    //   getDataManager().setCartDetails(null);

                                    emptyCart.set(true);


                                }


                                //   if (cartPageResponse.getResult().get(0).getItem()!=null){

                                ordernowItemViewModels.clear();
                                subscribeItemViewModels.clear();

                                ordernowLiveData.setValue(cartPageResponse.getResult().get(0).getItem());
                                subscribeLiveData.setValue(cartPageResponse.getResult().get(0).getSubscriptionItem());

                                //  }


                                if (cartPageResponse.getResult().get(0).getItem().size() > 0) {


                                    //  ordernowLiveData.setValue(cartPageResponse.getResult().get(0).getItem());

                                    emptyCart.set(false);

                                    cartBillLiveData.setValue(cartPageResponse.getResult().get(0).getCartdetails());


                                    totalAmount = cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal();


                                    total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getProductOrginalPrice()));

                                    grand_total.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(totalAmount));

                                    grandTotalTitle.set(cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotaltitle());


                                    gst.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                                //    delivery_charge.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));


                                    if (cartPageResponse.getResult().get(0).getAmountdetails() != null) {
                                        lowCostStatus.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitStatus());
                                        lowCost.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitMessage());
                                        lowCostShort.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitShortMessage());
                                    }


                                }
                                if (cartPageResponse.getResult().get(0).getSubscriptionItem() != null) {

                                    if (cartPageResponse.getResult().get(0).getSubscriptionItem().size() > 0) {

                                        showSubscription.set(true);

                                        //  subscribeLiveData.setValue(cartPageResponse.getResult().get(0).getSubscriptionItem());
                                        emptyCart.set(false);

                                        cartBillLiveData.setValue(cartPageResponse.getResult().get(0).getCartdetails());


                                        totalAmount = cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal();


                                        total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getProductOrginalPrice()));

                                        grand_total.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(totalAmount));

                                        grandTotalTitle.set(cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotaltitle());


                                        gst.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                                      //  delivery_charge.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));


                                        if (cartPageResponse.getResult().get(0).getAmountdetails() != null) {
                                            lowCostStatus.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitStatus());
                                            lowCost.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitMessage());
                                            lowCostShort.set(cartPageResponse.getResult().get(0).getAmountdetails().getProductCostLimitShortMessage());
                                        }

                                    } else showSubscription.set(false);
                                } else {
                                    showSubscription.set(false);
                                }

                                setAddressTitle();

                                serviceable.set(cartPageResponse.getResult().get(0).getIsAvaliablezone());


                            }
                        }


                        if (getNavigator() != null)
                            getNavigator().cartLoaded();

                       /* if (getNavigator() != null) {
                            getNavigator().metricsCartOpen();
                        }*/
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

    public void productDateChange(String date, CartResponse.Item product) {


        List<CartRequest.Orderitem> results = new ArrayList<>();
        CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();
        CartRequest cartRequestPojo = new CartRequest();


        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequest();
        if (cartRequestPojo.getOrderitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getOrderitems());
        }

        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (product.getPid().equals(results.get(i).getPid())) {

                        cartRequestPojoResult.setPid(product.getPid());
                        cartRequestPojoResult.setQuantity(product.getCartquantity());
                        cartRequestPojoResult.setPrice(String.valueOf(product.getAmount()));
                        cartRequestPojoResult.setDayorderdate(date);
                        results.set(i, cartRequestPojoResult);


                    }
                }

            }

        }

        cartRequestPojo.setOrderitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        getDataManager().setCartDetails(json);

        fetchRepos();

    }

    public CartRequest getCart() {
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(appPreferencesHelper.getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequest();
        if (cartRequestPojo.getSubscription() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getSubscription());
        }
        return cartRequestPojo;
    }

    public void deleteSubsItem(CartResponse.SubscriptionItem products) {

        getCart();
        if (cartRequestPojo.getSubscription() != null) {
            int totalSize = cartRequestPojo.getSubscription().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {
                        results.remove(i);
                        break;
                    }
                }

            }

        }

        cartRequestPojo.setSubscription(results);
        saveCart(cartRequestPojo);

        if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {

            if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {

                getDataManager().setCartDetails(null);
            }

        } else {
            getDataManager().setCartDetails(null);
        }


        String cc = getDataManager().getCartDetails();
        if (cc == null) {
            emptyCart.set(true);

            if (getNavigator() != null)
                getNavigator().cartLoaded();

            return;
        } else {
            fetchRepos();

        }
    }

    public void saveCart(CartRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setCartDetails(json);
    }
}
