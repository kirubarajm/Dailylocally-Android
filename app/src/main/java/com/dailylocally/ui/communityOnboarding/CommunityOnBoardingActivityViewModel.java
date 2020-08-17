package com.dailylocally.ui.communityOnboarding;


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


public class CommunityOnBoardingActivityViewModel extends BaseViewModel<CommunityOnBoardingActivityNavigator> {

    public CommunityOnBoardingActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }



    public void checkUpdate() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(DailylocallyApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {


                //    Toast.makeText(MvvmApp.getInstance(),String.valueOf(MvvmApp.getInstance().getVersionCode()) , Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);


    }

}
