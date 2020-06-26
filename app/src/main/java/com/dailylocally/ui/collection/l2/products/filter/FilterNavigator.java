package com.dailylocally.ui.collection.l2.products.filter;

public interface FilterNavigator {

    void handleError(Throwable throwable);

    void clearFilters();

    void applyFilter();

    void close();
}
