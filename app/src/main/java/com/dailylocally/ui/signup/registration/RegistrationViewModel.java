package com.dailylocally.ui.signup.registration;


import androidx.databinding.ObservableBoolean;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {

    public ObservableBoolean flagEdit = new ObservableBoolean();
    public ObservableBoolean male = new ObservableBoolean();
    public ObservableBoolean haveReferral = new ObservableBoolean();
    public ObservableBoolean referral = new ObservableBoolean();
    public ObservableBoolean regionotherClicked = new ObservableBoolean();

    public ObservableBoolean flagRegion = new ObservableBoolean();
    Response.ErrorListener errorListener;
    int gender = 0;

    public RegistrationViewModel(DataManager dataManager) {
        super(dataManager);
        if (getDataManager().getGender()==null) {
            male.set(true);
        }else {
            Integer gender =getDataManager().getGender();
            if (gender==0){
                male.set(false);
            }else {
                male.set(true);
            }
        }
        regionotherClicked.set(false);

        /*if (getDataManager().getRegionId()==0){
            regionotherClicked.set(true);
        }*/
    }

    public void proceed() {
        if (getNavigator()!=null)
        getNavigator().proceedClick();
    }

    public void maleClicked() {
        male.set(true);
        new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_MALE);

    }

    public void feMaleClicked() {
        male.set(false);
        new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_FEMALE);
    }

    public void viewReferral() {
        if (referral.get()) {
            referral.set(false);
        } else {
            referral.set(true);
        }
    }

    public void referralCode() {
        haveReferral.set(true);
    }

    public void insertNameGenderServiceCall(String name, String email, String referral) {

        if (male.get()) {
            gender = 1;
        } else {
            gender = 2;
        }

        String userIdMain = getDataManager().getCurrentUserId();
        RegistrationRequest registrationRequest;

        if (referral.isEmpty()) {
            registrationRequest = new RegistrationRequest(userIdMain, name, email, gender);
        } else {
            registrationRequest = new RegistrationRequest(userIdMain, name, email, gender, referral);
        }

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.REGISTRATION, NameGenderResponse.class, registrationRequest, new Response.Listener<NameGenderResponse>() {
                @Override
                public void onResponse(NameGenderResponse response) {
                    try {
                    if (response != null) {
                        if (response.getStatus()) {
                                if (getNavigator() != null)
                                    getNavigator().genderSuccess(response.getMessage(),flagEdit.get());

                            getDataManager().setUserRegistrationStatus(true);

                            if (response.getResult() != null && response.getResult().size() > 0) {
                                String userId = response.getResult().get(0).getUserid();
                                String UserName = response.getResult().get(0).getName();
                                String UserEmail = response.getResult().get(0).getEmail();
                                String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                String userReferralCode = response.getResult().get(0).getReferalcode();
                                Integer gender = response.getResult().get(0).getGender();
                                getDataManager().updateUserInformation(userId, UserName, UserEmail, userPhoneNumber, userReferralCode);
                                getDataManager().setGender(gender);
                                //   getDataManager().setRegionId(response.getResult().get(0).getRegionid());
                            }
                        } else {
                            getDataManager().setUserRegistrationStatus(false);
                            getNavigator().genderFailure(response.getMessage());
                        }
                    } }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    getDataManager().setUserRegistrationStatus(false);
                    if (getNavigator() != null)
                        getNavigator().genderFailure("Failed to update");
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
