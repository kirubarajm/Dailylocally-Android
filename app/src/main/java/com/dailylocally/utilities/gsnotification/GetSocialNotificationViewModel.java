package com.dailylocally.utilities.gsnotification;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class GetSocialNotificationViewModel extends BaseViewModel<GetSocialNotificationNavigator> {

    public GetSocialNotificationViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void gotoOrders(){
        getNavigator().gotoOrders();

    }
    public void goHome(){
        getNavigator().goHome();
    }

}
