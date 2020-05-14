package com.dailylocally.ui.update;



import android.util.Log;

import androidx.databinding.ObservableBoolean;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

public class UpdateViewModel extends BaseViewModel<UpdateNavigator> {


    public ObservableBoolean forceUpdate = new ObservableBoolean();


    public UpdateViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void checkIsUserLoggedInOrNot(){

        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_NOT_NOW);

        if (getDataManager().getCurrentUserId()!=null)
        {
            String userId = getDataManager().getCurrentUserId();
            Log.e("userId", String.valueOf(userId));

            if (getDataManager().isUserRegistered()) {
                getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
            }else {
                getNavigator().checkForUserGenderStatus(false);
            }
        }else {
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
        }

    }

    public void update(){

        getNavigator().update();


    }

}
