package com.dailylocally.ui.community;


public interface CommunityNavigator {

    void handleError(Throwable throwable);

    void showPromotions(String url, boolean fullScreen, int type, int promotionid);

    void gotoCommunityHome();

    void whatsAppGroup();

    void sneakPeak();

    void aboutUs();

    void communityEvent();

    void stopVideo();

    void actionBtClick();

    void postLikeClick();

    void creditInfoClick();
    void changeProfile();
    void refreshProfile();
    void homeDataLoaded();
}

