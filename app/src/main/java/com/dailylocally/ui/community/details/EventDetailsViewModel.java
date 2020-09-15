package com.dailylocally.ui.community.details;

import android.text.format.DateFormat;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import im.getsocial.sdk.Callback;
import im.getsocial.sdk.Communities;
import im.getsocial.sdk.CompletionCallback;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.communities.ActivityContent;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.PostActivityTarget;
import im.getsocial.sdk.communities.Reactions;

public class EventDetailsViewModel extends BaseViewModel<EventDetailsNavigator> {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();
    public final ObservableField<String> commentsCount = new ObservableField<>();
    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean postLike = new ObservableBoolean();
    public final ObservableBoolean commented = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();
    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    public MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;
    public String postId;
    public GetSocialActivity posts;

    public EventDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        socialActivitiesListLiveData = new MutableLiveData<>();
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

    public MutableLiveData<List<GetSocialActivity>> getsocialActivitiesListLiveData() {
        return socialActivitiesListLiveData;
    }

    public void addCommunityPostToList(List<GetSocialActivity> items) {
        getSocialActivities.clear();
        getSocialActivities.addAll(items);

    }

    public void back() {
        getNavigator().back();
    }

    public String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }

    public void postLikeClick() {

        if (postLike.get()) {
            Communities.removeReaction(Reactions.LIKE, postId, new CompletionCallback() {
                @Override
                public void onSuccess() {
                    postLike.set(false);
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });

        } else {
            Communities.addReaction(Reactions.LIKE, postId, new CompletionCallback() {
                @Override
                public void onSuccess() {
                    postLike.set(true);
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });
        }


    }

    public void commentClick() {

    }

    public void postComment(String text) {

        if (text.isEmpty()) return;

        ActivityContent content = new ActivityContent().withText(text);
        PostActivityTarget target1 = PostActivityTarget.comment(postId);

        Communities.postActivity(content, target1, new Callback<GetSocialActivity>() {
            @Override
            public void onSuccess(GetSocialActivity getSocialActivity) {
                commented.set(true);
                int comCount = 0;
                if (commentsCount.get() != null)
                    comCount = Integer.parseInt(commentsCount.get()) + 1;

                commentsCount.set(String.valueOf(comCount));
                if (getNavigator() != null)
                    getNavigator().postComment(getSocialActivity);
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(GetSocialError getSocialError) {

            }
        });

    }


}
