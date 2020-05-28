package com.dailylocally.ui.subscription;

public interface SubscriptionNavigator {

    void handleError(Throwable throwable);

    void goBack();
    void plans(SubscriptionResponse subscriptionPlan);

}
