package com.dailylocally.ui.community.event;


import android.text.format.DateFormat;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import im.getsocial.sdk.Callback;
import im.getsocial.sdk.Communities;
import im.getsocial.sdk.CompletionCallback;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.ActivityContent;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.PostActivityTarget;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.media.MediaAttachment;

public class EventListItemViewModel {

    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();
    public final ObservableField<String> commentsCount = new ObservableField<>();
    public final ObservableField<String> allCommentsCount = new ObservableField<>();

    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean postLike = new ObservableBoolean();
    public final ObservableBoolean commented = new ObservableBoolean();
    public final ObservableBoolean showComment = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();
    public final ObservableBoolean moreComments = new ObservableBoolean();
    private final GetSocialActivity posts;
    private final PostItemViewModelListener mListener;
    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    public MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;

    public EventListItemViewModel(PostItemViewModelListener mListener, GetSocialActivity result) {
        socialActivitiesListLiveData = new MutableLiveData<>();
        this.mListener = mListener;
        this.posts = result;

        //  postTitle.set(result.getAuthor().getDisplayName());
        postTitle.set(result.getSource().getTitle());
        postDes.set(result.getText());
        postDate.set(getDate(result.getCreatedAt()));



        commentsCount.set(String.valueOf(posts.getCommentsCount()));
        allCommentsCount.set("View all " +String.valueOf(posts.getCommentsCount())+" comments");


       /* if (posts.getSource().getType().equals(CommunitiesAction.COMMENT)){
            commented.set(true);
        }else {
            commented.set(false);
        }*/


        if (result.getButton() != null) {
            if (result.getButton().getAction() != null)
                showAction.set(true);
            actionText.set(result.getButton().getTitle());
        }

        //showAction.set(result.getButton().getAction() != null);
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

                if (list.get(0).equals(Reactions.LIKE))
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

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
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
                    mListener.dislike();
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
                    mListener.like();
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });
        }


    }

    public void commentClick() {

        if (showComment.get()) {
            showComment.set(false);
            moreComments.set(false);
        } else {
            moreComments.set(posts.getCommentsCount()>3);
            showComment.set(true);
            getComments(posts.getId());
        }


    }


    public void viewAllComment() {
        showComment.set(false);
        moreComments.set(false);
        mListener.viewAllComment(posts);
    }


    public void postComment(String text) {


        if (text.isEmpty()) return;

        ActivityContent content = new ActivityContent().withText(text);
        PostActivityTarget target1 = PostActivityTarget.comment(posts.getId());

        Communities.postActivity(content, target1, new Callback<GetSocialActivity>() {
            @Override
            public void onSuccess(GetSocialActivity getSocialActivity) {
                mListener.comment();
                commented.set(true);
                int comCount = 0;
                if (commentsCount.get() != null)
                    comCount = Integer.parseInt(commentsCount.get()) + 1;

                commentsCount.set(String.valueOf(comCount));
                // mListener.addOneComment(getSocialActivity);
                getComments(posts.getId());

            }
        }, new FailureCallback() {
            @Override
            public void onFailure(GetSocialError getSocialError) {

            }
        });

    }

    private void getComments(String id) {
        final ActivitiesQuery query = ActivitiesQuery.commentsToActivity(id);
        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(3);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            // socialActivitiesListLiveData.setValue(getSocialActivities);
            Collections.reverse(getSocialActivities);
            mListener.addComments(getSocialActivities);

        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });
    }


    public void actionClick() {
        if (posts.getButton().getAction().getType().equals("open page"))
            mListener.actionData(posts.getButton().getAction().getData());
    }

    public interface PostItemViewModelListener {
        void refresh();

        void like();
        void dislike();
        void comment();

        void actionClick();
        void viewAllComment(GetSocialActivity posts);

        void commentClick(GetSocialActivity posts);

        void addOneComment(GetSocialActivity posts);

        void addComments(List<GetSocialActivity> comments);

        void actionData(Map<String, String> actionDatas);

    }
}
