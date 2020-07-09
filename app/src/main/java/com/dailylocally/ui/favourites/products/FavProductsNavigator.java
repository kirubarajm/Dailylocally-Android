package com.dailylocally.ui.favourites.products;


public interface FavProductsNavigator {

    void handleError(Throwable throwable);

    void openFilter();

    void openSort();
    void DataLoaded(FavProductsResponse response);

}
