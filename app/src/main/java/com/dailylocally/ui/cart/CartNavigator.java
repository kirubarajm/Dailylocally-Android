package com.dailylocally.ui.cart;

public interface CartNavigator {
    void handleError(Throwable throwable);

    void cartLoaded();

    void changeAddress();

    void showToast(String msg);

    void emptyCart();

    void redirectHome();

    void notServicable();

    void clearToolTips();

    void metricsCartOpen();
}
