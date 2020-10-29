package com.dailylocally.ui.account;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.community.CommunityUserDetailsResponse;
import com.dailylocally.ui.signup.registration.GetUserDetailsResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;

@Module
public class MyAccountViewModel extends BaseViewModel<MyAccountNavigator> {

    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userPhoneNo = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> regionname = new ObservableField<>();
    public final ObservableBoolean showCommunity = new ObservableBoolean();
    private GetUserDetailsResponse getUserDetailsResponse;


    public MyAccountViewModel(DataManager dataManager) {
        super(dataManager);
        if (getDataManager().getUserDetails() != null) {

            Gson sGson = new GsonBuilder().create();
            CommunityUserDetailsResponse communityUserDetailsResponse = sGson.fromJson(getDataManager().getUserDetails(), CommunityUserDetailsResponse.class);
            if (communityUserDetailsResponse != null) {
                if (communityUserDetailsResponse.getResult() != null) {
                    if (communityUserDetailsResponse.getResult().size() > 0) {
                        CommunityUserDetailsResponse.Result result = communityUserDetailsResponse.getResult().get(0);

                     showCommunity.set(result.getCommunityStatus());

                    }
                }

            }

        }

    }

    public void loadUserDetails() {
        userName.set(getDataManager().getCurrentUserName());
        userEmail.set(getDataManager().getCurrentUserEmail());
        userPhoneNo.set(getDataManager().getCurrentUserPhNo());
    }

    public void changeAddress() {
        getNavigator().changeAddress();
    }

 public void transactions() {
        getNavigator().transactions();
    }


    public void orderHistory() {
        getNavigator().orderHistory();
    }

    public void couponsAndOffers() {
        getNavigator().couponsAndOffers();
    }


    public void gotoCommunity() {
        getNavigator().gotoCommunity();
    }
    public void favourites() {
        getNavigator().favourites();
    }

    public void referrals() {
        getNavigator().referrals();
    }


    public void offers() {
        getNavigator().offers();
    }

    public void favorites() {
        getNavigator().offers();
    }

    public void logOut() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.LOGOUT, LogoutResponse.class, new LogoutRequest(getDataManager().getCurrentUserId()),
                    new Response.Listener<LogoutResponse>() {
                @Override
                public void onResponse(LogoutResponse response) {
                    if (response.getStatus()) {
                        logOutSession();
                        if (getNavigator() != null)
                            getNavigator().logout();
                    }else {
                        if (getNavigator() != null)
                            getNavigator().logoutFailure("Failed");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
                        if (getNavigator() != null)
                            getNavigator().logoutFailure("Failed");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void feedbackAndSupport() {
        getNavigator().feedbackAndSupport();
    }

    public void editProfile() {
        if (getNavigator()!=null)
            getNavigator().editProfile();
    }

    public void logOutSession() {
        getDataManager().setLogout();
        getDataManager().setCartDetails(null);
        getDataManager().setCurrentAddress(null);
        getDataManager().setCurrentAddressArea(null);
        getDataManager().setCurrentAddressTitle(null);
        getDataManager().showFunnel(false);
        getDataManager().saveMaster(null);
        getDataManager().saveRazorpayCustomerId(null);
        getDataManager().saveCouponId(0);
        getDataManager().saveRatingSkipDate(null, 0);
        getDataManager().saveRatingSkipDate(0);
        getDataManager().saveRatingAppStatus(false);
        getDataManager().saveIsFilterApplied(false);
        getDataManager().saveApiToken(null);
        getDataManager().saveCouponCode(null);
        getDataManager().saveSupportNumber(null);
        getDataManager().orderInstruction(null);
        getDataManager().homeAddressadded(false);
        getDataManager().officeAddressadded(false);
        getDataManager().appStartedAgain(false);
        getDataManager().saveFirstLocation(null, null, null);
        getDataManager().setHomeAddressAdded(false);
        getDataManager().setOfficeAddressAdded(false);

    }


}
