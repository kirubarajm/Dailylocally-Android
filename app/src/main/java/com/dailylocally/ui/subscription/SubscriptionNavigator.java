package com.dailylocally.ui.subscription;

public interface SubscriptionNavigator {

    void handleError(Throwable throwable);

    void goBack();
    void subscribed();
    void selectDate(String date);
    void plans(SubscriptionResponse subscriptionPlan);
    void selectedplan(int planid, SubscriptionResponse subscriptionPlan);

}
