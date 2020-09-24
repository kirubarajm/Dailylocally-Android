package com.dailylocally.ui.community;


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
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.api.remote.VolleyMultiPartRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.category.l2.L2CategoryRequest;
import com.dailylocally.ui.joinCommunity.DocumentUploadResponse;
import com.dailylocally.ui.signup.registration.NameGenderResponse;
import com.dailylocally.ui.signup.registration.RegistrationRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.getsocial.sdk.communities.GetSocialActivity;

public class CommunityViewModel extends BaseViewModel<CommunityNavigator> {

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
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableBoolean showRating = new ObservableBoolean();

    public final ObservableBoolean showVideo = new ObservableBoolean();

    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();
    public final ObservableBoolean imageLoader = new ObservableBoolean();

    public final ObservableField<String> profilePic = new ObservableField<>();
    public final ObservableField<String> nameText = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> members = new ObservableField<>();
    public final ObservableField<String> membersText = new ObservableField<>();
    public final ObservableField<String> communityName = new ObservableField<>();
    public final ObservableField<String> communityArea = new ObservableField<>();
    public final ObservableField<String> credits = new ObservableField<>();
    public final ObservableField<String> creditsText = new ObservableField<>();
    public final ObservableField<String> creditInfoText = new ObservableField<>();

    public final ObservableField<String> eventImageUrl = new ObservableField<>();
    public final ObservableField<String> catImageUrl = new ObservableField<>();
    public final ObservableField<String> whatsImageUrl = new ObservableField<>();
    public final ObservableField<String> aboutImageUrl = new ObservableField<>();
    public final ObservableField<String> sneakPeakImageUrl = new ObservableField<>();
    public final ObservableField<String> aboutUsTitle = new ObservableField<>();
    public final ObservableField<String> aboutUsDes = new ObservableField<>();
    public final ObservableField<String> whatsTitle = new ObservableField<>();
    public final ObservableField<String> whatsDes = new ObservableField<>();
    public final ObservableField<String> sneakTitle = new ObservableField<>();
    public final ObservableField<String> sneakDes = new ObservableField<>();
    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();

    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean showCreditsInfo = new ObservableBoolean();
    public final ObservableBoolean postLike = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();

    public String whatsappgroupLink;
    public String sneakpeakVideoUrl;
    public String topic;
    public String eventTitle;

    public String homeEventTitle;
    public String homeEventTopic;


    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    public MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;


    public CommunityViewModel(DataManager dataManager) {
        super(dataManager);
        socialActivitiesListLiveData = new MutableLiveData<>();
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

                        nameText.set(result.getWelcomeText());
                        members.set(result.getMembersCount());
                        membersText.set(result.getMembers());
                        communityName.set(result.getCommunityName());
                        communityArea.set(result.getCommunityArea());
                        credits.set(result.getTotalCredits());
                        creditsText.set(result.getCreditsText());
                        creditInfoText.set(result.getCreditsInfo());


                        if (result.getShowCreditsInfo() != null)
                            showCreditsInfo.set(result.getShowCreditsInfo());

                    }
                }

            }

        }

        getHomeDetails();

        /*eventImageUrl.set("https://eattovo.s3.ap-south-1.amazonaws.com/upload/admin/makeit/product/1595868674050-DLV2%20Category-Bakery.jpg");
        catImageUrl.set("https://eattovo.s3.ap-south-1.amazonaws.com/upload/admin/makeit/product/1595847977688-Category%20Milk-01.jpg");
        whatsImageUrl.set("https://dailylocally.s3.ap-south-1.amazonaws.com/admin/1596196480459-DLV2%20Category-Spreads.jpg");
        aboutUsTitle.set("About Us");
        aboutUsDes.set("Know More about daily locally Exclusive");
        sneakTitle.set("Sneak Peak");
        sneakDes.set("Watch a short video on Daily Locally Exclusive");
        whatsTitle.set("What's Cooking in community");
        whatsDes.set("Join your community's WhatsApp group and socialize with the members");*/

    }

    public MutableLiveData<List<GetSocialActivity>> getsocialActivitiesListLiveData() {
        return socialActivitiesListLiveData;
    }


    public void addCommunityPostToList(List<GetSocialActivity> items) {
    //    getSocialActivities.clear();
        getSocialActivities.addAll(items);

    }


    public void postLikeClick() {
        getNavigator().postLikeClick();
    }


    public void creditInfoClick() {
        getNavigator().creditInfoClick();
    }


    public void actionBtClick() {
        getNavigator().actionBtClick();
    }

    public void gotoCommunityCat() {
        getNavigator().gotoCommunityHome();
    }

    public void aboutUs() {
        getNavigator().aboutUs();
    }

    public void sneakPeak() {
        getNavigator().sneakPeak();
    }

    public void whatsAppGroup() {
        getNavigator().whatsAppGroup();
    }

    public void communityEvent() {
        getNavigator().communityEvent();
    }


    public void changeProfile() {

        getNavigator().changeProfile();

    }

    public void getHomeDetails() {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_HOME_DETAILS, CommunityHomeResponse.class, new L2CategoryRequest(getDataManager().getCurrentUserId(), getDataManager().getCurrentLat(), getDataManager().getCurrentLng()), new Response.Listener<CommunityHomeResponse>() {
                @Override
                public void onResponse(CommunityHomeResponse response) {

                    if (response != null)
                        if (response.getStatus()) {
                            if (response.getResult() != null) {

                                if (response.getResult().size() > 0) {

                                    CommunityHomeResponse.Result result = response.getResult().get(0);


                                    eventImageUrl.set(result.getEvent().getImageUrl());
                                    catImageUrl.set(result.getCatList().getImageUrl());
                                    sneakPeakImageUrl.set(result.getSneakPeak().getImageUrl());
                                    whatsImageUrl.set(result.getWhatsapp().getImageUrl());
                                    aboutImageUrl.set(result.getAbout().getImageUrl());

                                    //whatsImageUrl.set("https://dailylocally.s3.ap-south-1.amazonaws.com/admin/1596196480459-DLV2%20Category-Spreads.jpg");
                                    aboutUsTitle.set(result.getAbout().getTitle());
                                    aboutUsDes.set(result.getAbout().getDes());

                                    sneakTitle.set(result.getSneakPeak().getTitle());
                                    sneakDes.set(result.getSneakPeak().getDes());

                                    whatsTitle.set(result.getWhatsapp().getTitle());
                                    whatsDes.set(result.getWhatsapp().getDes());

                                    whatsappgroupLink = result.getWhatsapp().getGroupUrl();
                                    sneakpeakVideoUrl = result.getSneakPeak().getVideoUrl();
                                    topic = result.getEvent().getTopic();
                                    eventTitle = result.getEvent().getTitle();

                                    homeEventTitle = result.getEvent().getHomeCommunityTitle();
                                    homeEventTopic = result.getEvent().getHomeCommunityTopic();

                                    if (getNavigator() != null)
                                        getNavigator().homeDataLoaded();

                                } else {
                                    /*if (getNavigator() != null)
                                        getNavigator().openHome();*/
                                }

                            } else {
                               /* if (getNavigator() != null)
                                    getNavigator().openHome();*/
                            }


                        } else {
                           /* if (getNavigator() != null)
                                getNavigator().openHome();*/
                        }


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

    public void closeVideo() {

        getNavigator().stopVideo();

        showVideo.set(false);
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
            //    imageLoader.set(false);

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
                                if (getNavigator()!=null)
                                    getNavigator().refreshProfile();
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