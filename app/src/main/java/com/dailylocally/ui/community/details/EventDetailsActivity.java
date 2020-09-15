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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityEventDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.GetSocialActivity;

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

    public static Intent newIntent(Context context) {
        return new Intent(context, EventDetailsActivity.class);
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
            mEventDetailsViewModel. postId = intent.getExtras().getString("id");
            getComments(mEventDetailsViewModel.postId);
            mEventDetailsViewModel.postTitle.set(intent.getExtras().getString("post_title"));
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
            mEventDetailsViewModel.postLike.set(intent.getExtras().getBoolean("like"));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventDetailsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.setReverseLayout(true);

        mActivityEventBinding.recyclerPost.setLayoutManager(new LinearLayoutManager(EventDetailsActivity.this));
        mActivityEventBinding.recyclerPost.setAdapter(mCommentsListAdapter);
    }

    private void subscribeToLiveData() {
        mEventDetailsViewModel.getsocialActivitiesListLiveData().observe(this,
                postItemViewModel -> mEventDetailsViewModel.addCommunityPostToList(postItemViewModel));
    }


    private void getComments(String id) {
        final ActivitiesQuery query = ActivitiesQuery.commentsToActivity(id);
        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            mEventDetailsViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);
          //  mActivityEventBinding.recyclerPost.scrollToPosition(getSocialActivities.size()-1);
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
