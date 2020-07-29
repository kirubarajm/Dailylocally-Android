/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dailylocally.ui.productDetail;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsViewModel extends BaseViewModel<ProductDetailsNavigator> {


    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> offerCost = new ObservableField<>();
    public final ObservableField<String> unit = new ObservableField<>();
    public final ObservableField<String> short_desc = new ObservableField<>();
    public final ObservableField<String> productname = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> mrp = new ObservableField<>();
    public final ObservableField<String> pktSize = new ObservableField<>();

    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableBoolean discount_cost_status = new ObservableBoolean();


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> subscribeText = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean subscribeAvailable = new ObservableBoolean();
    public final ObservableBoolean subscribed = new ObservableBoolean();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    private final List<CartRequest.Orderitem> results = new ArrayList<>();
    private final CartRequest.Orderitem cartRequestPojoResult = new CartRequest.Orderitem();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> noProductsString = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cartItemAvailble = new ObservableBoolean();
    public String vpid = "0";
    int quantity = 0;
    private ProductDetailsResponse.Result products;
    private CartRequest cartRequestPojo = new CartRequest();


    public ProductDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        totalCart();
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
                    if (products.getVpid().equals(results.get(i).getPid())) {
                        /*if (Integer.parseInt(currentTime)<14){
                            cartRequestPojoResult.setDayorderdate(tomorrowDate);
                        }else {
                            cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
                        }*/
                        cartRequestPojoResult.setPid(products.getVpid());
                        cartRequestPojoResult.setQuantity(quantity);
                        if (products.getDiscountCostStatus()) {
                            cartRequestPojoResult.setPrice(String.valueOf(products.getMrpDiscountAmount()));
                        } else {
                            cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
                        }
                        results.set(i, cartRequestPojoResult);
                    }
                }

            }

        }
        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);

    }

    public void subClicked() {


        /*String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);*/


        quantity--;

        sQuantity.set(String.valueOf(quantity));
        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (products.getVpid().equals(results.get(i).getPid())) {
                        if (quantity == 0) {
                            results.remove(i);
                            break;
                        } else {

                            /*if (Integer.parseInt(currentTime)<14){
                                cartRequestPojoResult.setDayorderdate(tomorrowDate);
                            }else {
                                cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
                            }*/


                            cartRequestPojoResult.setPid(products.getVpid());
                            cartRequestPojoResult.setQuantity(quantity);
                            if (products.getDiscountCostStatus()) {
                                cartRequestPojoResult.setPrice(String.valueOf(products.getMrpDiscountAmount()));
                            } else {
                                cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
                            }
                            results.set(i, cartRequestPojoResult);

                        }
                    }
                }

            }

        }
        cartRequestPojo.setOrderitems(results);
        if (cartRequestPojo.getSubscription() == null && cartRequestPojo.getOrderitems() == null) {
            saveCart(null);

        } else if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getOrderitems() != null) {
            if (cartRequestPojo.getSubscription().size() == 0 && cartRequestPojo.getOrderitems().size() == 0) {
                saveCart(null);

            } else {
                saveCart(cartRequestPojo);

            }

        } else {
            saveCart(cartRequestPojo);

        }

        if (quantity == 0) {
            isAddClicked.set(false);
        }

    }

    public void enableAdd() {
       /* String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);*/


        isAddClicked.set(true);
        quantity = 1;
        sQuantity.set(String.valueOf(quantity));

        getCart();


       /* if (Integer.parseInt(currentTime)<14){
            cartRequestPojoResult.setDayorderdate(tomorrowDate);
        }else {
            cartRequestPojoResult.setDayorderdate(dayAftertomorrowDate);
        }*/


        cartRequestPojoResult.setPid(products.getVpid());
        cartRequestPojoResult.setQuantity(quantity);


        if (products.getDiscountCostStatus()) {
            cartRequestPojoResult.setPrice(String.valueOf(products.getMrpDiscountAmount()));
        } else {
            cartRequestPojoResult.setPrice(String.valueOf(products.getMrp()));
        }


        results.add(cartRequestPojoResult);

        cartRequestPojo.setOrderitems(results);
        saveCart(cartRequestPojo);


    }

    public void saveCart(CartRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        getDataManager().setCartDetails(json);
        totalCart();
    }

    public CartRequest getCart() {
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequest();
        if (cartRequestPojo.getOrderitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getOrderitems());
        }
        return cartRequestPojo;
    }

    public void viewCart() {
        getNavigator().viewCart();
    }

    /*public void totalCart() {
        Gson sGson = new GsonBuilder().create();
        CartRequest CartRequest = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        cartItemAvailble.set(false);
        if (CartRequest == null)
            CartRequest = new CartRequest();
        int count = 0;
        int price = 0;
        if (CartRequest.getOrderitems() != null) {
            if (CartRequest.getOrderitems().size() == 0) {
                cartItemAvailble.set(false);
            } else {

                count = count + CartRequest.getOrderitems().size();

                for (int i = 0; i < CartRequest.getOrderitems().size(); i++) {
                    //  count = count + CartRequest.getOrderitems().get(i).getQuantity();
                    price = price + ((Integer.parseInt(CartRequest.getOrderitems().get(i).getPrice())) * CartRequest.getOrderitems().get(i).getQuantity());
                }

            }
        }


        if (CartRequest.getSubscription() != null) {
            if (CartRequest.getSubscription().size() == 0) {
                cartItemAvailble.set(false);
            } else {
                count = count + CartRequest.getSubscription().size();

                for (int i = 0; i < CartRequest.getSubscription().size(); i++) {
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())));
                }

            }
        }

        if (count <= 0) {
            cartItemAvailble.set(false);
        } else {
            cartItemAvailble.set(true);

            if (count == 1) {
                cartItems.set(count + " Item");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(price));

                items.set("Item");
            } else {
                cartItems.set(count + " Items");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(price));

                items.set("Items");
            }
        }

    }*/
    public void totalCart() {
        Gson sGson = new GsonBuilder().create();
        CartRequest CartRequest = sGson.fromJson(getDataManager().getCartDetails(), CartRequest.class);
        cartItemAvailble.set(false);
        if (CartRequest == null)
            CartRequest = new CartRequest();
        int count = 0;
        int price = 0;
        if (CartRequest.getOrderitems() != null) {
            if (CartRequest.getOrderitems().size() == 0) {
                cartItemAvailble.set(false);
            } else {

                count = count + CartRequest.getOrderitems().size();

                for (int i = 0; i < CartRequest.getOrderitems().size(); i++) {
                    //  count = count + CartRequest.getOrderitems().get(i).getQuantity();
                    price = price + ((Integer.parseInt(CartRequest.getOrderitems().get(i).getPrice())) * CartRequest.getOrderitems().get(i).getQuantity());
                }

            }
        }


        if (CartRequest.getSubscription() != null) {
            if (CartRequest.getSubscription().size() == 0) {
                cartItemAvailble.set(false);
            } else {
                count = count + CartRequest.getSubscription().size();

                for (int i = 0; i < CartRequest.getSubscription().size(); i++) {
                    price = price + ((Integer.parseInt(CartRequest.getSubscription().get(i).getPrice())) );
                }

            }
        }

        if (count <= 0) {
            cartItemAvailble.set(false);
        } else {
            cartItemAvailble.set(true);

            if (count == 1) {
                cartItems.set(count + " Item");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+" " +String.valueOf(price));
                items.set("Item");
            } else {
                cartItems.set(count + " Items");
                cartPrice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol)+" " +String.valueOf(price));
                items.set("Items");
            }
        }

    }

    public void getProductDetails(String vpid) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PRODUCT_DETAILS, ProductDetailsResponse.class,
                new ProductRequest(userId, getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), vpid),
                new Response.Listener<ProductDetailsResponse>() {
                    @Override
                    public void onResponse(ProductDetailsResponse response) {
                        try {
                            if (response != null) {

                                serviceable.set(response.getServiceablestatus());
                                unserviceableTitle.set(response.getUnserviceableTitle());
                                unserviceableSubTitle.set(response.getUnserviceableSubtitle());

                                if (response.getStatus() && response.getResult().size() > 0) {

                                    products = response.getResult().get(0);

                                    if (getNavigator() != null) {
                                        getNavigator().productsDetailsSuccess(response.getResult().get(0));
                                    }
                                    imageUrl.set(response.getResult().get(0).getImage());
                                    offer.set(response.getResult().get(0).getOffer());
                                    unit.set(response.getResult().get(0).getWeight() + " " + response.getResult().get(0).getUnit());
                                    short_desc.set(response.getResult().get(0).getShortDesc());
                                    productname.set(response.getResult().get(0).getProductname());
                                    discount_cost_status.set(response.getResult().get(0).getDiscountCostStatus());

                                    if (response.getResult().get(0).getDiscountCostStatus()) {
                                        mrp.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + response.getResult().get(0).getMrpDiscountAmount());
                                    } else {
                                        mrp.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + response.getResult().get(0).getMrp());
                                    }

                                    //mrp.set(String.valueOf(response.getResult().get(0).getMrp()));
                                    //offerCost.set("\u20B9"+response.getResult().get(0).getMrp() + " OFF on " + response.getResult().get(0).getProductname());


                                    serviceable.set(response.getResult().get(0).getServicableStatus());
                                    if (response.getResult().get(0).getSubscription() != null)
                                        if (response.getResult().get(0).getSubscription().equals("1")) {
                                            subscribeAvailable.set(true);
                                        } else {
                                            subscribeAvailable.set(false);
                                        }


                                    if (!response.getResult().get(0).getServicableStatus())
                                        subscribeAvailable.set(false);

                                    subscribeText.set("Subscribe");

                                 //   checkCart();


                                }
                            }
                            setIsLoading(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            setIsLoading(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }


    public void checkCart() {
        results.clear();
        getCart();
        if (cartRequestPojo.getOrderitems() != null) {
            int totalSize = cartRequestPojo.getOrderitems().size();
            if (totalSize != 0) {
                for (int i = 0; i < totalSize; i++) {
                    if (vpid.equals(results.get(i).getPid())) {
                        isAddClicked.set(true);
                        quantity = results.get(i).getQuantity();
                        sQuantity.set(String.valueOf(results.get(i).getQuantity()));
                    }
                }
            }
        }

        if (cartRequestPojo.getSubscription() != null && cartRequestPojo.getSubscription().size() > 0) {
            for (int i = 0; i < cartRequestPojo.getSubscription().size(); i++) {
                if (vpid.equals(cartRequestPojo.getSubscription().get(i).getPid())) {
                    subscribeText.set("Edit subscription");
                    subscribed.set(true);
                    pktSize.set(cartRequestPojo.getSubscription().get(i).getPktSize());

                }
            }
        }

    }


    public void goBack() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }

    public void subscribe() {
        getNavigator().subscribe();
    }

}
