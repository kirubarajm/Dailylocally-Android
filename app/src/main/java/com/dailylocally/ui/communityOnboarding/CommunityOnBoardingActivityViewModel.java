package com.dailylocally.ui.communityOnboarding;


import androidx.databinding.ObservableBoolean;
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


public class CommunityOnBoardingActivityViewModel extends BaseViewModel<CommunityOnBoardingActivityNavigator> {

    public final ObservableBoolean lastScreen = new ObservableBoolean();
    public final ObservableField<String> btnText = new ObservableField<>();
    public CommunityOnBoardingActivityViewModel(DataManager dataManager) {
        super(dataManager);
        btnText.set("Next");
    }

    public void skip() {
        getNavigator().skip();
    }
    public void action() {
        getNavigator().action();
    }
    public void goBack() {
        getNavigator().goBack();
    }



}
