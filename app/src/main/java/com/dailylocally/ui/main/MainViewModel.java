package com.dailylocally.ui.main;

import android.util.Log;

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
import com.dailylocally.ui.signup.registration.TokenRequest;
import com.dailylocally.ui.update.UpdateRequest;
import com.dailylocally.ui.update.UpdateResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CartRequestPojo;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.MasterPojo;

import com.dailylocally.utilities.analytics.Analytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;

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
    public final ObservableBoolean isExplore = new ObservableBoolean();
    public final ObservableBoolean isCart = new ObservableBoolean();
    public final ObservableBoolean isMyAccount = new ObservableBoolean();
    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<String> numOfCarts = new ObservableField<>();
    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();

    private long orderId;
    private long payment_orderId;
    private int payment_price;
    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
        screenName.set(AppConstants.SCREEN_HOME);


        Identity identity = new AnonymousIdentity.Builder()
                .withNameIdentifier(getDataManager().getCurrentUserName())
                .withEmailIdentifier(getDataManager().getCurrentUserEmail())
                .build();
        Zendesk.INSTANCE.setIdentity(identity);

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

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
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
            isMyAccount.set(true);
        }


    }

    public void gotoExplore() {

        if (!isExplore.get()) {

            getNavigator().openExplore();
            isHome.set(false);
            isExplore.set(true);
            isCart.set(false);
            isMyAccount.set(false);

        }
    }

    public void gotoHome() {
        if (!isHome.get()) {
            getNavigator().openHome();
            isHome.set(true);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);
        }
    }

    public boolean totalCart() {
        try {
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

        }
        return false;
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


    public void masterRequest() {
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER, MasterPojo.class, new Response.Listener<MasterPojo>() {
                @Override
                public void onResponse(MasterPojo response) {
                    if (response != null) {

                        Gson gson = new Gson();
                        String master = gson.toJson(response);
                        getDataManager().saveMaster("");
                        getDataManager().saveMaster(master);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }

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


}
