package com.dailylocally.ui.subscription;


import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionViewModel extends BaseViewModel<SubscriptionNavigator> {
    public final ObservableBoolean supportNumber = new ObservableBoolean();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> startDate = new ObservableField<>();
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
    public int planId = 0;
    public boolean edit = false;
    SubscriptionResponse mSubscriptionResponse;
    SubscriptionResponse.Result products;
    int quantity = 0;
    private CartRequest cartRequestPojo = new CartRequest();


    public SubscriptionViewModel(DataManager dataManager) {
        super(dataManager);

        serviceable.set(true);
    }

    public void selectDate() {

        getStartDate(AppConstants.SUBSCRIBEPRODUCT_CHANGE);
    }

    public void delete() {

        if (edit) {

            results.clear();
            getCart();
            if (cartRequestPojo.getSubscription() != null) {
                int totalSize = cartRequestPojo.getSubscription().size();
                if (totalSize != 0) {
                    for (int i = 0; i < totalSize; i++) {
                        if (products.getPid().equals(results.get(i).getPid())) {
                            results.remove(i);
                            break;
                        }
                    }

                }

            }


            cartRequestPojo.setSubscription(results);
            saveCart(cartRequestPojo);
            getNavigator().goBack();

        } else {

            getNavigator().goBack();
        }


    }


    public void createSubscription() {
        if (quantity == 0) {
            Toast.makeText(DailylocallyApp.getInstance(), "Please add quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (planId == 0) {
            Toast.makeText(DailylocallyApp.getInstance(), "Please choose deliveries ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (monClicked.get() || tueClicked.get() || wedClicked.get() || thuClicked.get() || friClicked.get() || satClicked.get() || sunClicked.get()) {

        } else {
            Toast.makeText(DailylocallyApp.getInstance(), "Please select day", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SUBS_TOTAL_AMOUNT, SubscriptionTotalAmountResponse.class, new SubscriptionTotalAmountRequest(products.getPid(), getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId(), quantity, planId), new Response.Listener<SubscriptionTotalAmountResponse>() {

            @Override
            public void onResponse(SubscriptionTotalAmountResponse response) {
                if (response != null) {

                    if (response.getResult() != null && response.getResult().size() > 0)

                        if (edit) {
                            results.clear();
                            getCart();
                            if (cartRequestPojo.getSubscription() != null) {
                                int totalSize = cartRequestPojo.getSubscription().size();
                                if (totalSize != 0) {
                                    for (int i = 0; i < totalSize; i++) {
                                        if (products.getPid().equals(results.get(i).getPid())) {
                                            if (quantity == 0) {
                                                results.remove(i);
                                                break;
                                            } else {

                                                cartRequestPojoResult.setStartDate(startDate.get());
                                                cartRequestPojoResult.setPid(products.getPid());
                                                cartRequestPojoResult.setQuantity(quantity);
                                                cartRequestPojoResult.setPlanid(planId);
                                                cartRequestPojoResult.setPrice(response.getResult().get(0).getAmount());


                                                if (monClicked.get()) {
                                                    cartRequestPojoResult.setMon(1);
                                                } else {
                                                    cartRequestPojoResult.setMon(0);
                                                }


                                                if (tueClicked.get()) {
                                                    cartRequestPojoResult.setTue(1);
                                                } else {
                                                    cartRequestPojoResult.setTue(0);
                                                }


                                                if (wedClicked.get()) {
                                                    cartRequestPojoResult.setWed(1);
                                                } else {
                                                    cartRequestPojoResult.setWed(0);
                                                }


                                                if (thuClicked.get()) {
                                                    cartRequestPojoResult.setThur(1);
                                                } else {
                                                    cartRequestPojoResult.setThur(0);
                                                }

                                                if (friClicked.get()) {
                                                    cartRequestPojoResult.setFri(1);
                                                } else {
                                                    cartRequestPojoResult.setFri(0);
                                                }

                                                if (satClicked.get()) {
                                                    cartRequestPojoResult.setSat(1);
                                                } else {
                                                    cartRequestPojoResult.setSat(0);
                                                }

                                                if (sunClicked.get()) {
                                                    cartRequestPojoResult.setSun(1);
                                                } else {
                                                    cartRequestPojoResult.setSun(0);
                                                }


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


                            getNavigator().goBack();

                        } else {

                            getCart();

                            cartRequestPojoResult.setStartDate(startDate.get());
                            cartRequestPojoResult.setPid(products.getPid());
                            cartRequestPojoResult.setQuantity(quantity);
                            cartRequestPojoResult.setPlanid(planId);
                            cartRequestPojoResult.setPrice(response.getResult().get(0).getAmount());

                            if (monClicked.get()) {
                                cartRequestPojoResult.setMon(1);
                            } else {
                                cartRequestPojoResult.setMon(0);
                            }


                            if (tueClicked.get()) {
                                cartRequestPojoResult.setTue(1);
                            } else {
                                cartRequestPojoResult.setTue(0);
                            }


                            if (wedClicked.get()) {
                                cartRequestPojoResult.setWed(1);
                            } else {
                                cartRequestPojoResult.setWed(0);
                            }


                            if (thuClicked.get()) {
                                cartRequestPojoResult.setThur(1);
                            } else {
                                cartRequestPojoResult.setThur(0);
                            }

                            if (friClicked.get()) {
                                cartRequestPojoResult.setFri(1);
                            } else {
                                cartRequestPojoResult.setFri(0);
                            }

                            if (satClicked.get()) {
                                cartRequestPojoResult.setSat(1);
                            } else {
                                cartRequestPojoResult.setSat(0);
                            }

                            if (sunClicked.get()) {
                                cartRequestPojoResult.setSun(1);
                            } else {
                                cartRequestPojoResult.setSun(0);
                            }


                            results.add(cartRequestPojoResult);
                            cartRequestPojo.setSubscription(results);
                            saveCart(cartRequestPojo);


                            getNavigator().goBack();
                        }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


    }

    public void getStartDate(String editOrNew) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_GET_START_DATE, StartDateResponse.class, new SubscriptionRequest(getDataManager().getCurrentUserId()), new Response.Listener<StartDateResponse>() {

            @Override
            public void onResponse(StartDateResponse response) {
                if (response != null) {

                    if (editOrNew.equals(AppConstants.SUBSCRIBEPRODUCT_EDIT)) {
                       /* if (getNavigator() != null)
                            getNavigator().selectDate(response.getOrderDeliveryDay());*/


                    } else if (editOrNew.equals(AppConstants.SUBSCRIBEPRODUCT_CHECK)) {

                        startDate.set(response.getOrderDeliveryDay());
                    } else if (editOrNew.equals(AppConstants.SUBSCRIBEPRODUCT_CHANGE)) {

                        if (getNavigator() != null)
                            getNavigator().selectDate(response.getOrderDeliveryDay());
                    } else {
                        startDate.set(response.getOrderDeliveryDay());
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


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
        if (cartRequestPojo.getSubscription() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getSubscription());
        }
        return cartRequestPojo;
    }

    public void addClicked() {

        quantity++;
        sQuantity.set(String.valueOf(quantity));


    }

    public void subClicked() {

        quantity--;
        sQuantity.set(String.valueOf(quantity));

        if (quantity == 0)
            isAddClicked.set(false);


    }

    public void enableAdd() {

        isAddClicked.set(true);
        quantity = 1;
        sQuantity.set(String.valueOf(quantity));

    }

    public void fetchProductDetails(String pid) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SUBS_DETAILS, SubscriptionResponse.class, new SubscriptionRequest(pid, getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId()), new Response.Listener<SubscriptionResponse>() {

            @Override
            public void onResponse(SubscriptionResponse response) {


                if (response != null) {


                    if (response.getResult() != null && response.getResult().size() > 0) {

                        products = response.getResult().get(0);

                        if (response.getSubscriptionPlan() != null && response.getSubscriptionPlan().size() > 0) {
                            mSubscriptionResponse = response;
                            if (getNavigator() != null)
                                getNavigator().plans(response);

                        }

                        results.clear();
                        getCart();
                        if (cartRequestPojo.getSubscription() != null) {
                            int totalSize = cartRequestPojo.getSubscription().size();
                            if (totalSize != 0) {
                                for (int i = 0; i < totalSize; i++) {
                                    if (products.getPid().equals(results.get(i).getPid())) {
                                        edit = true;
                                        isAddClicked.set(true);
                                        quantity = results.get(i).getQuantity();
                                        sQuantity.set(String.valueOf(results.get(i).getQuantity()));
                                        planId = results.get(i).getPlanid();
                                        startDate.set(results.get(i).getStartDate());

                                        if (getNavigator() != null)
                                            getNavigator().selectedplan(results.get(i).getPlanid(),response);

                                        if (results.get(i).getMon() == 1) {
                                            monClicked.set(true);
                                        } else {
                                            monClicked.set(false);
                                        }

                                        if (results.get(i).getTue() == 1) {
                                            tueClicked.set(true);
                                        } else {
                                            tueClicked.set(false);
                                        }

                                        if (results.get(i).getWed() == 1) {
                                            wedClicked.set(true);
                                        } else {
                                            wedClicked.set(false);
                                        }

                                        if (results.get(i).getThur() == 1) {
                                            thuClicked.set(true);
                                        } else {
                                            thuClicked.set(false);
                                        }


                                        if (results.get(i).getFri() == 1) {
                                            friClicked.set(true);
                                        } else {
                                            friClicked.set(false);
                                        }


                                        if (results.get(i).getSat() == 1) {
                                            satClicked.set(true);
                                        } else {
                                            satClicked.set(false);
                                        }


                                        if (results.get(i).getSun() == 1) {
                                            sunClicked.set(true);
                                        } else {
                                            sunClicked.set(false);
                                        }


                                    }
                                }

                            }

                        }

                        if (edit) {
                            getStartDate(AppConstants.SUBSCRIBEPRODUCT_EDIT);
                        } else {
                            getStartDate(AppConstants.SUBSCRIBEPRODUCT_NEW);
                        }


                        name.set(response.getResult().get(0).getProductname());
                        image.set(response.getResult().get(0).getImage());
                        weight.set(response.getResult().get(0).getWeight());
                        price.set("INR. " + response.getResult().get(0).getMrp());



                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);


    }
}
