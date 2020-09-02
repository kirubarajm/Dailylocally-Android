package com.dailylocally.ui.community.catlist;


public interface CommunityCatNavigator {

    void handleError(Throwable throwable);


    void dataLoaded();
    void dataLoading();
    void gotoOrders();
    void changeAddress();

    void changeHeaderText(String headerContent);
    void showPromotions(String url, boolean fullScreen, int type, int promotionid);
    void searchClick();
    void ratingClick();
}
