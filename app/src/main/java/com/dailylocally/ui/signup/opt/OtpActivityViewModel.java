package com.dailylocally.ui.signup.opt;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.signup.SignUpRequest;
import com.dailylocally.ui.signup.SignUpResponse;
import com.dailylocally.ui.signup.registration.TokenRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;

public class OtpActivityViewModel extends BaseViewModel<OtpActivityNavigator> {

    public final ObservableBoolean otp = new ObservableBoolean();
    public final ObservableField<String> userId = new ObservableField<>();
    public final ObservableField<String> oId = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> number = new ObservableField<>();
    public final ObservableField<String> timer = new ObservableField<>();

    public long OtpId = 0;

    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    Response.ErrorListener errorListener;

    public OtpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void continueClick() {
        if (getNavigator() != null)
            getNavigator().continueClick();
    }

    public void forgotPasswordClick() {
        getNavigator().forgotPassword();
    }




/*
    @BindingAdapter("touchListener")
    public void setTouchListener(View self, boolean value){
        self.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Check if the button is PRESSED
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    //do some thing
                }// Check if the button is RELEASED
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //do some thing
                }
                return false;
            }
        });
    }*/


    public void loginClick() {
        getNavigator().login();
    }

    public void userContinueClick(final String phoneNumber, int otp) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.OTP_VERIFICATION, OtpResponse.class, new OtpRequest(phoneNumber, otp, OtpId), new Response.Listener<OtpResponse>() {
                @Override
                public void onResponse(OtpResponse response) {

                    try {
                        String CurrentuserId = null;

                        if (response != null) {

                            if (response.getStatus() != null)
                                if (response.getStatus()) {


                                    String addressCreated = response.getAddressCreated();
                                    genderstatus = response.getGenderstatus();

                                    getDataManager().setUserRegistrationStatus(genderstatus);

                                    new Analytics().userLogin(response.getUserid(), phoneNumber);


                                    getDataManager().saveApiToken(response.getToken());

                                    getDataManager().setRazorpayCustomerId(response.getRazerCustomerid());


                                    getDataManager().setCurrentUserId(response.getUserid());


                                  /*  FreshchatUser freshUser= Freshchat.getInstance(MvvmApp.getInstance()).getUser();
                                    freshUser.setPhone("+91", String.valueOf(phoneNumber));
                                    Freshchat.getInstance(MvvmApp.getInstance()).setUser(freshUser);*/


                                    userId.set(String.valueOf(response.getUserid()));
                                    CurrentuserId = response.getUserid();

                                    if (response.getResult() != null && response.getResult().size() > 0) {


                                        if (response.getResult().get(0).getAid() != null) {

                                            getDataManager().setCurrentAddressTitle(response.getResult().get(0).getCity());
                                            getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                            getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                            getDataManager().setAddressId(response.getResult().get(0).getAid());

                                            getDataManager().setCurrentAddressArea(response.getResult().get(0).getLocality());


                                            if (response.getResult().get(0).getAddressType() == 1) {
                                                String completeAddress = "No." + response.getResult().get(0).getFlatHouseNo() + ", " + response.getResult().get(0).getBlockName() + ", " + response.getResult().get(0).getApartmentName() + "," + response.getResult().get(0).getCompleteAddress();

                                                getDataManager().setCurrentAddress(completeAddress);

                                            } else {
                                                String completeAddress = "No." + response.getResult().get(0).getPlotHouseNo() + ", Floor-" + response.getResult().get(0).getFloor() + ", " + response.getResult().get(0).getCompleteAddress();
                                                getDataManager().setCurrentAddress(completeAddress);
                                            }


                                        }

                                        if (response.getRegistrationstatus()) {

                                            String cuserid = response.getResult().get(0).getUserid();
                                            String UserName = response.getResult().get(0).getName();
                                            String UserEmail = response.getResult().get(0).getEmail();
                                            String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                            String userReferralCode = response.getResult().get(0).getReferalcode();
                                            Integer gender = response.getResult().get(0).getGender();
                                            getDataManager().updateUserInformation(cuserid, UserName, UserEmail, userPhoneNumber, userReferralCode);
                                            getDataManager().setGender(gender);
                                        }

                                    }


                                    if (genderstatus) {


                                        if (addressCreated != null && addressCreated.equals("0")) {

                                            if (getNavigator() != null)
                                                getNavigator().addNewAddress();

                                        } else {
                                            if (response.getResult() != null && response.getResult().size() > 0) {
                                                if (response.getResult().get(0).getAid() != null) {
                                                    if (response.getResult().get(0).getAid().equals("0")) {
                                                        getDataManager().setUserAddress(false);
                                                        if (getNavigator() != null) {
                                                            getNavigator().addAddressActivity(response.getResult().get(0).getAid());
                                                        }
                                                    } else {
                                                        getDataManager().setUserAddress(true);
                                                        if (getNavigator() != null) {
                                                            getNavigator().openHomeActivity(true);
                                                        }
                                                    }

                                                } else {
                                                    getDataManager().setUserAddress(false);
                                                    if (getNavigator() != null) {
                                                        getNavigator().addAddressActivity(response.getResult().get(0).getAid());
                                                    }
                                                }
                                            } else {
                                                getDataManager().setUserAddress(false);
                                            }


                                        }

                                    } else {
                                        if (getNavigator() != null)
                                            getNavigator().nameGenderScreen();
                                    }
                                }


                        } else {
                            if (getNavigator() != null)
                                getNavigator().loginFailure();
                            getDataManager().setUserRegistrationStatus(genderstatus);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception ee) {

                        ee.printStackTrace();
                    }

                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().loginFailure();
                    getDataManager().setUserRegistrationStatus(genderstatus);
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void resendClick() {

        getNavigator().resend();

    }

    public void resendOtp() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.SEND_OTP, SignUpResponse.class, new SignUpRequest(number.get()), new Response.Listener<SignUpResponse>() {
                @Override
                public void onResponse(SignUpResponse response) {
                    if (response != null) {

                        OtpId = response.getOid();
                        setIsLoading(false);
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

        /*gsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 2000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void saveToken(String token) {
        String userIdMain = getDataManager().getCurrentUserId();
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {


            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_FCM_TOKEN, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (response.isStatus()) {


                        }
                    }
                }
            }, errorListener = new Response.ErrorListener() {
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
}
