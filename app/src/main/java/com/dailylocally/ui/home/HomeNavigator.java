package com.dailylocally.ui.home;


public interface HomeNavigator {

    void handleError(Throwable throwable);


    void dataLoaded();
    void gotoOrders();

    void changeHeaderText(String headerContent);
}
