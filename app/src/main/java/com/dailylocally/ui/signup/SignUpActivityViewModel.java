package com.dailylocally.ui.signup;


import android.util.Log;

import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.AppSignatureHashHelper;
import com.dailylocally.utilities.DailylocallyApp;


public class SignUpActivityViewModel extends BaseViewModel<SignUpActivityNavigator> {

    public final ObservableField<String> otp = new ObservableField<>();
    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    long OtpId;
    String userId;
    Response.ErrorListener errorListener;

    public SignUpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void usersLoginMain() {
        getNavigator().verifyUser();
    }


    public void faqs() {

        getNavigator().faqs();

    }


    public void privacy() {

        getNavigator().privacy();


    }

    public void termsandconditions() {

        getNavigator().termsandconditions();


    }


    public void users(String phoneNumber) {


        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(DailylocallyApp.getInstance());

        // This code requires one time to get Hash keys do comment and share key
        Log.e("OTP", "Apps Hash Key: " + appSignatureHashHelper.getAppSignatures().get(0));

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SIGN_UP, SignUpResponse.class, new SignUpRequest(phoneNumber, appSignatureHashHelper.getAppSignatures().get(0)), new Response.Listener<SignUpResponse>() {
                @Override
                public void onResponse(SignUpResponse response) {
                    if (response != null) {
                        if (response.getStatus()) {
                            passwordstatus = response.getPasswordstatus();
                            otpStatus = response.getOtpstatus();
                            genderstatus = response.getGenderstatus();

                            if (response.getOid() != null) {
                                OtpId = response.getOid();

                                if (getNavigator() != null)
                                    getNavigator().otpScreenFalse(OtpId);
                            }
                        }
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator() != null)
                    getNavigator().loginError(false);
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);


            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {
            ee.printStackTrace();

        }

    }

}
