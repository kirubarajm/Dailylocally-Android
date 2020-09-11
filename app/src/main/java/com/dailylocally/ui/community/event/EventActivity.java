package com.dailylocally.ui.community.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityEventBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.community.details.EventDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.media.MediaAttachment;

public class EventActivity extends BaseActivity<ActivityEventBinding, EventViewModel> implements EventNavigator, EventListAdapter.ProductsAdapterListener {

    @Inject
    EventViewModel mEventViewModel;
    @Inject
    EventListAdapter mEventListAdapter;

    ActivityEventBinding mActivityEventBinding;
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

    public static Intent newIntent(Context context, String topic, String title) {

        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra("topic", topic);
        intent.putExtra("title", title);
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
        return R.layout.activity_event;
    }

    @Override
    public EventViewModel getViewModel() {
        return mEventViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventViewModel.setNavigator(this);
        mActivityEventBinding = getViewDataBinding();
        mEventListAdapter.setListener(this);
        subscribeToLiveData();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            getTopicPost(intent.getExtras().getString("topic"));
            mEventViewModel.title.set(intent.getExtras().getString("title"));

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityEventBinding.recyclerPost.setLayoutManager(new LinearLayoutManager(EventActivity.this));
        mActivityEventBinding.recyclerPost.setAdapter(mEventListAdapter);
    }

    private void subscribeToLiveData() {
        mEventViewModel.getsocialActivitiesListLiveData().observe(this,
                postItemViewModel -> mEventViewModel.addCommunityPostToList(postItemViewModel));
    }


    private void getTopicPost(String topic) {


        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(topic);
        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            mEventViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);
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
            final List<GetSocialActivity> getSocialActivities2 = result.getEntries();

        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });




        Bundle bundle = new Bundle();
        bundle.putString("post_title", posts.getSource().getTitle());
        bundle.putString("id", posts.getId());
        bundle.putString("content", posts.getText());
        bundle.putString("time", String.valueOf(posts.getCreatedAt()));
        bundle.putString("comment_count", String.valueOf(posts.getCommentsCount()));
        if (posts.getButton() != null) {
            if (posts.getButton().getAction() != null)
                bundle.putBoolean("show_button", true);
                bundle.putString("button_text", posts.getButton().getTitle());
        }
        bundle.putString("icon", posts.getAuthor().getAvatarUrl());

        if (posts.getAttachments() != null)
            if (posts.getAttachments().size() > 0) {
                bundle.putBoolean("show_attachment", true);
                for (int i = 0; i < posts.getAttachments().size(); i++) {
                    final MediaAttachment attachment = posts.getAttachments().get(i);
                    if (attachment.getImageUrl() != null) {
                        if (i == 0) {
                            bundle.putBoolean("single_image", true);
                            bundle.putString("image1",attachment.getImageUrl());
                        } else {
                            bundle.putBoolean("single_image", false);
                            bundle.putString("image2",attachment.getImageUrl());
                        }
                    }
                }
            } else {
                bundle.putBoolean("show_attachment", false);
            }


        if (posts.getMyReactions() != null) {

            List<String> list = new ArrayList<>(posts.getMyReactions());

            if (list != null && list.size() > 0) {

                if (list.get(0).equals(Reactions.LIKE)) {
                    bundle.putBoolean("like", true);
                }else {
                    bundle.putBoolean("like", false);
                }

            }
        }

        Intent intent = EventDetailsActivity.newIntent(EventActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void actionData(Map<String, String> actionDatas) {

    }
}
