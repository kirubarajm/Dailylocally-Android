package com.dailylocally.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;


import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.update.UpdateRequest;
import com.dailylocally.ui.update.UpdateResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;


public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public final ObservableField<String> version = new ObservableField<>();

    public SplashViewModel(DataManager dataManager) {
        super(dataManager);
        getDataManager().appStartedAgain(true);
        getDataManager().savePromotionAppStartAgain(true);
        getDataManager().setUpdateAvailable(false);
    }

    public void clearLatLng(){

        if (getDataManager().getAddressId()==null) {
            getDataManager().setCurrentLat(null);
            getDataManager().setCurrentLng(null);
        }

    }

    public void checkIsUserLoggedInOrNot() {
        try {
            if (getDataManager().getCurrentUserId() != null) {

                if (getDataManager().isUserRegistered()) {
                    if (getDataManager().isUserAddress()) {
                        if (getNavigator() != null)
                            getNavigator().checkForUserLogin(AppConstants.FLAG_TRUE);
                    }else {
                            if (getNavigator()!=null){
                                getNavigator().userAddressActivity();
                            }
                    }
                } else {
                    if (getNavigator() != null)
                        getNavigator().userAlreadyRegistered(false);
                }
            } else {
                if (getNavigator() != null)
                    getNavigator().checkForUserLogin(AppConstants.FLAG_FALSE);
            }
        } catch (Exception e) {

            if (getNavigator() != null)
                getNavigator().checkForUserLogin(AppConstants.FLAG_FALSE);
        }
    }

    public void checkUpdate() {
        /*   MvvmApp.getInstance().getVersionCode()*/

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(DailylocallyApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {
              //  getNavigator().update(false, false);

            //    Toast.makeText(MvvmApp.getInstance(),String.valueOf(MvvmApp.getInstance().getVersionCode()) , Toast.LENGTH_SHORT).show();

                if (response != null)
                    if (response.getResult() != null && response.getStatus()) {
                        getDataManager().saveSupportNumber(response.getResult().getSupportNumber());
                        getDataManager().setUpdateAvailable(response.getResult().getVersionstatus());
                        if (getNavigator() != null)
                            getNavigator().update(response.getResult().getVersionstatus(), response.getResult().getDluserforceupdatestatus());

                    } else {
                        getDataManager().setUpdateAvailable(false);
                        if (getNavigator() != null)
                            getNavigator().update(false, false);
                    }
                setIsLoading(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getDataManager().setUpdateAvailable(false);
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
