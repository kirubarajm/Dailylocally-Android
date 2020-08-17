package com.dailylocally.ui.collection.l2;

public interface CollectionDetailsNavigator {
    void handleError(Throwable throwable);
    void goBack();
    void viewCart();
    void createtabs(CollectionDetailsResponse response);

    void dataLoaded();
}
