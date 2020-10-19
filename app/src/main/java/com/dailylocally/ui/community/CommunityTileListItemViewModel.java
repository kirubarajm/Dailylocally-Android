package com.dailylocally.ui.community;


import android.text.format.DateFormat;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommunityTileListItemViewModel {

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

    private final PostItemViewModelListener mListener;
    public String whatsappgroupLink;
    public String sneakpeakVideoUrl;
    public String topic;
    public String eventTitle;
    public String homeEventTitle;
    public String homeEventTopic;
    AppPreferencesHelper appDataManager;


    public CommunityTileListItemViewModel(PostItemViewModelListener mListener) {
        this.mListener = mListener;

        appDataManager = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);


        getHomeDetails();


    }

    public static String formateddate(String date) {
        DateTime dateTime = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss a").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime twodaysago = today.minusDays(2);
        DateTime tomorrow = today.minusDays(-1);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today " + formattedDateFromString("dd-MM-yyyy hh:mm:ss a", "hh:mm a", date);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday " + formattedDateFromString("dd-MM-yyyy hh:mm:ss a", "hh:mm a", date);
        } else if (dateTime.toLocalDate().equals(tomorrow.toLocalDate())) {
            return "Tomorrow " + formattedDateFromString("dd-MM-yyyy hh:mm:ss a", "hh:mm a", date);
        } else {
            return formattedDateFromString("dd-MM-yyyy hh:mm:ss a", "MMM dd, yyyy hh:mm a", date);
        }
    }

    public static String formattedDateFromString(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            // Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }

    public void getHomeDetails() {


        try {
            Gson sGson = new GsonBuilder().create();
            CommunityHomeResponse response = sGson.fromJson(appDataManager.getCommunityHomepage(), CommunityHomeResponse.class);

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


                        } else {

                        }

                    } else {

                    }

                } else {

                }


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }


    public void communityEvent() {
        mListener.communityEvent();
    }

    public void whatsAppGroup() {
        mListener.whatsAppGroup();

    }

    public void sneakPeak() {

        mListener.sneakPeak();
    }

    public void aboutUs() {
        mListener.aboutUs();

    }

    public void gotoCommunityCat() {

        mListener.gotoCommunityHome();
    }


    public interface PostItemViewModelListener {

        void gotoCommunityHome();

        void whatsAppGroup();

        void sneakPeak();

        void aboutUs();

        void communityEvent();


    }
}
