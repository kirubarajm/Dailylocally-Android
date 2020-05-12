package com.dailylocally.ui.signup.privacy;


import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class PrivacyViewModel extends BaseViewModel<PrivacyNavigator> {
    public PrivacyViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void onAgreeAndAcceptClick(){
        getNavigator().openRegActivity();
    }


    public void goBack(){
        getNavigator().goBack();
    }

}
