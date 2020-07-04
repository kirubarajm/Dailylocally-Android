package com.dailylocally.ui.home;


public interface HomeNavigator {

    void handleError(Throwable throwable);


    void dataLoaded();
    void dataLoading();
    void gotoOrders();
    void changeAddress();

    void changeHeaderText(String headerContent);
    void showPromotions(String url,boolean fullScreen, int type,int promotionid);
    void searchClick();
}
