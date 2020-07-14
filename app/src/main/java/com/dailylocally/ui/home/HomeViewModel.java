package com.dailylocally.ui.home;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.promotion.bottom.PromotionRequest;
import com.dailylocally.ui.promotion.bottom.PromotionResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean fullEmpty = new ObservableBoolean();
    public final ObservableBoolean kitchenListLoading = new ObservableBoolean();
    public final ObservableField<String> emptyImageUrl = new ObservableField<>();
    public final ObservableField<String> emptyContent = new ObservableField<>();
    public final ObservableField<String> headerContent = new ObservableField<>();
    public final ObservableField<String> emptySubContent = new ObservableField<>();
    public final ObservableField<String> headerSubContent = new ObservableField<>();
    public final ObservableField<String> categoryTitle = new ObservableField<>();
    public final ObservableField<String> collectionTitle = new ObservableField<>();
    public final ObservableField<String> ratingTitle = new ObservableField<>();
    public final ObservableField<String> ratingDate = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean categoryLoading = new ObservableBoolean();
    public final ObservableBoolean showRating = new ObservableBoolean();

    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();

    public ObservableList<HomepageResponse.Result> categoryList = new ObservableArrayList<>();
    public String ratingDOID = "0";
    private MutableLiveData<List<HomepageResponse.Result>> categoryListLiveData;


    public HomeViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        updateAvailable.set(getDataManager().isUpdateAvailable());

    }

    public MutableLiveData<List<HomepageResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }


    public void addCategoryToList(List<HomepageResponse.Result> items) {
        categoryList.clear();
        categoryList.addAll(items);

    }


    public String updateAddressTitle() {
        addressTitle.set(getDataManager().getCurrentAddressTitle());
        return getDataManager().getCurrentAddressTitle();

    }

    public void myOrders() {
        getNavigator().gotoOrders();
    }

    public void closeUnserviceable() {
        serviceable.set(true);
    }

    public void closeRating() {
        showRating.set(false);
    }

    public void changeAddress() {
        getNavigator().changeAddress();
    }

    public boolean isAddressAdded() {

        return getDataManager().getCurrentLat() != null;

    }

    public void fetchCategoryList() {


        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        HomePageRequest homePageRequest = new HomePageRequest();
        homePageRequest.setUserid(getDataManager().getCurrentUserId());
        homePageRequest.setLat(getDataManager().getCurrentLat());
        homePageRequest.setLon(getDataManager().getCurrentLng());
       /* homePageRequest.setUserid("1");
        homePageRequest.setLat("12.979937");
        homePageRequest.setLon( "80.218418");*/
        Gson gson = new Gson();
        String json = gson.toJson(homePageRequest);
        //  getDataManager().setFilterSort(json);

        try {
//                setIsLoading(true);
            categoryLoading.set(true);
            //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"http://192.168.1.102/tovo/infinity_kitchen.json", new JSONObject(json), new Response.Listener<JSONObject>() {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_CATEGORY_LIST, new JSONObject(json), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject homepageResponse) {
                    try {

                        HomepageResponse response;
                        Gson sGson = new GsonBuilder().create();
                        response = sGson.fromJson(homepageResponse.toString(), HomepageResponse.class);

                        if (response != null) {

                            getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                            serviceable.set(response.getServiceablestatus());
                            unserviceableTitle.set(response.getUnserviceableTitle());
                            unserviceableSubTitle.set(response.getUnserviceableSubtitle());
                            emptyImageUrl.set(response.getEmptyUrl());
                            emptyContent.set(response.getEmptyContent());
                            emptySubContent.set(response.getEmptySubconent());
                            headerContent.set(response.getHeaderContent());
                            headerSubContent.set(response.getHeaderSubconent());
                            categoryTitle.set(response.getCategoryTitle());

                            if (getNavigator() != null)
                                getNavigator().changeHeaderText(response.getHeaderContent());

                            if (getNavigator() != null)
                                getNavigator().changeHeaderText(response.getHeaderContent());

                            if (response.getResult() != null && response.getResult().size() > 0) {
                                fullEmpty.set(false);
                                categoryListLiveData.setValue(response.getResult());
                                if (getNavigator() != null)
                                    getNavigator().dataLoaded();

                            } else {
                                fullEmpty.set(true);
                                if (getNavigator() != null)
                                    getNavigator().dataLoaded();
                            }


                        } else {
                            fullEmpty.set(true);
                            if (getNavigator() != null)
                                getNavigator().dataLoaded();

                        }


                        setIsLoading(false);
                        categoryLoading.set(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("", ""+error.getMessage());
                    categoryLoading.set(false);
                    if (getNavigator() != null)
                        getNavigator().dataLoaded();
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public void searchClick() {
        if (getNavigator() != null) {
            getNavigator().searchClick();
        }
    }

    public void getPromotions() {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PROMOTION, PromotionResponse.class, new PromotionRequest(getDataManager().getCurrentUserId()), new Response.Listener<PromotionResponse>() {
                @Override
                public void onResponse(PromotionResponse response) {

                    if (response != null) {

                        if (response.getStatus()) {

                            //      getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(),response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                            if (getDataManager().getPromotionAppStartAgain())
                                if (response.getResult() != null && response.getResult().size() > 0) {
                                    if (response.getResult().get(0).getShowStatus()) {


                                        if (getDataManager().getCurrentUserId().equals(getDataManager().getCurrentPromotionUserId())) {

                                            if (getDataManager().getPromotionId().equals(response.getResult().get(0).getPid())) {

                                                Date c = Calendar.getInstance().getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                String currentdate = df.format(c);

                                                String promotionDate = getDataManager().getPromotionShowedDate();

                                                if (!getDataManager().getPromotionShowedDate().equals(currentdate)) {

                                                    if (getDataManager().getPromotionDisplayedCount() <= response.getResult().get(0).getNumberoftimes()) {
                                                        getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());
                                                    }

                                                } else {
                                                    if (getDataManager().getPromotionDailyCount() <= response.getResult().get(0).getDailyShowCount()) {
                                                        if (getDataManager().getPromotionDisplayedCount() <= response.getResult().get(0).getNumberoftimes()) {
                                                            getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());
                                                        }
                                                    }
                                                }

                                            } else {
                                                getDataManager().savePromotionDailyCount(0);
                                                getDataManager().savePromotionDisplayedCount(0);
                                                getDataManager().savePromotionSeen(false);
                                                getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                                            }
                                        } else {
                                            getDataManager().savePromotionDisplayedCount(0);
                                            getDataManager().savePromotionDailyCount(0);
                                            getDataManager().savePromotionSeen(false);
                                            getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                                        }
                                    }


                                }

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
                }
            }, AppConstants.API_VERSION_ONE);


            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


    public void getRatings() {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_RATING_CHECK, RatingCheckResponse.class, new PromotionRequest(getDataManager().getCurrentUserId()), new Response.Listener<RatingCheckResponse>() {
                @Override
                public void onResponse(RatingCheckResponse response) {

                    try {

                    if (response != null) {

                        if (response.getStatus()){

                        if (response.getRatingStatus()) {

                            if (response.getResult() != null && response.getResult().size() > 0)

                                if (response.getResult().get(0).getRatingSkip() != null) {
                                    if (Integer.parseInt(response.getResult().get(0).getRatingSkip()) <= response.getResult().get(0).getRatingSkipAvailable()) {
                                        ratingDOID = response.getResult().get(0).getId();
                                        showRating.set(true);
                                        ratingTitle.set(response.getTitle());
                                        ratingDate.set(response.getResult().get(0).getDate());
                                        skipRating();
                                    }
                                } else {
                                    ratingDOID = response.getResult().get(0).getId();
                                    showRating.set(true);
                                    ratingTitle.set(response.getTitle());
                                    ratingDate.set(parseDateToddMMyyyy(response.getResult().get(0).getDate()));
                                    skipRating();

                                }
                        }
                    }
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
                }
            }, AppConstants.API_VERSION_ONE);


            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
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


    public void skipRating() {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_RATING_SKIP, CommonResponse.class, new RatingSkipRequest(ratingDOID), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);


            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }

    public void ratingClick(){
        if (getNavigator()!=null){
            getNavigator().ratingClick();
        }
    }


}