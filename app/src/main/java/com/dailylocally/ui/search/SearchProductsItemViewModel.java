package com.dailylocally.ui.search;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchProductsItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> subscribeText = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();
    private final SearchProductResponse.Result products;
    private final ProductsItemViewModelListener mListener;
    private final List<CartRequest.Orderitem> results = new ArrayList<>();
    private final CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();
    int quantity = 0;
    private CartRequest cartRequestPojo = new CartRequest();


    public SearchProductsItemViewModel(ProductsItemViewModelListener mListener, SearchProductResponse.Result result) {
        this.mListener = mListener;
        this.products = result;
        name.set(result.getProductname());

        subscribeText.set("Subscribe");

        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {
                        isAddClicked.set(true);
                        quantity = results.get(i).getQuantity();
                        sQuantity.set(String.valueOf(results.get(i).getQuantity()));
                    }
                }
            }
        }

        if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getSubscription().size() > 0) {
            for (int i = 0; i < cartRequestPojo.getSubscription().size(); i++) {
                if (products.getPid().equals(cartRequestPojo.getSubscription().get(i).getPid())) {
                    subscribeText.set("Edit subscription");
                    serviceable.set(false);
                }
            }
        }
    }

    public void saveCart(CartRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setCartDetails(json);
    }

    public CartRequest getCart() {
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(appPreferencesHelper.getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequest();
        if (cartRequestPojo.getOrderitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getOrderitems());
        }
        return cartRequestPojo;
    }

    public void onItemClick() {
        mListener.onProductItemClick(products);
    }

    public void addClicked() {

        quantity++;
        sQuantity.set(String.valueOf(quantity));

        results.clear();

        getCart();

        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {

                        cartRequestPojoResult.setPid(String.valueOf(products.getPid()));
                        cartRequestPojoResult.setQuantity(quantity);
                        results.set(i, cartRequestPojoResult);
                    }
                }

            }

        }
        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.refresh();

    }

    public void subClicked() {

        quantity--;

        sQuantity.set(String.valueOf(quantity));
        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {
                        if (quantity == 0) {
                            results.remove(i);
                            break;
                        } else {

                            cartRequestPojoResult.setPid(String.valueOf(products.getPid()));
                            cartRequestPojoResult.setQuantity(quantity);
                            results.set(i, cartRequestPojoResult);

                        }
                    }
                }

            }

        }
        cartRequestPojo.setOrderitems(results);
        if (cartRequestPojo.getSubscription() == null && cartRequestPojo.getOrderitems() == null) {
            saveCart(null);
            mListener.refresh();
        }else  if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {
            if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {
                saveCart(null);
                mListener.refresh();
            }else {
                saveCart(cartRequestPojo);
                mListener.refresh();
            }

        }else {
            saveCart(cartRequestPojo);
            mListener.refresh();
        }

        if (quantity == 0) {
            isAddClicked.set(false);
        }

    }

    public void enableAdd() {

        isAddClicked.set(true);
        quantity = 1;
        sQuantity.set(String.valueOf(quantity));

        getCart();

        cartRequestPojoResult.setPid(String.valueOf(products.getPid()));
        cartRequestPojoResult.setQuantity(quantity);
        results.add(cartRequestPojoResult);

        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.refresh();

    }

    public void subscribe() {
        mListener.subscribeProduct(products);
    }

    public void delete() {
        mListener.subscribeProduct(products);
    }

    public interface ProductsItemViewModelListener {
        void refresh();

        void subscribeProduct(SearchProductResponse.Result products);

        void onProductItemClick(SearchProductResponse.Result products);
    }

}
