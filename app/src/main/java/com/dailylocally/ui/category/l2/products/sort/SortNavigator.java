package com.dailylocally.ui.category.l2.products.sort;

public interface SortNavigator {

    void handleError(Throwable throwable);

    void clearFilters();

    void applyFilter();

    void close();
}
