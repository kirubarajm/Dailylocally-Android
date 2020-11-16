package com.dailylocally.ui.community.details;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityEventDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

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
import java.util.Objects;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.media.MediaAttachment;

public class EventDetailsActivity extends BaseActivity<ActivityEventDetailsBinding, EventDetailsViewModel> implements EventDetailsNavigator, CommentsListAdapter.ProductsAdapterListener {

    @Inject
    EventDetailsViewModel mEventDetailsViewModel;
    @Inject
    CommentsListAdapter mCommentsListAdapter;

    ActivityEventDetailsBinding mActivityEventBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_PLACED;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(getApplicationContext());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        }
    };

    public static Intent newIntent(Context context,String fromPage,String toPage) {
        Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, toPage);
        return intent;
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        if (mWifiReceiver != null)
            unregisterReceiver(mWifiReceiver);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void back() {

        finish();
    }

    @Override
    public void postComment(GetSocialActivity getSocialActivity) {

        mActivityEventBinding.commentText.setText("");
        hideKeyboard();
        //    mCommentsListAdapter.addOneItems(getSocialActivity);
        getComments(mEventDetailsViewModel.postId);

    }

    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.eventViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_event_details;
    }

    @Override
    public EventDetailsViewModel getViewModel() {
        return mEventDetailsViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventDetailsViewModel.setNavigator(this);
        mActivityEventBinding = getViewDataBinding();
        mCommentsListAdapter.setListener(this);
        subscribeToLiveData();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            //  getTopicPost(intent.getExtras().getString("topic"));
            //    mEventDetailsViewModel.title.set(intent.getExtras().getString("title"));
            mEventDetailsViewModel.postId = intent.getExtras().getString("id");
            getActivityDetails(mEventDetailsViewModel.postId);
            getComments(mEventDetailsViewModel.postId);

           /* mEventDetailsViewModel.postTitle.set(intent.getExtras().getString("post_title"));
            mEventDetailsViewModel.postDes.set(intent.getExtras().getString("content"));
            mEventDetailsViewModel.postDate.set(mEventDetailsViewModel.getDate(Long.parseLong(intent.getExtras().getString("time"))));
            mEventDetailsViewModel.commentsCount.set(intent.getExtras().getString("comment_count"));

            mEventDetailsViewModel.showAction.set(intent.getExtras().getBoolean("show_button"));
            if (intent.getExtras().getBoolean("show_button")) {
                mEventDetailsViewModel.actionText.set(intent.getExtras().getString("button_text"));
            }

            mEventDetailsViewModel.icon.set(intent.getExtras().getString("icon"));
            mEventDetailsViewModel.attachmentAvailable.set(intent.getExtras().getBoolean("show_attachment"));

            if (intent.getExtras().getBoolean("show_attachment")) {
                if (intent.getExtras().getBoolean("single_image")) {
                    mEventDetailsViewModel.singleImage.set(intent.getExtras().getBoolean("single_image"));
                    mEventDetailsViewModel.image1.set(intent.getExtras().getString("image1"));
                } else {
                    mEventDetailsViewModel.image1.set(intent.getExtras().getString("image1"));
                    mEventDetailsViewModel.image2.set(intent.getExtras().getString("image2"));
                }
            }
            mEventDetailsViewModel.postLike.set(intent.getExtras().getBoolean("like"));*/
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventDetailsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mActivityEventBinding.recyclerPost.setLayoutManager(new LinearLayoutManager(EventDetailsActivity.this));
        mActivityEventBinding.recyclerPost.setAdapter(mCommentsListAdapter);

        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_COMMUNITY_EVENT_POST);
    }








    private void subscribeToLiveData() {
        mEventDetailsViewModel.getsocialActivitiesListLiveData().observe(this,
                postItemViewModel -> mEventDetailsViewModel.addCommunityPostToList(postItemViewModel));
    }


    public void getActivityDetails(String id) {

        Communities.getActivity(id, new im.getsocial.sdk.Callback<GetSocialActivity>() {
            @Override
            public void onSuccess(GetSocialActivity result) {

                mEventDetailsViewModel.postTitle.set(result.getSource().getTitle());
                mEventDetailsViewModel.postDes.set(result.getText());
                mEventDetailsViewModel.postDate.set(getDate(result.getCreatedAt()));
                mEventDetailsViewModel.commentsCount.set(String.valueOf(result.getCommentsCount()));

                if (result.getButton() != null) {
                    if (result.getButton().getAction() != null)
                        mEventDetailsViewModel.showAction.set(true);
                    mEventDetailsViewModel.actionText.set(result.getButton().getTitle());
                }
                mEventDetailsViewModel.icon.set(result.getAuthor().getAvatarUrl());
                if (result.getAttachments() != null)
                    if (result.getAttachments().size() > 0) {
                        mEventDetailsViewModel.attachmentAvailable.set(true);
                        for (int i = 0; i < result.getAttachments().size(); i++) {
                            final MediaAttachment attachment = result.getAttachments().get(i);
                            if (attachment.getImageUrl() != null) {
                                if (i == 0) {
                                    mEventDetailsViewModel.singleImage.set(true);
                                    mEventDetailsViewModel.image1.set(attachment.getImageUrl());
                                } else {
                                    mEventDetailsViewModel.singleImage.set(false);
                                    mEventDetailsViewModel.image2.set(attachment.getImageUrl());
                                }


                            }
                        }
                    } else {
                        mEventDetailsViewModel.attachmentAvailable.set(false);
                    }

                if (result.getMyReactions() != null) {

                    List<String> list = new ArrayList<>(result.getMyReactions());

                    if (list != null && list.size() > 0) {

                        if (list.get(0).equals(Reactions.LIKE))
                            mEventDetailsViewModel.postLike.set(true);

                    }
                }
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(GetSocialError getSocialError) {

            }
        });
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

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }

    private void getComments(String id) {
        final ActivitiesQuery query = ActivitiesQuery.commentsToActivity(id);
        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(100);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            Collections.reverse(getSocialActivities);
            mEventDetailsViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);
            //  mActivityEventBinding.recyclerPost.scrollToPosition(getSocialActivities.size()-1);


            mActivityEventBinding.recyclerPost.smoothScrollToPosition(mActivityEventBinding.recyclerPost.getBottom());
            mActivityEventBinding.fullContent.fullScroll(View.FOCUS_DOWN);


        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void canceled() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void commentClick(GetSocialActivity posts) {


        final ActivitiesQuery query = ActivitiesQuery.commentsToActivity(posts.getId());

        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();

        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });

    }

    @Override
    public void actionData(Map<String, String> actionDatas) {

    }
}
