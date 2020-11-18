package com.dailylocally.ui.community.catlist;


import android.graphics.Bitmap;
import android.util.Base64;

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
import com.dailylocally.api.remote.VolleyMultiPartRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.community.CommunityUserDetailsResponse;
import com.dailylocally.ui.home.HomePageRequest;
import com.dailylocally.ui.home.HomepageResponse;
import com.dailylocally.ui.home.RatingCheckResponse;
import com.dailylocally.ui.home.RatingSkipRequest;
import com.dailylocally.ui.joinCommunity.DocumentUploadResponse;
import com.dailylocally.ui.promotion.bottom.PromotionRequest;
import com.dailylocally.ui.promotion.bottom.PromotionResponse;
import com.dailylocally.ui.signup.registration.NameGenderResponse;
import com.dailylocally.ui.signup.registration.RegistrationRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityCatViewModel extends BaseViewModel<CommunityCatNavigator> {

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
    public final ObservableBoolean isCommunityUser = new ObservableBoolean();

    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();
    public final ObservableBoolean imageLoader = new ObservableBoolean();
    public final ObservableField<String> profilePic = new ObservableField<>();
    public final ObservableField<String> welcomeText = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> minValue = new ObservableField<>();
    public final ObservableField<String> minValueText = new ObservableField<>();
    public final ObservableField<String> freeDelivery = new ObservableField<>();
    public final ObservableField<String> freeDeliveryText = new ObservableField<>();
    public final ObservableField<String> cod = new ObservableField<>();
    public final ObservableField<String> codText = new ObservableField<>();


    public final ObservableField<String> cat_page_content = new ObservableField<>();
    public final ObservableField<String> cat_page_subcontent = new ObservableField<>();

    public ObservableList<HomepageResponse.Result> categoryList = new ObservableArrayList<>();
    public String ratingDOID = "0";
    private MutableLiveData<List<HomepageResponse.Result>> categoryListLiveData;


    public CommunityCatViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        updateAvailable.set(getDataManager().isUpdateAvailable());

        profilePic.set(getDataManager().getUserProfilePic());
        name.set(getDataManager().getCurrentUserName());


        if (getDataManager().getUserDetails() != null) {

            Gson sGson = new GsonBuilder().create();
            CommunityUserDetailsResponse communityUserDetailsResponse = sGson.fromJson(getDataManager().getUserDetails(), CommunityUserDetailsResponse.class);
            if (communityUserDetailsResponse != null) {
                if (communityUserDetailsResponse.getResult() != null) {
                    if (communityUserDetailsResponse.getResult().size() > 0) {
                        CommunityUserDetailsResponse.Result result = communityUserDetailsResponse.getResult().get(0);
                        welcomeText.set(result.getWelcomeNameContent());
                        minValue.set(result.getMinCartValue());
                        minValueText.set(result.getMinCartText());
                        freeDelivery.set(result.getFreeDeliveryValue());
                        freeDeliveryText.set(result.getFreeDeliveryText());
                        cod.set(result.getCodValue());
                        codText.set(result.getCodText());
                        if (result.getCommunityStatus() != null)
                            isCommunityUser.set(result.getCommunityStatus());

                        cat_page_content.set(result.getCatPageContent());
                        cat_page_subcontent.set(result.getCatPageSubcontent());


                    }
                }

            }

        }

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
        skipRating();
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
                            //serviceable.set(false);


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
                                serviceable.set(true);
                                fullEmpty.set(true);
                                if (getNavigator() != null)
                                    getNavigator().dataLoaded();
                            }


                        } else {
                            serviceable.set(true);
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
                    fullEmpty.set(true);
                    serviceable.set(true);
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
                    return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
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

                            if (response.getStatus()) {

                                if (response.getRatingStatus()) {

                                    if (response.getResult() != null && response.getResult().size() > 0)

                                        if (response.getResult().get(0).getRatingSkip() != null) {
                                            if (Integer.parseInt(response.getResult().get(0).getRatingSkip()) <= response.getResult().get(0).getRatingSkipAvailable()) {
                                                ratingDOID = response.getResult().get(0).getId();
                                                showRating.set(true);
                                                ratingTitle.set(response.getTitle());
                                                ratingDate.set(parseDateToddMMyyyy(response.getResult().get(0).getDate()));
                                                // skipRating();
                                            }
                                        } else {
                                            ratingDOID = response.getResult().get(0).getId();
                                            showRating.set(true);
                                            ratingTitle.set(response.getTitle());
                                            ratingDate.set(parseDateToddMMyyyy(response.getResult().get(0).getDate()));
                                            // skipRating();

                                        }
                                }
                            }
                        }
                    } catch (Exception e) {
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

    public void ratingClick() {
        closeRating();
        if (getNavigator() != null) {
            getNavigator().ratingClick();
        }
    }


    public void changeProfile() {

        getNavigator().changeProfile();

    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadImage(Bitmap bitmap) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        imageLoader.set(true);
        final String image = getStringImage(bitmap);
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, AppConstants.URL_UPLOAD_DOCUMENT_PICKUP,
                DocumentUploadResponse.class, new Response.Listener<DocumentUploadResponse>() {
            @Override
            public void onResponse(DocumentUploadResponse response) {
                if (response != null) {
                    if (response.getSuccess()) {

                        saveProfilePic(response.getData().getLocation());

                    }
                }
                imageLoader.set(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageLoader.set(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "tag");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("accept-version", AppConstants.API_VERSION_ONE);
                headers.put("Authorization", "Bearer " + getDataManager().getApiToken());
                return headers;
            }

            @Override
            protected Map<String, VolleyMultiPartRequest.DataPart> getByteData() {
                Map<String, VolleyMultiPartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("lic", new VolleyMultiPartRequest.DataPart("lic", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        DailylocallyApp.getInstance().addToRequestQueue(volleyMultipartRequest);
    }

    public void saveProfilePic(String profileImageUrl) {


        String userIdMain = getDataManager().getCurrentUserId();
        RegistrationRequest registrationRequest;


        registrationRequest = new RegistrationRequest(userIdMain, profileImageUrl);


        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.REGISTRATION, NameGenderResponse.class, registrationRequest, new Response.Listener<NameGenderResponse>() {
                @Override
                public void onResponse(NameGenderResponse response) {
                    try {
                        if (response != null) {
                            if (response.getStatus()) {
                                profilePic.set(profileImageUrl);
                                getDataManager().updateProfilePic(profileImageUrl);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imageLoader.set(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageLoader.set(false);
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


}