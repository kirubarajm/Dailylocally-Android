package com.dailylocally.ui.community;


public interface CommunityNavigator {

    void handleError(Throwable throwable);

    void showPromotions(String url, boolean fullScreen, int type, int promotionid);
}

