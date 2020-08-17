package com.dailylocally.ui.collection.l2.products;


public interface CollectionProductsNavigator {

    void handleError(Throwable throwable);

    void openFilter();

    void openSort();

    void DataLoaded();
}
