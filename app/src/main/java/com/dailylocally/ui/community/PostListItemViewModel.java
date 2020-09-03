package com.dailylocally.ui.community;


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

public class PostListItemViewModel {

    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();

    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean postLike = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();

    private final GetSocialActivity posts;
    private final PostItemViewModelListener mListener;

    public PostListItemViewModel(PostItemViewModelListener mListener, GetSocialActivity result) {
        this.mListener = mListener;
        this.posts = result;

        postTitle.set(result.getAuthor().getDisplayName());
        postDes.set(result.getText());
        postDate.set(getDate(result.getCreatedAt()));
        actionText.set(result.getButton().getTitle());
        showAction.set(result.getButton().getAction() != null);
        icon.set(result.getAuthor().getAvatarUrl());


        if (result.getAttachments() != null)
            if (result.getAttachments().size() > 0) {
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
            } else {
                attachmentAvailable.set(false);
            }


        if (posts.getMyReactions() != null) {

            List<String> list = new ArrayList<>(posts.getMyReactions());

            if (list != null && list.size() > 0) {

                if (list.get(0).equals("like"))
                  postLike.set(true);

            }
        }

    }

    public static String formateddate(String date) {
        DateTime dateTime = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss a").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime twodaysago = today.minusDays(2);
        DateTime tomorrow = today.minusDays(-1);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today " + formattedDateFromString("dd-MM-yyyy hh:mm:ss", "hh:mm a", date);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday " + formattedDateFromString("dd-MM-yyyy hh:mm:ss", "hh:mm a", date);
        } else if (dateTime.toLocalDate().equals(tomorrow.toLocalDate())) {
            return "Tomorrow " + formattedDateFromString("dd-MM-yyyy hh:mm:ss", "hh:mm a", date);
        } else {
            return formattedDateFromString("dd-MM-yyyy hh:mm:ss", "MMM dd, yyyy hh:mm a", date);
        }
    }

    public static String formattedDateFromString(String inputFormat, String outputFormat, String inputDate) {

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

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }

    public void postLikeClick() {

        if (postLike.get()) {
            Communities.removeReaction(Reactions.LIKE, posts.getId(), new CompletionCallback() {
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
            Communities.addReaction(Reactions.LIKE, posts.getId(), new CompletionCallback() {
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

    public void actionClick() {
        if (posts.getButton().getAction().getType().equals("open page"))
            mListener.actionData(posts.getButton().getAction().getData());
    }

    public interface PostItemViewModelListener {
        void refresh();

        void actionData(Map<String, String> actionDatas);

    }
}
