package com.dailylocally.ui.subscription;

public interface SubscriptionNavigator {

    void handleError(Throwable throwable);

    void goBack();
    void selectDate(String date);
    void plans(SubscriptionResponse subscriptionPlan);

}
