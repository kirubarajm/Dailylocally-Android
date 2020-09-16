package com.dailylocally.ui.community.details;


import android.text.format.DateFormat;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.CompletionCallback;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.media.MediaAttachment;

public class CommentsListItemViewModel {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> comment = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();

    public final ObservableBoolean isMore = new ObservableBoolean();
    private final GetSocialActivity posts;
    private final PostItemViewModelListener mListener;

    public CommentsListItemViewModel(PostItemViewModelListener mListener, GetSocialActivity result) {
        this.mListener = mListener;
        this.posts = result;

        //  postTitle.set(result.getAuthor().getDisplayName());
        name.set(result.getAuthor().getDisplayName());
        comment.set(result.getText());
        postDate.set(getDate(result.getCreatedAt()));

        isMore.set(false);


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

    public void commentClick() {

        if (isMore.get()){
            isMore.set(false);
            mListener.commentClick(false);
        }else {
            isMore.set(true);
            mListener.commentClick(true);
        }


    }


    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }




    public interface PostItemViewModelListener {
        void refresh();

        void actionClick();

        void commentClick(Boolean isMore);

        void actionData(Map<String, String> actionDatas);

    }
}
