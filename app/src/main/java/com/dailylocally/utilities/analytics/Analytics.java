package com.dailylocally.utilities.analytics;


import android.content.Context;
import android.os.Bundle;

import com.dailylocally.BuildConfig;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class Analytics {

    private static FirebaseAnalytics mFirebaseAnalytics;
    String userid = null,username = null,phoneNo = null;
    private String screen_name, screen_id, click;

    public Analytics() {

        if (BuildConfig.ENABLE_DEBUG) return;

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);


            userid = appPreferencesHelper.getCurrentUserId();
        username = appPreferencesHelper.getCurrentUserName();
        phoneNo = appPreferencesHelper.getCurrentUserPhNo();

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
    }

    public Analytics(Context context, String screen_name) {

       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        this.screen_name = screen_name;
*/
        sendViewData(screen_name);
    }

    public Analytics(String screen_name) {
        /*if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        this.screen_name = screen_name;

        sendViewData(screen_name);*/
    }

    public Analytics(int productid, String productname, int price, int quantity, String kitchenName) {
        //    if (BuildConfig.ENABLE_DEBUG) return;
     /*   if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, kitchenName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(productid));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productname);
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        bundle.putString(FirebaseAnalytics.Param.QUANTITY, quantity);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);*/

    }

    public Analytics(int price) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);*/


    }

    public Analytics(String screen_name, String click) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }


       *//* Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent(click, params);*//*

        this.screen_name = screen_name;

        this.click = click;
        sendClickData(screen_name, click);*/
    }

    public void AnalyticsAppOpens(Context context,String userid,String phno,String name) {




    }

    public void addProperties() {
    /*    mFirebaseAnalytics = FirebaseAnalytics.getInstance(DailylocallyApp.getInstance());
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));*/
    }

    public void sendViewData(String screen_name) {
        /*if (BuildConfig.ENABLE_DEBUG) return;
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent("event_screen_view", params);*/
    }

    public void sendClickData(String screen_name, String click) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null)
            addProperties();
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        params.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(click, params);*/
    }


    public void addtoCart(int productid, String productName, int quantiy, int price) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null)
            addProperties();

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ADD_TO_CART, bundle);*/


    }

    public void removeFromCart(int productid, String productName, int quantiy, int price) {
        /*if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REMOVE_FROM_CART, bundle);*/


    }


    public void userLogin(String user_id, String number) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, user_id);
        bundle.putString(AppConstants.ANALYTICYS_MOBILE_NUMBER, number);*/
        //     mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_USER_LOGIN, bundle);
    }


    public void paymentFailed(String order_id, int price) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        if (order_id != null) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, order_id);
            bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
            bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
            bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
            mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_FAILED, bundle);
        }*/
    }

    public void paymentSuccess(String order_id, int price) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        if (order_id != null) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, order_id);
            bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
            bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
            bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
            mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_SUCCESS, bundle);
        }*/
    }


    public void orderPlaced(String order_id, int price) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ORDER_PLACED, bundle);*/
    }

    public void createOrder(String order_id, int price) {
        /*if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_CREATE_ORDER, bundle);*/
    }

    public void search(String type, String name, String suggestion) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_TYPE, type);
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_NAME, name);
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_SUGGESTION, suggestion);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_SEARCH_CLICKED, bundle);*/
    }

    public void story(int id, String title) {
        /*if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_STORY_ID, id);
        bundle.putString(AppConstants.ANALYTICYS_STORY_TITLE, title);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_STORY_VIEW, bundle);*/
    }

    public void regionSelected(String title) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_REGION, title);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REGION_SELECTED, bundle);*/
    }


    public void appFeedback(int rating, String feedback) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_RATING, rating);
        bundle.putString(AppConstants.ANALYTICYS_feedback, feedback);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.SCREEN_APP_FEEDBCK, bundle);*/
    }


    public void orderRating(double prodRating, double delRating, String prodFeedback, String delFeedback) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putDouble(AppConstants.PRODUCT_RATING, prodRating);
        bundle.putDouble(AppConstants.PRODUCT_RATING, delRating);
        bundle.putString(AppConstants.PRODUCT_FEEDBACK, prodFeedback);
        bundle.putString(AppConstants.DELIVERY_FEEDBACK, delFeedback);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.METRICS_ORDER_RATING, bundle);*/
    }


    public void queriesChat(String query, String message) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_QUERIES, query);
        bundle.putString(AppConstants.ANALYTICYS_CHAT_MESSAGE, message);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.SCREEN_QUERY_CHAT, bundle);*/
    }

    public void makeQuery(String query) {
       /* if (BuildConfig.ENABLE_DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_QUERIES, query);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_MAKE_QUERIES, bundle);*/
    }

    public void repeatOrder(String orderid) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, orderid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REPEAT_ORDER, bundle);*/
    }


    public void selectKitchen(String type, String kitchenId) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_KITCHEN_ID, kitchenId);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(type, bundle);*/
    }


    public void kitchenViewcart(String type, String kitchenid) {
     /*   if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_KITCHEN_ID, kitchenid);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(type, bundle);*/
    }

    public void proceedToPay(int price) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_CHECKOUT, bundle);*/
    }

    /**
     * METRICS
     */
    /////APP OPENS
    public void appOpensMetrics(String previousPage, String addressType, int serviceablestatus) {
     /*   if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.APP_OPENS_PREVIOUS_PAGE, previousPage);
        bundle.putString(AppConstants.APP_OPENS_ADDRESS_TYPE, addressType);
        bundle.putInt(AppConstants.APP_OPENS_SERVICEABLESTATUS, serviceablestatus);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_APP_OPENS, bundle);*/
    }

    /////APP OPENS
    public void appHomeMetrics(String previousPage, String addressType, int categoryCount, int collectionCount, int serviceablestatus) {
      /*  if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.APP_HOME_PREVIOUS_PAGE, previousPage);
        bundle.putString(AppConstants.APP_HOME_ADDRESS_TYPE, addressType);
        bundle.putInt(AppConstants.APP_CATEGORY_COUNT, categoryCount);
        bundle.putInt(AppConstants.APP_HOME_SERVICEABLESTATUS, serviceablestatus);
        bundle.putInt(AppConstants.APP_COLLECRTION_COUNT, collectionCount);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_APP_HOME, bundle);*/
    }

    /////KITCHEN PAGE
    /*public void kitchenPageMetrics(String makeitId, String eta, double rating, List<KitchenDetailsResponse.Product> favOtherItemsTdysmenu *//*int productFavSectionCount, int productOtherCombosSectionCount,
                                   int productOtherItemsSectionCount,*//*, int nextAvailableProductCount, boolean serviceability, int homeMakerBadge,
                                   String favoriteByUser, String vegOnly, String nextPage) {
        try {
            if (BuildConfig.ENABLE_DEBUG) return;
            if (mFirebaseAnalytics == null) {
                addProperties();
            }
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
            bundle.putString(AppConstants.KITCHEN_PAGE_MAKEIT_ID, makeitId);
           // bundle.putString(AppConstants.KITCHEN_PAGE_ETA, eta);
         //   bundle.putDouble(AppConstants.KITCHEN_PAGE_RATING, rating);
            if (favOtherItemsTdysmenu != null && favOtherItemsTdysmenu.size() > 0) {
                for (int i = 0; i < favOtherItemsTdysmenu.size(); i++) {
                    bundle.putInt("cp" + favOtherItemsTdysmenu.get(i).getTitle().replace(" ", "_").toLowerCase(), favOtherItemsTdysmenu.get(i).getProductList().size());
                }
            }
          //  bundle.putInt(AppConstants.KITCHEN_PAGE_NEXT_AVAILABLE_PRODUCT_COUNT, nextAvailableProductCount);
            bundle.putBoolean(AppConstants.KITCHEN_PAGE_SERVICEABILITY, serviceability);
          //  bundle.putInt(AppConstants.KITCHEN_PAGE_HOME_MAKER_BADGE, homeMakerBadge);
          //  bundle.putString(AppConstants.KITCHEN_PAGE_FAVORITE_BY_USER, favoriteByUser);
           // bundle.putString(AppConstants.KITCHEN_PAGE_VEG_ONLY, vegOnly);

            mFirebaseAnalytics.logEvent(AppConstants.METRICS_KITCHEN_PAGE, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /////REGION PAGE
    public void regionPageMetrics(String previousPage, String regionId, String regionName, int serviceableCount, int unServiceableCount,/*String nextPage,*/String serviceableKitchensList
            , String unserviceableKitchensList) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.REGION_PREVIOUS_PAGE, previousPage);
        bundle.putString(AppConstants.REGION_PAGE_REGION_ID, regionId);
        bundle.putString(AppConstants.REGION_PAGE_REGION_NAME, regionName);
        bundle.putInt(AppConstants.REGION_PAGE_SERVICEABLE_COUNT, serviceableCount);
        bundle.putInt(AppConstants.REGION_PAGE_UNSERVICEABLE_COUNT, unServiceableCount);
        bundle.putString(AppConstants.REGION_PAGE_SERVICEABLE_KITCHEN_LIST, serviceableKitchensList);
        bundle.putString(AppConstants.REGION_PAGE_UNSERVICEABLE_KITCHEN_LIST, unserviceableKitchensList);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_REGION_PAGE, bundle);*/
    }

    /////SEARCH
    public void searchMetrics(String prevPage, String wordSearched, int regionSuggestionCount, int kitchenSuggestionCount, int dishSuggestionCount, int type,
                              int uniqueId, String nextPage, String regionSuggestionList, String kitchenSuggestionList, String dishSuggestionList) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.WORD_SEARCHED, wordSearched);
        bundle.putString(AppConstants.SEARCH_PREVOIUS_PAGE, prevPage);
       // bundle.putInt(AppConstants.REGION_SUGGESTION_COUNT, regionSuggestionCount);
        bundle.putInt(AppConstants.KITCHEN_SUGGESTION_COUNT, kitchenSuggestionCount);
        bundle.putInt(AppConstants.DISH_SUGGESTION_COUNT, dishSuggestionCount);
        bundle.putInt(AppConstants.SEARCH_CLICK_TYPE, type);
        bundle.putInt(AppConstants.UNIQUE_ID, uniqueId);
    //    bundle.putString(AppConstants.REGION_SUGGESTION_LIST, regionSuggestionList);
        bundle.putString(AppConstants.KITCHEN_SUGGESTION_LIST, kitchenSuggestionList);
        bundle.putString(AppConstants.DISH_SUGGESTION_LIST, dishSuggestionList);
   ///     bundle.putString(AppConstants.SEARCH_CLICK_TYPE, dishSuggestionList);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_SEARCH, bundle);*/
    }

    /////ADD TO CART
    public void addToCartPageMetrics(String currentPage, int productId, int price, int quantity, String isProductFavorite, String action) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(AppConstants.ADD_TO_CART_PRODUCT_ID, productId);
        bundle.putString(AppConstants.ADD_TO_CART_CURRENT_PAGE, currentPage);
        bundle.putInt(AppConstants.ADD_TO_CART_PRICE, price);
        bundle.putInt(AppConstants.ADD_TO_CART_QUANTITY, quantity);
        //bundle.putString(AppConstants.ADD_TO_CART_PRODUCT_TYPE, productType);
        bundle.putString(AppConstants.ADD_TO_CART_IS_FAVORITE_PRODUCT, isProductFavorite);
        bundle.putString(AppConstants.ADD_TO_CART_ACTION, action);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_ADD_TO_CART, bundle);*/
    }

    /////OPEN CART PAGE
    public void openCartPageMetrics(String previousScreen, String makeitId, int totalAmt, String promoCode, String deliveryAddressType, String nextPage, String cartProductIdQtyList) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {

            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.OPEN_CART_PAGE_MAKEIT_ID, makeitId);
        bundle.putString(AppConstants.OPEN_CART_PREVIOUS_PAGE, previousScreen);
        bundle.putInt(AppConstants.OPEN_CART_PAGE_TOTAL_AMOUNT, totalAmt);
        bundle.putString(AppConstants.OPEN_CART_PAGE_PROMO_CODE, promoCode);
        bundle.putString(AppConstants.OPEN_CART_PAGE_DELIVERY_ADDRESS_TYPE, deliveryAddressType);
        bundle.putString(AppConstants.OPEN_CART_PRODUCT_ID_AND_QUANTITY_LIST, cartProductIdQtyList);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_OPEN_CART_PAGE, bundle);*/
    }

    /////PAYMENT METHOD PAGE
    public void paymentMethodPageMetrics(String prevPage, String codOrOnline, String nextPage) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.PAYMENT_METHOD_PAGE_COD_OR_ONLINE, codOrOnline);
        bundle.putString(AppConstants.PAYMENT_METHOD_PREVIOUS_PAGE, prevPage);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_PAYMENT_METHOD_PAGE, bundle);*/
    }

    /////TRACK ORDER PAGE
    public void trackOrderPageMetrics(String prevPage, String orderId, String nextPage) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.TRACK_ORDER_PAGE_ORDER_ID, orderId);
        bundle.putString(AppConstants.TRACK_ORDER_PREVIOUS_PAGE, prevPage);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_TRACK_ORDER_PAGE, bundle);*/
    }

    /////SEARCH SUGGESTION PAGE
    public void searchSuggestionPageMetrics(String suggestionWord, int regionCount, String regionList, int kitchenCount, String kitchenList, int dishCount,
                                            String dishList) {
       /* if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.SEARCH_SUGGESTION_WORD, suggestionWord);
        bundle.putInt(AppConstants.SEARCH_SUGGESTION_REGION_COUNT, regionCount);
        bundle.putString(AppConstants.SEARCH_SUGGESTION_REGION_LIST, regionList);
        bundle.putInt(AppConstants.SEARCH_SUGGESTION_KITCHEN_COUNT, kitchenCount);
        bundle.putString(AppConstants.SEARCH_SUGGESTION_KITCHEN_LIST, kitchenList);
        bundle.putInt(AppConstants.SEARCH_SUGGESTION_DISH_COUNT, dishCount);
        bundle.putString(AppConstants.SEARCH_SUGGESTION_DISH_LIST, dishList);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_SEARCH_SUGGESTION, bundle);*/
    }

    /////PROCEED TO PAY
    public void proceedToPayPageMetrics(String productIdList, String productQtyList, String totalAmt, int promoCodeId, String previousPage, int mincart) {
     /*   if (BuildConfig.ENABLE_DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.PROCEED_TO_PAY_PRODUCT_ID_LIST, productIdList);
        bundle.putString(AppConstants.PROCEED_TO_PAY_PRODUCT_QUANTITY_LIST, productQtyList);
        bundle.putString(AppConstants.PROCEED_TO_PAY_AMOUNT, totalAmt);
        bundle.putInt(AppConstants.PROCEED_TO_PAY_PROMO_CODE, promoCodeId);
        bundle.putString(AppConstants.PROCEED_TO_PAY_PREVIOUS_PAGE, previousPage);
        bundle.putString(AppConstants.PROCEED_TO_PAY_PREVIOUS_PAGE, previousPage);
        bundle.putInt(AppConstants.PROCEED_TO_PAY_MINCARTVALUE, mincart);

        mFirebaseAnalytics.logEvent(AppConstants.METRICS_PROCEED_TO_PAY, bundle);*/
    }





    ////App opens
    public void eventAppOpens(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_APP_OPENS, bundle);
    }

    ////App opens
    public void eventPageOpens(Context context,String fromPage,String toPage) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_FROM_PAGE, fromPage);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_TO_PAGE, toPage);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_PAGE_OPENS, bundle);
    }

    ////Home page - social feed
    public void eventHomePageOpens(Context context,int pagination,int lastPos) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putInt(AppConstants.EVENT_HOME_PAGE_SOCIAL_FEED_PARAM_NO_OF_SWIPES_PAGE, pagination);
        bundle.putInt(AppConstants.EVENT_HOME_PAGE_SOCIAL_FEED_PARAM_POST_POSITION_OF_LAST_POST_SEEN, lastPos);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_HOME_PAGE_SOCIAL_FEED, bundle);
    }

    ////Feed traction on our community page
    public void eventFeedTractionOnHomePage(Context context/*,String page*/,String action) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
       // bundle.putString(AppConstants.PAGE, page);
        bundle.putString(AppConstants.EVENT_ACTION, action);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_HOME_PAGE_FEED_TRACTION, bundle);
    }

    ////Feed traction on our community page
    public void eventFeedTractionOnOurCommunityPage(Context context/*,String page*/,String action) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
      //  bundle.putString(AppConstants.PAGE, page);
        bundle.putString(AppConstants.EVENT_ACTION, action);
        mFirebaseAnalytics.logEvent(AppConstants.EVENT_COMMUNITY_PAGE_FEED_TRACTION, bundle);
    }

    ////Traction DLE banner tile
    public void eventTractionDLEBannerTile(Context context,int position) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putInt(AppConstants.EVENT_POSITION, position);
        mFirebaseAnalytics.logEvent(AppConstants.EVENT_TRACTION_DLE_BANNER_TILE, bundle);
    }

    ////Traction Non-DLE banner tile
    public void eventTractionNonDLEBannerTile(Context context,int position) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putInt(AppConstants.EVENT_POSITION, position);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_TRACTION_NON_DLE_BANNER_TILE, bundle);
    }

    ////Category page(page opened)
    public void eventCategoryPage(Context context,String type,int lastPos) {
         FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
       // bundle.putString(AppConstants.EVENT_CATEGORY_PAGE_PARAM_NO_OF_SWIPES, "");
        //bundle.putString(AppConstants.EVENT_CATEGORY_PAGE_PARAM_TIME_ON_PAGE, "");
        bundle.putString(AppConstants.EVENT_CATEGORY_PAGE_PARAM_TILE_TYPE_OF_LAST_TILE_SEEN, type);
        bundle.putInt(AppConstants.EVENT_CATEGORY_PAGE_PARAM_TILE_POSITION_OF_LAST_TILE_SEEN, lastPos);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_CATEGORY_PAGE, bundle);
    }

    ////Category tile
    public void eventCategoryTile(Context context,String catTileName,String catTileType,String catTilePos) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_CATEGORY_TILE_PARAM_CATEGORY_TILE_NAME, catTileName);
        bundle.putString(AppConstants.EVENT_CATEGORY_TILE_PARAM_CATEGORY_TILE_TYPE, catTileType);
        bundle.putString(AppConstants.EVENT_CATEGORY_TILE_PARAM_CATEGORY_TILE_POSITION, catTilePos);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_CATEGORY_TILE, bundle);
    }

    ////L1 Sub-category page
    public void eventL1SubCategoryPage(Context context,String l1scName,String catName,String l1ScPos) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_L1_SUB_CATEGORY_PARAM_L1_SC_NAME, l1scName);
        bundle.putString(AppConstants.EVENT_L1_SUB_CATEGORY_PARAM_CATEGORY_NAME, catName);
        bundle.putString(AppConstants.EVENT_L1_SUB_CATEGORY_PARAM_L1_SC_POSITION, l1ScPos);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_L1_SUB_CATEGORY, bundle);
    }

    ////Collection tile
    public void eventCollectionTile(Context context,String colTileName,String colTileType,String colTilePos,String colColId) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_COLLECTION_PARAM_COL_TILE_NAME, colTileName);
        bundle.putString(AppConstants.EVENT_COLLECTION_PARAM_COL_TILE_TYPE, colTileType);
        bundle.putString(AppConstants.EVENT_COLLECTION_PARAM_COL_TILE_POSITION, colTilePos);
        bundle.putString(AppConstants.EVENT_COLLECTION_PARAM_COL_ID, colColId);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_COLLECTION_TILE, bundle);
    }

    ////Item detail page
    public void eventItemDetailPage(Context context,String catName,String L1SubCat,String L2SubCat,String itemCost,String sourcePage) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_ITEM_DETAIL_PAGE_PARAM_ITEM_CATEGORY, catName);
        bundle.putString(AppConstants.EVENT_ITEM_DETAIL_PAGE_PARAM_ITEM_L1_SC, L1SubCat);
        bundle.putString(AppConstants.EVENT_ITEM_DETAIL_PAGE_PARAM_ITEM_L2_SC, L2SubCat);
        bundle.putString(AppConstants.EVENT_ITEM_DETAIL_PAGE_PARAM_ITEM_COST, itemCost);
        bundle.putString(AppConstants.EVENT_ITEM_DETAIL_PAGE_PARAM_SOURCE_PAGE_NAME, sourcePage);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_ITEM_DETAIL_PAGE, bundle);
    }

    ////Add button
    public void eventAddButton(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_NAME, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ACTION_TYPE, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_CATEGORY, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_L1, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_L2, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_COST, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_QUANTITY_AFTER_EVENT, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_ITEM_TAG, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_CART_VALUE, "");
        bundle.putString(AppConstants.EVENT_ADD_BUTTON_PARAM_PAGE_TYPE, "");

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_ADD_BUTTON, bundle);
    }

    ////User subscribe
    public void eventUserSubscribe(Context context, String itemName, String iCategory, String l1Name, String l2Name, String cost, String tag, int CartValue, int quantity, ArrayList<String> days, int totalDays,String startDate,String pageType) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_NAME, itemName);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_CATEGORY, iCategory);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_L1, l1Name);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_L2, l2Name);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_COST, cost);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_ITEM_TAG, tag);
        bundle.putInt(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_CART_VALUE_AFTER_SUBS, CartValue);
        bundle.putInt(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_UNITS_PER_DAY_IN_SUBS, quantity);
        bundle.putStringArrayList(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_DAYS_IN_WEEK, days);
        bundle.putInt(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_NO_OF_DAYS, totalDays);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_START_DATE, startDate);
        bundle.putString(AppConstants.EVENT_USER_SUBSCRIBE_PARAM_PAGE_TYPE, pageType);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_USER_SUBSCRIBE, bundle);
    }

    ////Abandon cart
    public void eventAbandonCart(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_ABANDON_CART, bundle);
    }

    ////Place order
    public void eventPlaceOrder(Context context,String dlMethod,String pMode,int cartSize,String cartValue,String gst,int delCharges,String couponName,String orderid) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_DELIVERY_METHOD, dlMethod);
        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_PAYMENT_MODE, pMode);
        bundle.putInt(AppConstants.EVENT_PLACE_ORDER_PARAM_NO_OF_PRODUCT_IN_CART, cartSize);
        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_PRODUCT_VALUE, cartValue);
      //  bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_CART_ITEMS, "");
        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_GST, gst);
        bundle.putString(AppConstants.EVENT_PARAM_ORDERID, orderid);
        bundle.putInt(AppConstants.EVENT_PLACE_ORDER_PARAM_DELIVERY_CHARGE, delCharges);
        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_COUPON_NAME, couponName);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_PLACE_ORDER, bundle);
    }

     ////Payment completed
    public void eventPaymentCompleted(Context context,String dlMethod,int cartSize,String cartValue,String gst,int delCharges,String couponName,String totalCharge,String orderid) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_DELIVERY_METHOD, dlMethod);
        bundle.putInt(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_CART_SIZE, cartSize);
        bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_CART_VALUE, cartValue);
       // bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_CART_ITEMS, "");
      //  bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_CARD_TYPE, "");
        bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_TOTAL_CHANGES, totalCharge);
     //   bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_MIS_FEE, "");
        bundle.putString(AppConstants.EVENT_PLACE_ORDER_PARAM_GST, gst);
        bundle.putString(AppConstants.EVENT_PARAM_ORDERID, orderid);
        bundle.putInt(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_DELIVERY_FEE, delCharges);
        bundle.putString(AppConstants.EVENT_PAYMENT_COMPLETED_PARAM_COUPON, couponName);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_PAYMENT_COMPLETED, bundle);
    }

    ////Search
    public void eventSearch(Context context,String searchTerms,String resultReturned,String resultClickedType,String resultClicked) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_SEARCH_PARAM_SEARCH_TERMS, searchTerms);
        bundle.putString(AppConstants.EVENT_SEARCH_PARAM_RESULTS_RETURNED, resultReturned);
        bundle.putString(AppConstants.EVENT_SEARCH_PARAM_RESULTS_CLICKED_TYPE, resultClickedType);
        bundle.putString(AppConstants.EVENT_SEARCH_PARAM_RESULTS_CLICKED, resultClicked);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_SEARCH, bundle);
    }

    ////Account created
    public void eventAccountCreated(Context context,String email,String gender,String apartmentName,String type) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);
        bundle.putString(AppConstants.EVENT_ACCOUNT_CREATED_PARAM_EMAIL, email);
        bundle.putString(AppConstants.EVENT_ACCOUNT_CREATED_PARAM_GENDER,gender);
        bundle.putString(AppConstants.EVENT_ACCOUNT_CREATED_PARAM_APARTMENT_NAME, apartmentName);
        bundle.putString(AppConstants.EVENT_ACCOUNT_CREATED_PARAM_USER_REGISTRATION_TYPE, type);


        mFirebaseAnalytics.logEvent(AppConstants.EVENT_ACCOUNT_CREATED, bundle);
    }

    ////DLE registration tile on category page
    public void eventDLEregistrationPageOnCategoryPage(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_DLE_REGISTRATION_TILE_ON_CATEGORY_PAGE, bundle);
    }

    ////GetSocial push notification
    public void eventGetSocialPushNotification(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_GET_SOCIAL_PUSH_NOTIFICATION, bundle);
    }

    ////Normal push notification
    public void eventNormalPushNotification(Context context) {
        FirebaseAnalytics mFirebaseAnalytics = null;
        if (BuildConfig.ENABLE_DEBUG) return;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_ID, userid);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_NAME, username);
        bundle.putString(AppConstants.EVENT_COMMON_PARAM_USER_PHONE_NUMBER, phoneNo);

        mFirebaseAnalytics.logEvent(AppConstants.EVENT_NORMAL_PUSH_NOTIFICATION, bundle);
    }

}
