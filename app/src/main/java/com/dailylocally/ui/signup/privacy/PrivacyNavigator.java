package com.dailylocally.ui.signup.privacy;

public interface PrivacyNavigator {

    void handleError(Throwable throwable);

    void openRegActivity();
    void goBack();
}
