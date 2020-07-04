package com.dailylocally.ui.pronotion;


import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class AdViewModel extends BaseViewModel<AdNavigator> {
    public AdViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void onAgreeAndAcceptClick(){
        getNavigator().openRegActivity();
    }


    public void goBack(){
        getNavigator().goBack();
    }

}
