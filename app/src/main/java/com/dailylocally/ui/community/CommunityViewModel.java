package com.dailylocally.ui.community;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.category.l2.L2CategoryRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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
    public final ObservableBoolean showRating = new ObservableBoolean();

    public final ObservableBoolean showVideo = new ObservableBoolean();

    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();

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


    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    private MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;


    public CommunityViewModel(DataManager dataManager) {
        super(dataManager);
        socialActivitiesListLiveData = new MutableLiveData<>();
        updateAvailable.set(getDataManager().isUpdateAvailable());


        if (getDataManager().getUserDetails() != null) {

            Gson sGson = new GsonBuilder().create();
            CommunityUserDetailsResponse communityUserDetailsResponse = sGson.fromJson(getDataManager().getUserDetails(), CommunityUserDetailsResponse.class);
            if (communityUserDetailsResponse != null) {
                if (communityUserDetailsResponse.getResult() != null) {
                    if (communityUserDetailsResponse.getResult().size() > 0) {
                        CommunityUserDetailsResponse.Result result = communityUserDetailsResponse.getResult().get(0);

                        profilePic.set(result.getProfileImage());
                        name.set(result.getName());
                        nameText.set(result.getWelcomeText());
                        members.set(result.getMembersCount());
                        membersText.set(result.getMembers());
                        communityName.set(result.getCommunityName());
                        communityArea.set(result.getCommunityArea());
                        credits.set(result.getTotalCredits());
                        creditsText.set(result.getCreditsText());
                        creditInfoText.set(result.getCreditsInfo());
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
        getSocialActivities.clear();
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
                                    //whatsImageUrl.set("https://dailylocally.s3.ap-south-1.amazonaws.com/admin/1596196480459-DLV2%20Category-Spreads.jpg");
                                    aboutUsTitle.set(result.getAbout().getTitle());
                                    aboutUsDes.set(result.getAbout().getDes());
                                    sneakTitle.set(result.getSneakPeak().getTitle());
                                    sneakDes.set(result.getSneakPeak().getDes());
                                    whatsTitle.set(result.getWhatsapp().getTitle());
                                    whatsDes.set(result.getWhatsapp().getDes());

                                    whatsappgroupLink=result.getWhatsapp().getGroupUrl();
                                    sneakpeakVideoUrl=result.getSneakPeak().getVideoUrl();
                                    topic=result.getEvent().getTopic();


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

    public void closeVideo(){

        getNavigator().stopVideo();

        showVideo.set(false);
    }

}