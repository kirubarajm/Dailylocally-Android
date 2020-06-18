package com.dailylocally.ui.category.l2.products.filter;

public interface FilterNavigator {

    void handleError(Throwable throwable);

    void clearFilters();

    void applyFilter();

}
