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
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityEventBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.community.details.EventDetailsActivity;
import com.dailylocally.ui.main.MainViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.EndlessRecyclerViewScrollListener;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.common.PagingResult;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.media.MediaAttachment;

public class EventActivity extends BaseActivity<ActivityEventBinding, EventViewModel> implements EventNavigator, EventListAdapter.ProductsAdapterListener {

    @Inject
    EventViewModel mEventViewModel;
    @Inject
    EventListAdapter mEventListAdapter;
    Boolean loading = false;

    PagingResult<GetSocialActivity> pagingResult;
    PagingQuery<ActivitiesQuery> pagingQuery;

    LinearLayoutManager linearLayoutManager;
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
    // region Listeners
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int previousCount=mEventViewModel.getSocialActivities.size();
            if (! mEventViewModel.loading.get() ) {
                if (pagingResult != null && pagingQuery != null)
                    if (!pagingResult.isLastPage()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            mEventViewModel.loading.set(true);
                            Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                pagingResult = result;
                                final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                mEventViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                mEventViewModel.loading.set(false);

                                mActivityEventBinding.recyclerPost.scrollToPosition(previousCount+1);


                            }, exception -> {
                                mEventViewModel.loading.set(false);
                                // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                            });
                        }
                    }



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
        mActivityEventBinding.loader.startShimmerAnimation();


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
             getTopicPost(intent.getExtras().getString("topic"));
           //getTopicPost("home_page");
            mEventViewModel.title.set(intent.getExtras().getString("title"));

        }

        // linearLayoutManager = new LinearLayoutManager(EventActivity.this);
       // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      //  mActivityEventBinding.recyclerPost.setLayoutManager(new LinearLayoutManager(EventActivity.this));
        mActivityEventBinding.recyclerPost.setAdapter(mEventListAdapter);
        mActivityEventBinding.content.setSmoothScrollingEnabled(true);

       // mActivityEventBinding.recyclerPost.setHasFixedSize(true);
        mActivityEventBinding.recyclerPost.setItemViewCacheSize(50);
       mActivityEventBinding.recyclerPost.setDrawingCacheEnabled(true);
        mActivityEventBinding.recyclerPost.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);



      //  mActivityEventBinding.recyclerPost.addOnScrollListener(recyclerViewOnScrollListener);

        mActivityEventBinding.recyclerPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int previousCount=mEventViewModel.getSocialActivities.size();
                if (!mEventViewModel.loading.get()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mEventViewModel.getSocialActivities.size() - 1) {
                        //bottom of list!
                       /* loadMore();
                        isLoading = true;*/

                        if (pagingResult != null && pagingQuery != null)
                            if (!pagingResult.isLastPage()) {

                                mEventViewModel.loading.set(true);
                                Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                    pagingResult = result;
                                    final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                    mEventViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                    mEventViewModel.loading.set(false);

                                    mActivityEventBinding.recyclerPost.scrollToPosition(previousCount+1);


                                }, exception -> {
                                    mEventViewModel.loading.set(false);
                                    // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                });

                            }

                    }
                }
            }
        });




        /*mActivityEventBinding.recyclerPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int tt=linearLayoutManager.findLastCompletelyVisibleItemPosition();

                int previousCount=mEventViewModel.getSocialActivities.size();
                if (! mEventViewModel.loading.get() ) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == previousCount) {
                        //bottom of list!

                            if (pagingResult != null && pagingQuery != null)
                                if (!pagingResult.isLastPage()) {

                                        mEventViewModel.loading.set(true);
                                        Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                            pagingResult = result;
                                            final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                            mEventViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                            mEventViewModel.loading.set(false);

                                            mActivityEventBinding.recyclerPost.scrollToPosition(previousCount+1);


                                        }, exception -> {
                                            mEventViewModel.loading.set(false);
                                            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                        });

                                }




                    }
                }
            }
        });*/


        /*mActivityEventBinding.recyclerPost.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                if(dy > 0) //check for scroll down
                {
                  int  visibleItemCount = linearLayoutManager.getChildCount();
                    int    totalItemCount = linearLayoutManager.getItemCount();
                    int   pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading)
                    {

                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount && (visibleItemCount + pastVisiblesItems) >= 10)
                        {
                            loading = false;
                            Toast.makeText(EventActivity.this, "more", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });*/


       /* mActivityEventBinding.recyclerPost.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(EventActivity.this, "more", Toast.LENGTH_SHORT).show();
            }
        });*/



       /* ViewCompat.setNestedScrollingEnabled(  mActivityEventBinding.recyclerPost, false);

        mActivityEventBinding.content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

              
                if ((scrollY >= (mActivityEventBinding.content.getChildAt(mActivityEventBinding.content.getChildCount() - 1).getMeasuredHeight() - mActivityEventBinding.content.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {

                    int previousCount=mEventViewModel.getSocialActivities.size();

                    if (mEventViewModel.getSocialActivities.size() > 5) {
                        if (!mEventViewModel.loading.get()) {
                            //  productListAdapter.addLoader();
                            if (pagingResult != null && pagingQuery != null)
                                if (!pagingResult.isLastPage()) {
                                    mEventViewModel.loading.set(true);
                                    Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                        pagingResult = result;
                                        final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                        mEventViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                        mEventViewModel.loading.set(false);

                                        mActivityEventBinding.recyclerPost.scrollToPosition(previousCount+1);


                                    }, exception -> {
                                        mEventViewModel.loading.set(false);
                                        // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                    });


                                }
                        }
                    }

                }


            }
        });*/


       /* mActivityEventBinding.recyclerPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View visi = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                int lVV = recyclerView.getChildAdapterPosition(visi);
                int itemCount = mEventListAdapter.getItemCount();
                if (itemCount > 15) {
                    if (dy > 0) {
                        if (lVV >= itemCount - 2) {
                            if (!loading) {
                                //  productListAdapter.addLoader();
                                if (pagingResult != null && pagingQuery != null)
                                    if (!pagingResult.isLastPage()) {
                                        loading = true;
                                        Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                            pagingResult = result;
                                            final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                            mEventViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                            loading = false;
                                        }, exception -> {
                                            loading = false;
                                            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                        });


                                    }
                            }
                        }

                    } else {
                        // Scrolling down
                        // Toast.makeText(getContext(),"Loading more", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/


    }

    private void subscribeToLiveData() {
        mEventViewModel.getsocialActivitiesListLiveData().observe(this,
                postItemViewModel -> mEventViewModel.addCommunityPostToList(postItemViewModel));
    }


    private void getTopicPost(String topic) {

        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(topic);
        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(20);
        this.pagingQuery = pagingQuery;
        Communities.getActivities(pagingQuery, result -> {
            pagingResult = result;
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            mEventViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);

            mActivityEventBinding.loader.stopShimmerAnimation();
            mActivityEventBinding.loader.setVisibility(View.GONE);

        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
            mActivityEventBinding.loader.stopShimmerAnimation();
            mActivityEventBinding.loader.setVisibility(View.GONE);
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
    public void hideKBoard() {
        hideKeyboard();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void commentClick(GetSocialActivity posts) {

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
                            bundle.putString("image1", attachment.getImageUrl());
                        } else {
                            bundle.putBoolean("single_image", false);
                            bundle.putString("image2", attachment.getImageUrl());
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
                } else {
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

    @Override
    public void viewAllComment(GetSocialActivity posts) {
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
                            bundle.putString("image1", attachment.getImageUrl());
                        } else {
                            bundle.putBoolean("single_image", false);
                            bundle.putString("image2", attachment.getImageUrl());
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
                } else {
                    bundle.putBoolean("like", false);
                }

            }
        }
        Intent intent = EventDetailsActivity.newIntent(EventActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
