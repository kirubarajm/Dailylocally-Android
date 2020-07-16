package com.dailylocally.ui.cart;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.R;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderNowItemViewModel {

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> futureDate = new ObservableField<>();
    public final ObservableField<String> veg_type = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> productDes = new ObservableField<>();


    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<String> availability = new ObservableField<>();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();
    public final ObservableBoolean isAvailable = new ObservableBoolean();
    public final ObservableBoolean isVeg = new ObservableBoolean();
    public final ObservableField<String> weight = new ObservableField<>();
    private final ObservableField<Integer> price = new ObservableField<>();
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
        // product_name.set("Abcdefghijklmnopqrstuvwxyz a b c d e f g h i j k l m n o p q r s t u v w x y z ");


        sprice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(dishList.getAmount()));
        image.set(dishList.getImage());
        weight.set(dishList.getWeight() + " " + dishList.getUnit());

        sQuantity.set(String.valueOf(dishList.getCartquantity()));
        quantity.set(dishList.getCartquantity());


        futureDate.set("Schedule for " + parseDateToddMMyyyy(dishList.getDeliverydate()));
        isAddClicked.set(true);

        if (dishList.getSubscription() != null) {
            if (dishList.getSubscription() == 1) {
                subscribeAvailable.set(true);
            } else {
                subscribeAvailable.set(false);
            }
        }
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
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
                        cartRequestPojoResult.setDayorderdate(parseDateToddMMyyyy(dishList.getDeliverydate()));
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
                            cartRequestPojoResult.setDayorderdate(parseDateToddMMyyyy(dishList.getDeliverydate()));
                            cartRequestPojoResult.setPrice(String.valueOf(dishList.getMrp()));
                            results.set(i, cartRequestPojoResult);

                        }
                    }
                }

            }

        }

        cartRequestPojo.setOrderitems(results);


        if (cartRequestPojo.getSubscription() == null && cartRequestPojo.getOrderitems() == null) {
            saveCart(null);
            mListener.reload();
        } else if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {
            if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {
                saveCart(null);
                mListener.reload();
            } else {
                saveCart(cartRequestPojo);
                mListener.reload();
            }

        } else {
            saveCart(cartRequestPojo);
            mListener.reload();
        }

           /* saveCart(cartRequestPojo);
            mListener.reload();*/


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
        cartRequestPojoResult.setDayorderdate(parseDateToddMMyyyy(dishList.getDeliverydate()));
        results.add(cartRequestPojoResult);

        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.reload();
    }


    public void subscribe() {
        mListener.subscribe(dishList);

    }

    public void delete() {

        quantity.set(0);
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
                        }
                    }
                }

            }

        }

        cartRequestPojo.setOrderitems(results);

        if (cartRequestPojo.getSubscription() == null && cartRequestPojo.getOrderitems() == null) {
            saveCart(null);
            mListener.reload();
        } else if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {
            if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {
                saveCart(null);
                mListener.reload();
            } else {
                saveCart(cartRequestPojo);
                mListener.reload();
            }

        } else {
            saveCart(cartRequestPojo);
            mListener.reload();
        }

           /* saveCart(cartRequestPojo);
            mListener.reload();*/


        if (quantity.get() == 0) {
            isAddClicked.set(false);
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


    public void changeDate() {

        String date = mListener.changeDate(dishList);


    }

    public void itemClick() {

        //   String date = mListener.changeDate(dishList);

        mListener.itemClick(dishList);


    }

    public interface DishItemViewModelListener {
        void reload();

        void itemClick(CartResponse.Item product);

        String changeDate(CartResponse.Item product);

        void subscribe(CartResponse.Item product);
    }

}
