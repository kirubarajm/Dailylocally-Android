package com.dailylocally.ui.community.event;

public interface EventNavigator {

    void handleError(Throwable throwable);

    void back();
}
