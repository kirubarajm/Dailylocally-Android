package com.dailylocally.ui.orderplaced;

public interface OrderPlacedNavigator {

    void handleError(Throwable throwable);

    void gotoOrders();

    void goHome();
}
