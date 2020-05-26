package com.dailylocally.ui.cart;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class OrderNowItemViewModel {

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> veg_type = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> productDes = new ObservableField<>();


    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<String> availability = new ObservableField<>();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean isAvailable = new ObservableBoolean();
    public final ObservableBoolean isVeg = new ObservableBoolean();
    private final ObservableField<Integer> price = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    private final ObservableField<Integer> quantity = new ObservableField<>();
    private final DishItemViewModelListener mListener;
    private final CartResponse.Item dishList;

    private final List<CartRequest.Orderitem> results = new ArrayList<>();
    private final CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();
    private CartRequest cartRequestPojo = new CartRequest();


    public OrderNowItemViewModel(DishItemViewModelListener mListener, CartResponse.Item dishList) {

        this.mListener = mListener;
        this.dishList = dishList;

        product_name.set(dishList.getProductname());
        sprice.set(String.valueOf(dishList.getMrp()));
        image.set(dishList.getImage());
        weight.set(dishList.getWeight());

        sQuantity.set(String.valueOf(dishList.getCartquantity()));
        quantity.set(dishList.getCartquantity());
        isAddClicked.set(true);
    }

    public void addClicked() throws NullPointerException {

        int checkQuantity = quantity.get();
        quantity.set(checkQuantity + 1);

        sQuantity.set(String.valueOf(quantity.get()));

        results.clear();

        getCart();

        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (dishList.getPid().equals(results.get(i).getPid())) {
                        cartRequestPojoResult.setPid(dishList.getPid());
                        cartRequestPojoResult.setQuantity(quantity.get());
                        cartRequestPojoResult.setPrice(String.valueOf(dishList.getMrp()));
                        results.set(i, cartRequestPojoResult);
                    }
                }

            }

        }
        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.reload();
    }


    public void subClicked() {

        quantity.set(quantity.get() - 1);
        sQuantity.set(String.valueOf(quantity.get()));
        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (dishList.getPid().equals(results.get(i).getPid())) {
                        if (quantity.get() == 0) {
                            results.remove(i);
                            break;
                        } else {
                            cartRequestPojoResult.setPid(dishList.getPid());
                            cartRequestPojoResult.setQuantity(quantity.get());
                            cartRequestPojoResult.setPrice(String.valueOf(dishList.getMrp()));
                            results.set(i, cartRequestPojoResult);

                        }
                    }
                }

            }

        }


        if (results.size() == 0) {
            saveCart(null);

        } else {
            cartRequestPojo.setOrderitems(results);
            saveCart(cartRequestPojo);
            mListener.reload();
        }

        if (quantity.get() == 0) {
            isAddClicked.set(false);
        }
    }

    public void enableAdd() {
        isAddClicked.set(true);
        quantity.set(1);
        sQuantity.set(String.valueOf(quantity.get()));

        getCart();

        cartRequestPojoResult.setPid(dishList.getPid());
        cartRequestPojoResult.setQuantity(quantity.get());
        cartRequestPojoResult.setPrice(String.valueOf(dishList.getMrp()));
        results.add(cartRequestPojoResult);

        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.reload();
    }


    public void subscribe() {


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

    public interface DishItemViewModelListener {
        void reload();
    }

}
