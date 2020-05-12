package com.dailylocally.ui.signup.tandc;


import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class TermsAndConditionViewModel extends BaseViewModel<TermsAndConditionNavigator> {
    public TermsAndConditionViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void accept(){
        getNavigator().accept();
    }


    public void goBack(){
        getNavigator().goBack();
    }

}
