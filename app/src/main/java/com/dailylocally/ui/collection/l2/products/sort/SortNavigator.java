package com.dailylocally.ui.collection.l2.products.sort;

public interface SortNavigator {

    void handleError(Throwable throwable);

    void clearFilters();

    void applyFilter();

    void close();
}
