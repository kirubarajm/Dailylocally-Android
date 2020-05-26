package com.dailylocally.ui.home;


public interface HomeNavigator {

    void handleError(Throwable throwable);


    void dataLoaded();

    void changeHeaderText(String headerContent);
}
