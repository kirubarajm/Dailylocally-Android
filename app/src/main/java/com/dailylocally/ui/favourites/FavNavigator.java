package com.dailylocally.ui.favourites;

public interface FavNavigator {
    void handleError(Throwable throwable);
    void goBack();
    void viewCart();
    void createtabs(FavResponse response);
}
