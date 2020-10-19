package com.dailylocally.ui.onboarding;


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


public class OnBoardingActivityViewModel extends BaseViewModel<OnBoardingActivityNavigator> {

    public OnBoardingActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void checkIsUserLoggedInOrNot(){
        /*if (getDataManager().getCurrentUserId()!=null)
        {
            int userId = getDataManager().getCurrentUserId();
            Log.e("userId", String.valueOf(userId));
            boolean genderStatus = getDataManager().getisGenderStatus();
            if (genderStatus) {
                getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
            }else {
                getNavigator().checkForUserGenderStatus(true);
            }
        }else {*/
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
      //  }

    }


    public void checkUpdate() {
        /*   MvvmApp.getInstance().getVersionCode()*/

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(DailylocallyApp.getInstance().getVersionCode(),getDataManager().getCurrentUserId()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {
                //  getNavigator().update(false, false);


                //    Toast.makeText(MvvmApp.getInstance(),String.valueOf(MvvmApp.getInstance().getVersionCode()) , Toast.LENGTH_SHORT).show();

                if (response != null)
                    if (response.getResult() != null && response.getStatus()) {
                        if (getNavigator() != null)
                            getNavigator().update(response.getResult().getVersionstatus(), response.getResult().getDluserforceupdatestatus());

                        getDataManager().saveSupportNumber(response.getResult().getSupportNumber());

                    } else {
                        if (getNavigator() != null)
                            getNavigator().update(false, false);
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getNavigator().update(false, false);
            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);


    }



}
