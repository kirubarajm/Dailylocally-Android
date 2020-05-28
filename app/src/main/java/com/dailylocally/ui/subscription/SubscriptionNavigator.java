package com.dailylocally.ui.subscription;

public interface SubscriptionNavigator {

    void handleError(Throwable throwable);

    void feedBackClick();

    void supportClick();

    void goBack();

    void callCusstomerCare();
}
