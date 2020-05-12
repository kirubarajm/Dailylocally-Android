package com.dailylocally.ui.signup.tandc;

public interface TermsAndConditionNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void  accept();
}
