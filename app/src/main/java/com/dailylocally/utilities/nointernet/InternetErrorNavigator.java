package com.dailylocally.utilities.nointernet;

public interface InternetErrorNavigator {

    void handleError(Throwable throwable);

    void retry();


}
