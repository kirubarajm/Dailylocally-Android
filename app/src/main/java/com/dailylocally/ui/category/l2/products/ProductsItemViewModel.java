package com.dailylocally.ui.category.l2.products;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.cart.CartResponse;
import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class ProductsItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();
    private final ProductsResponse.Result products;
    private final ProductsItemViewModelListener mListener;
int quantity=0;
    private final List<CartRequest.Orderitem> results = new ArrayList<>();
    private final CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();
    private CartRequest cartRequestPojo = new CartRequest();


    public ProductsItemViewModel(ProductsItemViewModelListener mListener, ProductsResponse.Result result) {
        this.mListener=mListener;
        this.products = result;
        name.set(result.getProductname());
        weight.set(result.getWeight());
        price.set("INR " + result.getMrp());
        image.set(result.getImage());
  //      serviceable.set(result.getServicableStatus());
        serviceable.set(true);
        subscribeAvailable.set(true);

        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {
                        isAddClicked.set(true);
                        quantity=results.get(i).getQuantity();
                       sQuantity.set(String.valueOf(results.get(i).getQuantity()));

                    }
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
        // if (coupon.isClickable())
        //     mListener.onItemClick(result);

    }

    public void addClicked() {

        quantity++;
        sQuantity.set(String.valueOf(quantity));

        results.clear();

        getCart();

        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);




        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {

                            if (Integer.parseInt(currentTime)<14){
                                cartRequestPojoResult.setDayorderdate(tomorrowDate);
                            }else {
                                cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
                            }

                        cartRequestPojoResult.setPid(products.getPid());
                        cartRequestPojoResult.setQuantity(quantity);
                        cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
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


        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);


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

                            if (Integer.parseInt(currentTime)<14){
                                cartRequestPojoResult.setDayorderdate(tomorrowDate);
                            }else {
                                cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
                            }


                            cartRequestPojoResult.setPid(products.getPid());
                            cartRequestPojoResult.setQuantity(quantity);
                            cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
                            results.set(i, cartRequestPojoResult);

                        }
                    }
                }

            }

        }


        if (results.size() == 0) {
            saveCart(null);
            mListener.refresh();

        } else {
            cartRequestPojo.setOrderitems(results);
            saveCart(cartRequestPojo);
            mListener.refresh();
        }

        if (quantity == 0) {
            isAddClicked.set(false);
        }

    }

    public void enableAdd() {
        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);




        isAddClicked.set(true);
        quantity=1;
        sQuantity.set(String.valueOf(quantity));

        getCart();


        if (Integer.parseInt(currentTime)<14){
            cartRequestPojoResult.setDayorderdate(tomorrowDate);
        }else {
            cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
        }


        cartRequestPojoResult.setPid(products.getPid());
        cartRequestPojoResult.setQuantity(quantity);
        cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
        results.add(cartRequestPojoResult);

        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);
        mListener.refresh();

    }

    public void subscribe() {

    }

    public interface ProductsItemViewModelListener {
        void refresh();
    }

}
