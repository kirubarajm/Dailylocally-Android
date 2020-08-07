package com.dailylocally.ui.category.l2.products.filter;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class FilterItemViewModel {


    public final ObservableField<String> filterTitle = new ObservableField<>();

    public final ObservableBoolean isClicked = new ObservableBoolean();


    public final FilterItemViewModelListener mListener;
    private final FilterItems.Result filterItems;

    String id;

    public FilterItemViewModel(FilterItemViewModelListener mListener, FilterItems.Result filterItems) {


        this.mListener = mListener;
        this.filterItems = filterItems;

        id = filterItems.getBrand();

        filterTitle.set(filterItems.getBrandname());


        try {
            AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
            List<ProductsRequest.Brandlist> brandlists = new ArrayList<>();
            Gson sGson = new GsonBuilder().create();
            ProductsRequest productsRequest = sGson.fromJson(appPreferencesHelper.getFilterSort(), ProductsRequest.class);


            if (productsRequest != null) {
                if (productsRequest.getBrandlist() != null) {
                    if (productsRequest.getBrandlist().size() > 0) {
                        brandlists.addAll(productsRequest.getBrandlist());
                        for (int i = 0; i < productsRequest.getBrandlist().size(); i++) {
                            if (productsRequest.getBrandlist().get(i).getBrand().equals(filterItems.getBrand())) {
                                isClicked.set(true);
                            }
                        }
                    }
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


    public void onItemClick() {

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);


        List<ProductsRequest.Brandlist> brandlists = new ArrayList<>();
        Gson sGson = new GsonBuilder().create();
        ProductsRequest productsRequest = sGson.fromJson(appPreferencesHelper.getFilterSort(), ProductsRequest.class);


        if (productsRequest != null) {

            if (productsRequest.getBrandlist() != null) {

                if (productsRequest.getBrandlist().size() > 0) {
                    brandlists.addAll(productsRequest.getBrandlist());
                    for (int i = 0; i < productsRequest.getBrandlist().size(); i++) {
                        if (productsRequest.getBrandlist().get(i).getBrand().equals(filterItems.getBrand())) {
                            brandlists.remove(i);
                            productsRequest.setBrandlist(brandlists);
                            break;
                        } else {
                            brandlists.add(new ProductsRequest.Brandlist(filterItems.getBrand()));
                            productsRequest.setBrandlist(brandlists);
                            break;
                        }

                    }

                } else {


                    brandlists.add(new ProductsRequest.Brandlist(filterItems.getBrand()));
                    productsRequest.setBrandlist(brandlists);
                }

            } else {
                brandlists.add(new ProductsRequest.Brandlist(filterItems.getBrand()));
                productsRequest.setBrandlist(brandlists);
            }


        } else {
            productsRequest = new ProductsRequest();
            brandlists.add(new ProductsRequest.Brandlist(filterItems.getBrand()));
            productsRequest.setBrandlist(brandlists);
        }

        Gson gson = new Gson();
        String request = gson.toJson(productsRequest);

        appPreferencesHelper.setFilterSort(request);

        if (isClicked.get()) {
            isClicked.set(false);
        } else {
            isClicked.set(true);
        }
    }


    interface FilterItemViewModelListener {


        void addfilter(String id);

        void removeFilter(String id);


    }


}
