package com.dailylocally.ui.fandsupport.help;

public interface HelpNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void showToast(String msg);

    void createChat(String department, String tag, String note);
    void mapChat(String department, String tag, String note, int issueid, int tid);


}
