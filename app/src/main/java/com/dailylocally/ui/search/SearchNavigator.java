package com.dailylocally.ui.search;


public interface SearchNavigator {

    void handleError(Throwable throwable);

    void suggestionProductSuccess();

    void quickSearchSuccess();

    void searchNotFound();

}
