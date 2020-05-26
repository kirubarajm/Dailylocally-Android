package com.dailylocally.ui.category.l2.products;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.ui.category.l1.L1CategoryResponse;

public class ProductsItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();

    private final ProductsResponse.Result result;


    public ProductsItemViewModel(ProductsResponse.Result result) {
        this.result = result;
        name.set(result.getProductname());
        weight.set(result.getWeight());
        price.set("INR " + result.getMrp());
        image.set(result.getImage());
  //      serviceable.set(result.getServicableStatus());
        serviceable.set(true);
        subscribeAvailable.set(true);
    }


    public void onItemClick() {
        // if (coupon.isClickable())
        //     mListener.onItemClick(result);

    }

    public void addClicked() {

    }

    public void subClicked() {

    }

    public void enableAdd() {
        isAddClicked.set(true);
    }

    public void subscribe() {
        isAddClicked.set(true);
    }

    public interface L1CategoriesItemViewModelListener {
        void onItemClick(L1CategoryResponse.Result result);
    }

}
