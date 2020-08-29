package com.dailylocally.ui.community;


import android.text.format.DateFormat;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.media.MediaAttachment;

public class PostListItemViewModel {

    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();

    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();

    private final GetSocialActivity products;
    private final PostItemViewModelListener mListener;

    public PostListItemViewModel(PostItemViewModelListener mListener, GetSocialActivity result) {
        this.mListener = mListener;
        this.products = result;

        postTitle.set(result.getAuthor().getDisplayName());
        postDes.set(result.getText());
        postDate.set(getDate(result.getCreatedAt()));
        actionText.set(result.getButton().getTitle());
        showAction.set(result.getButton().getAction() != null);
        icon.set(result.getAuthor().getAvatarUrl());

        if (result.getAttachments() != null)
           if (result.getAttachments().size()>0) {
               attachmentAvailable.set(true);
               for (int i = 0; i < result.getAttachments().size(); i++) {
                   final MediaAttachment attachment = result.getAttachments().get(i);
                   if (attachment.getImageUrl() != null) {
                       if (i == 0) {
                           singleImage.set(true);
                           image1.set(attachment.getImageUrl());
                       } else {
                           singleImage.set(false);
                           image2.set(attachment.getImageUrl());
                       }


                   }
               }
           }else {
               attachmentAvailable.set(false);
           }
    }

    public interface PostItemViewModelListener {
        void refresh();


    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }


    public static String formateddate(String date) {
        DateTime dateTime = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss a").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime twodaysago = today.minusDays(2);
        DateTime tomorrow= today.minusDays(-1);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today " +formattedDateFromString("dd-MM-yyyy hh:mm:ss","hh:mm a",date);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday "+formattedDateFromString("dd-MM-yyyy hh:mm:ss","hh:mm a",date);
        }  else if (dateTime.toLocalDate().equals(tomorrow.toLocalDate())) {
            return "Tomorrow "+formattedDateFromString("dd-MM-yyyy hh:mm:ss","hh:mm a",date);
        } else {
            return formattedDateFromString("dd-MM-yyyy hh:mm:ss","MMM dd, yyyy hh:mm a",date);
        }
    }

    public static String formattedDateFromString(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            // Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }
}
