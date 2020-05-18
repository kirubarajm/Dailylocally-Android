package com.dailylocally.ui.home;


public interface HomeNavigator {

    void handleError(Throwable throwable);


    void kitchenLoaded();

    void changeHeaderText(String headerContent);
}
