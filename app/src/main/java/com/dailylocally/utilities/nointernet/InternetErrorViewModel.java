package com.dailylocally.utilities.nointernet;


import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.DailylocallyApp;

import dagger.Module;

@Module
public class InternetErrorViewModel extends BaseViewModel<InternetErrorNavigator> {


    public InternetErrorViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void retry() {
        getNavigator().retry();

    }


    public boolean checkInternet() {

        return DailylocallyApp.getInstance().onCheckNetWork();


    }


}
