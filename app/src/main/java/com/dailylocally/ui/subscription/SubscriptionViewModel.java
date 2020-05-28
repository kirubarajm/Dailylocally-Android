package com.dailylocally.ui.subscription;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.DataManager;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.category.l2.products.ProductsResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubscriptionViewModel extends BaseViewModel<SubscriptionNavigator> {
    public final ObservableBoolean supportNumber = new ObservableBoolean();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();

    public final ObservableBoolean monClicked = new ObservableBoolean();
    public final ObservableBoolean tueClicked = new ObservableBoolean();
    public final ObservableBoolean wedClicked = new ObservableBoolean();
    public final ObservableBoolean thuClicked = new ObservableBoolean();
    public final ObservableBoolean friClicked = new ObservableBoolean();
    public final ObservableBoolean satClicked = new ObservableBoolean();
    public final ObservableBoolean sunClicked = new ObservableBoolean();

    public final ObservableBoolean dailyClicked = new ObservableBoolean();
    public final ObservableBoolean weekendClicked = new ObservableBoolean();
    public final ObservableBoolean weekClicked = new ObservableBoolean();

    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();
    private final List<CartRequest.Subscription> results = new ArrayList<>();
    private final CartRequest.Subscription cartRequestPojoResult = new CartRequest.Subscription();
    public ObservableBoolean contact = new ObservableBoolean();
    public ObservableField<String> support = new ObservableField<>();
    ProductsResponse.Result products;
    int quantity = 0;
    private CartRequest cartRequestPojo = new CartRequest();

    public SubscriptionViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void clickDaily() {

        if (dailyClicked.get()) {
            monClicked.set(false);
            tueClicked.set(false);
            wedClicked.set(false);
            thuClicked.set(false);
            friClicked.set(false);
            satClicked.set(false);
            sunClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        } else {
            monClicked.set(true);
            tueClicked.set(true);
            wedClicked.set(true);
            thuClicked.set(true);
            friClicked.set(true);
            satClicked.set(true);
            sunClicked.set(true);
            dailyClicked.set(true);
            weekClicked.set(false);
            weekendClicked.set(false);
        }

    }

    public void clickWeekDays() {

        if (weekClicked.get()) {
            monClicked.set(false);
            tueClicked.set(false);
            wedClicked.set(false);
            thuClicked.set(false);
            friClicked.set(false);
            satClicked.set(false);
            sunClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        } else {
            monClicked.set(true);
            tueClicked.set(true);
            wedClicked.set(true);
            thuClicked.set(true);
            friClicked.set(true);

            satClicked.set(false);
            sunClicked.set(false);

            dailyClicked.set(false);
            weekClicked.set(true);
            weekendClicked.set(false);
        }

    }

    public void clickWeekEnds() {

        if (weekendClicked.get()) {
            monClicked.set(false);
            tueClicked.set(false);
            wedClicked.set(false);
            thuClicked.set(false);
            friClicked.set(false);
            satClicked.set(false);
            sunClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        } else {
            monClicked.set(false);
            tueClicked.set(false);
            wedClicked.set(false);
            thuClicked.set(false);
            friClicked.set(false);

            satClicked.set(true);
            sunClicked.set(true);

            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(true);
        }

    }

    public void clickMon() {

        if (monClicked.get()) {
            monClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            monClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }

    }

    public void clickTue() {

        if (tueClicked.get()) {
            tueClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            tueClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }

    }

    public void clickWed() {
        if (wedClicked.get()) {
            wedClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            wedClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }
    }

    public void clickThu() {
        if (thuClicked.get()) {
            thuClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            thuClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }
    }

    public void clickFri() {
        if (friClicked.get()) {
            friClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            friClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }
    }

    public void clickSat() {
        if (satClicked.get()) {
            satClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            satClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }
    }

    public void clickSun() {
        if (sunClicked.get()) {
            sunClicked.set(false);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);

        } else {
            sunClicked.set(true);
            dailyClicked.set(false);
            weekClicked.set(false);
            weekendClicked.set(false);
        }
    }

    public void goBack() {
        getNavigator().goBack();
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
            results.addAll(cartRequestPojo.getSubscription());
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

        String tomorrowDate = dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate = dateFormat.format(dat);


        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getPid().equals(results.get(i).getPid())) {

                        if (Integer.parseInt(currentTime) < 14) {
                            cartRequestPojoResult.setStartDate(tomorrowDate);
                        } else {
                            cartRequestPojoResult.setStartDate(dayAftertomorrowDate);
                        }
                        cartRequestPojoResult.setPid(products.getPid());
                        cartRequestPojoResult.setQuantity(quantity);
                        cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
                        results.set(i, cartRequestPojoResult);
                    }
                }

            }

        }
        cartRequestPojo.setSubscription(results);
        saveCart(cartRequestPojo);
    }

    public void subClicked() {


        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate = dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate = dateFormat.format(dat);


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

                            if (Integer.parseInt(currentTime) < 14) {
                                cartRequestPojoResult.setStartDate(tomorrowDate);
                            } else {
                                cartRequestPojoResult.setStartDate(dayAftertomorrowDate);
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


        } else {
            cartRequestPojo.setSubscription(results);
            saveCart(cartRequestPojo);
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

        String tomorrowDate = dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate = dateFormat.format(dat);

        isAddClicked.set(true);
        quantity = 1;
        sQuantity.set(String.valueOf(quantity));

        getCart();

        if (Integer.parseInt(currentTime) < 14) {
            cartRequestPojoResult.setStartDate(tomorrowDate);
        } else {
            cartRequestPojoResult.setStartDate(dayAftertomorrowDate);
        }
        cartRequestPojoResult.setPid(products.getPid());
        cartRequestPojoResult.setQuantity(quantity);
        cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
        results.add(cartRequestPojoResult);

        cartRequestPojo.setSubscription(results);
        saveCart(cartRequestPojo);
    }
}
