package com.dailylocally.ui.joinCommunity.contactWhatsapp;

public interface ContactWhatsAppNavigator {

    void handleError(Throwable throwable);

    void goBack();

    void onWhatsAppClick();

    void callClick();

    void changeHeader(String content);

}
