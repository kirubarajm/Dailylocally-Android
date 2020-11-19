package com.dailylocally.ui.main;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.category.l2.L2CategoryRequest;
import com.dailylocally.ui.community.CommunityUserDetailsResponse;
import com.dailylocally.ui.signup.registration.TokenRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;
    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableBoolean titleVisible = new ObservableBoolean();
    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
    public final ObservableBoolean isHome = new ObservableBoolean();
    public final ObservableBoolean isCommunity = new ObservableBoolean();
    public final ObservableBoolean isCommunityUser = new ObservableBoolean();
    public final ObservableBoolean isCommunityCat = new ObservableBoolean();
    public final ObservableBoolean isOrder = new ObservableBoolean();
    public final ObservableBoolean isExplore = new ObservableBoolean();
    public final ObservableBoolean isCart = new ObservableBoolean();
    public final ObservableBoolean isMyAccount = new ObservableBoolean();
    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();
    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<String> numOfCarts = new ObservableField<>();
    private long orderId;
    private long payment_orderId;
    private int payment_price;
    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
        screenName.set(AppConstants.SCREEN_HOME);


       /* Identity identity = new AnonymousIdentity.Builder()
                .withNameIdentifier(getDataManager().getCurrentUserName())
                .withEmailIdentifier(getDataManager().getCurrentUserEmail())
                .build();
        Zendesk.INSTANCE.setIdentity(identity);*/

    }

    public int getAction() {
        return action;
    }

    public void gotoCart(String screenName) {
        if (!isCart.get()) {
            getNavigator().openCart(screenName);
            isHome.set(false);
            isExplore.set(false);
            isCart.set(true);
            isMyAccount.set(false);
            isOrder.set(false);
            isCommunity.set(false);
            isCommunityCat.set(false);
        }
    }


    public boolean isAddressAdded() {

        if (getDataManager().getCurrentLat() != null && !getDataManager().getCurrentLat().equals("0.0")) {
            return true;
        } else {
            return false;
        }

//        return getDataManager().getCurrentLat() != null || !getDataManager().getCurrentLat().equals("0.0")|| !getDataManager().getCurrentLat().equals(0.0);
    }

    public void saveToken(String token) {
        String userIdMain = getDataManager().getCurrentUserId();
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_FCM_TOKEN, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (response.isStatus()) {


                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void gotoAccount() {

        if (!isMyAccount.get()) {
            getNavigator().openAccount();
            isHome.set(false);
            isExplore.set(false);
            isCart.set(false);
            isOrder.set(false);
            isMyAccount.set(true);
            isCommunity.set(false);
            isCommunityCat.set(false);
        }


    }

    public void gotoExplore() {

        if (!isExplore.get()) {

            getNavigator().openExplore(true);
            isHome.set(false);
            isExplore.set(true);
            isCart.set(false);
            isOrder.set(false);
            isMyAccount.set(false);
            isCommunity.set(false);
            isCommunityCat.set(false);
        }
    }

    public void gotoHome() {
        if (!isHome.get()) {
            getNavigator().openHome();
            isHome.set(true);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);
            isOrder.set(false);
            isCommunity.set(false);
            isCommunityCat.set(false);
        }
    }


    public void gotoCommunity() {
        if (!isCommunity.get()) {
            getNavigator().openCommunity();
            isCommunity.set(true);
            isHome.set(false);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);
            isOrder.set(false);
            isCommunityCat.set(false);
        }
    }


    public void gotoOrders() {
        if (!isOrder.get()) {
            getNavigator().openOrders();
            isHome.set(false);
            isOrder.set(true);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);
            isCommunity.set(false);
            isCommunityCat.set(false);
        }
    }

    public void gotoCommunityCat() {
        if (!isCommunityCat.get()) {
            getNavigator().openCommunityCat();
            isHome.set(false);
            isOrder.set(false);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);
            isCommunity.set(false);
            isCommunityCat.set(true);
        }
    }


    public boolean totalCart() {


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
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())) * CartRequest.getSubscription().get(i).getQuantity());
                }

            }
        }


        if (count <= 0) {
            cart.set(false);
            return false;
        } else {
            cart.set(true);
            numOfCarts.set(String.valueOf(count));
            return true;
        }


       /* try {
            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);
            cart.set(false);
            if (cartRequestPojo == null)
                cartRequestPojo = new CartRequestPojo();
            int count = 0;

            if (cartRequestPojo.getCartitems() != null) {
                if (cartRequestPojo.getCartitems().size() == 0) {
                    cart.set(false);
                } else {

                    for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {

                        count = count + cartRequestPojo.getCartitems().get(i).getQuantity();

                        if (count == 0) {
                            cart.set(false);
                            return false;

                        } else {
                            numOfCarts.set(String.valueOf(count));
                            cart.set(true);
                        }

                    }

                }
            }
        } catch (Exception ee) {

            ee.printStackTrace();

        }*/
        // return false;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }


    public ObservableField<String> getNumOfCarts() {
        return numOfCarts;
    }


    public boolean checkInternet() {
        return DailylocallyApp.getInstance().onCheckNetWork();
    }


    public void currentLatLng(String lat, String lng) {
        getDataManager().setCurrentAddressTitle("Current location");
        getDataManager().setCurrentLat(lat);
        getDataManager().setCurrentLng(lng);
        // getNavigator().openHome();
    }


    public void paymentSuccess(String orderid, String paymentId, Integer status) {


        JsonObjectRequest jsonObjectRequest = null;
        try {

            JSONObject json = new JSONObject();
            json.put("transactionid", paymentId);
            json.put("payment_status", status);
            json.put("orderid", orderid);
            json.put("payment_gatway_type", AppConstants.RAZORPAY_TYPE);


            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_PAYMENT_CONFIRM, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response != null)
                            if (response.getBoolean("status")) {

                                if (getNavigator() != null)
                                    getNavigator().paymentSuccessed(true);
                                getDataManager().setCartDetails(null);

                            } else {
                                if (getNavigator() != null)
                                    getNavigator().paymentSuccessed(false);

                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

        DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void getUserDetails(String page) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_USER_DETAILS, CommunityUserDetailsResponse.class, new L2CategoryRequest(getDataManager().getCurrentUserId(), getDataManager().getCurrentLat(), getDataManager().getCurrentLng()), new Response.Listener<CommunityUserDetailsResponse>() {
                @Override
                public void onResponse(CommunityUserDetailsResponse response) {

                    Gson gson = new Gson();
                    String userDetails = gson.toJson(response);
                    getDataManager().setUserDetails(userDetails);

                    if (response != null)
                        if (response.getStatus()) {
                            if (response.getResult() != null) {

                                if (response.getResult().size() > 0) {

                                    CommunityUserDetailsResponse.Result result = response.getResult().get(0);
                                    getDataManager().updateProfilePic(result.getProfileImage());
                                    if (result.getJoinStatus() == 1) {
                                        isCommunityUser.set(true);
                                      /*  if (getNavigator() != null)
                                            getNavigator().openCommunity();*/
                                    } else {
                                        isCommunityUser.set(false);
                                       /* if (getNavigator() != null)
                                            getNavigator().openHome();*/
                                    }

                                } else {
                                   /* if (getNavigator() != null)
                                        getNavigator().openHome();*/
                                }

                            } else {
                              /*  if (getNavigator() != null)
                                    getNavigator().openHome();*/
                            }

                                                    /*getDataManager().updateCurrentAddress("", completeAddress, lat, lon,
                                city, String.valueOf(response.getAid()));
                        getDataManager().setCurrentLat(lat);
                        getDataManager().setCurrentLng(lon);
                        getDataManager().setCurrentAddress(completeAddress);
                        getDataManager().setCurrentAddressArea(city);
                        getDataManager().setCurrentAddressTitle(city);
                        getDataManager().setAddressId(response.getAid());

                        if (addressType.equals("1")){
                            String cAddress="No."+flatHouseNo+", "+blockName+", "+apartmentName+", "+completeAddress;

                            getDataManager().setCurrentAddress(cAddress);

                        }else {
                            String cAddress="No."+plotHouseNo+", Floor-"+floor+", "+completeAddress;
                            getDataManager().setCurrentAddress(cAddress);
                        }*/


                            String completeAddress = response.getuserdetails().get(0).getCompleteAddress();
                            String lat = String.valueOf(response.getuserdetails().get(0).getLat());
                            String lon = String.valueOf(response.getuserdetails().get(0).getLon());
                            String aid = String.valueOf(response.getuserdetails().get(0).getAid());
                            String city = String.valueOf(response.getuserdetails().get(0).getCity());
                            String addressType = String.valueOf(response.getuserdetails().get(0).getAddressType());
                            String flatHouseNo = String.valueOf(response.getuserdetails().get(0).getFlatHouseNo());
                            String blockName = String.valueOf(response.getuserdetails().get(0).getBlockName());
                            String apartmentName = String.valueOf(response.getuserdetails().get(0).getApartmentName());
                            String plotHouseNo = String.valueOf(response.getuserdetails().get(0).getPlotHouseNo());
                            String floor = String.valueOf(response.getuserdetails().get(0).getFloor());


                            getDataManager().updateCurrentAddress("", response.getuserdetails().get(0).getCompleteAddress(), lat, lon, city, aid);
                            getDataManager().setCurrentLat(lat);
                            getDataManager().setCurrentLng(lon);
                            getDataManager().setCurrentAddress(completeAddress);
                            getDataManager().setCurrentAddressArea(city);
                            getDataManager().setCurrentAddressTitle(city);
                            getDataManager().setAddressId(aid);

                            if (addressType.equals("1")){
                                String cAddress="No."+flatHouseNo+", "+blockName+", "+apartmentName+", "+completeAddress;
                                getDataManager().setCurrentAddress(cAddress);

                            }else {
                                String cAddress="No."+plotHouseNo+", Floor-"+floor+", "+completeAddress;
                                getDataManager().setCurrentAddress(cAddress);
                            }
                        } else {
                           /* if (getNavigator() != null)
                                getNavigator().openHome();*/
                        }


                    if (getNavigator() != null)
                        getNavigator().pageNavigation(page);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_TWO);


            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }


}
