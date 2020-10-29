package com.dailylocally.ui.signup.opt;

public interface OtpActivityNavigator {

    void handleError(Throwable throwable);

    void continueClick();

    void openHomeActivity(boolean trueOrFalse);

    void addAddressActivity(String aId);

    void nameGenderScreen();

    void login();

    void forgotPassword();

    void loginSuccess();

    void loginFailure();

    void goBack();
    void resend();


    void addNewAddress();
}
