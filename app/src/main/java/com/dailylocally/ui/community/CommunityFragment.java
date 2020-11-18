package com.dailylocally.ui.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCommunityBinding;
import com.dailylocally.ui.aboutus.AboutUsActivity;
import com.dailylocally.ui.address.type.CommunitySearchActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.promotion.bottom.PromotionFragment;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.ui.video.VideoActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.CompletionCallback;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocial;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.common.PagingResult;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.CurrentUser;
import im.getsocial.sdk.communities.FollowQuery;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Identity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.communities.UserId;
import im.getsocial.sdk.communities.UserUpdate;

import static com.dailylocally.utilities.AppConstants.STORAGE_PERMISSION_REQUEST_CODE;

public class CommunityFragment extends BaseFragment<FragmentCommunityBinding, CommunityViewModel> implements CommunityNavigator, CommunityPostListAdapter.ProductsAdapterListener {
    private static final int RC_APP_UPDATE = 55669;
    public ToolTipView myToolTipView;
    @Inject
    CommunityPostListAdapter communityPostListAdapter;
    @Inject
    CommunityViewModel mCommunityViewModel;
    FragmentCommunityBinding mFragmentCommunityBinding;
    GetSocialActivity firstPost;
    Bitmap imageBitmap;
int pagination=1;
    PagingResult<GetSocialActivity> pagingResult;
    PagingQuery<ActivitiesQuery> pagingQuery;
    LinearLayoutManager layoutManager;


    public static CommunityFragment newInstance(String fromPage, String toPage) {
        Bundle args = new Bundle();
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public int getBindingVariable() {
        return BR.communityViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    public CommunityViewModel getViewModel() {
        return mCommunityViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void showPromotions(String url, boolean fullScreen, int type, int promotionid) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PROMOTION_TYPE, type);
        bundle.putInt(AppConstants.PROMOTION_ID, promotionid);
        bundle.putString(AppConstants.PROMOTION_URL, url);
        bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_COMMUNITY);
        bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_PROMOTION);

        PromotionFragment bottomSheetFragment = new PromotionFragment();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());

    }

    @Override
    public void gotoCommunityHome() {
        ((MainActivity) getActivity()).openCommunityCat();
    }

    public void saveHomeCloseAnalytics(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mFragmentCommunityBinding.postList.getLayoutManager();
        new Analytics().eventHomePageOpens(getContext(),pagination,linearLayoutManager.findLastCompletelyVisibleItemPosition());
    }

    @Override
    public void whatsAppGroup() {

      saveHomeCloseAnalytics();

        if (mCommunityViewModel.communityUser.get()) {

            String url = mCommunityViewModel.whatsappgroupLink;
            try {
                if (url != null && !url.isEmpty()) {
                    Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                    //String url = "https://chat.whatsapp.com/<group_link>";
                    intentWhatsapp.setData(Uri.parse(url));
                    intentWhatsapp.setPackage("com.whatsapp");
                    startActivity(intentWhatsapp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            if (mCommunityViewModel.getDataManager().isCommunityOnboardSeen()) {
                Intent inIntent = CommunitySearchActivity.newIntent(getContext(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.URL_COMMUNITY_SEARCH);
                inIntent.putExtra("newuser", false);
                inIntent.putExtra("lat", mCommunityViewModel.getDataManager().getCurrentLat());
                inIntent.putExtra("lng", mCommunityViewModel.getDataManager().getCurrentLng());
                startActivityForResult(inIntent, AppConstants.SELECT_COMMUNITY_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                Intent inIntent = CommunityOnBoardingActivity.newIntent(getContext());
                inIntent.putExtra("next", false);
                startActivity(inIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }


        }
    }

    @Override
    public void sneakPeak() {
        saveHomeCloseAnalytics();
        if (mCommunityViewModel.sneakpeakVideoUrl != null) {

            Intent tDintent = VideoActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_VIDEO);
            // Intent tDintent=new Intent(getContext(),VideoActivity.class);

            tDintent.putExtra("video", mCommunityViewModel.sneakpeakVideoUrl);
            startActivity(tDintent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            //  mCommunityViewModel.showVideo.set(true);
            //   mFragmentCommunityBinding.videoPlayer.setSource(mCommunityViewModel.sneakpeakVideoUrl);
            //   mFragmentCommunityBinding.videoPlayer.setSource("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        }


    }

    @Override
    public void aboutUs() {
        saveHomeCloseAnalytics();
        Intent intent = AboutUsActivity.newIntent(getContext(), "", "");
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void communityEvent() {
        /*final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(mCommunityViewModel.topic);
        //ActivityFeedViewBuilder.create(query).show();
        //  ActivityFeedViewBuilder.create(query).setWindowTitle("test").show();

        boolean wasLoaded = GetSocialUi.loadConfiguration("getsocial-light/ui-config.json");

        if (wasLoaded)
            ActivityFeedViewBuilder.create(query).setMentionClickListener(new MentionClickListener() {
                @Override
                public void onMentionClicked(String s) {
                    //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }).setActionListener(new ActionListener() {
                @Override
                public void handleAction(Action action) {

                    if (action.getType().equals("open page"))
                        actionData(action.getData());
                }
            }).setWindowTitle(mCommunityViewModel.eventTitle).show();*/
        saveHomeCloseAnalytics();
        Intent intent = EventActivity.newIntent(getContext(), mCommunityViewModel.topic, mCommunityViewModel.eventTitle, AppConstants.SCREEN_NAME_COMMUNITY,
                AppConstants.SCREEN_NAME_POST_DETAILS);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void creditInfoClick(TextView infoImageView) {
        if (mCommunityViewModel.showCreditsInfo.get()) {

            if (myToolTipView != null) {
                myToolTipView.remove();
                myToolTipView = null;
                return;
            }

            ToolTip toolTip = new ToolTip()
                    .withContentView(LayoutInflater.from(DailylocallyApp.getInstance()
                    ).inflate(R.layout.tool_tip_info, null))
                    //  .withText(mCommunityViewModel.creditInfoText.get())
                    .withColor(DailylocallyApp.getInstance().getResources().getColor(R.color.lgray))
                    .withShadow()
                    .withTextColor(Color.BLACK)
                    .withAnimationType(ToolTip.AnimationType.NONE);
            myToolTipView = mFragmentCommunityBinding.communityToolTipLayout.showToolTipForView(toolTip, infoImageView);
            //   myToolTipView = relativeLayout.showToolTipForView(toolTip,imageView);
            TextView title = myToolTipView.findViewById(R.id.info);
            title.setText(mCommunityViewModel.creditInfoText.get());

            myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
                @Override
                public void onToolTipViewClicked(ToolTipView toolTipView) {

                    if (myToolTipView != null) {
                        myToolTipView.remove();

                    }
                }
            });
        }
    }

    @Override
    public void like() {
        new Analytics().eventFeedTractionOnHomePage(getContext(),AppConstants.EVENT_LIKE );
    }

    @Override
    public void dislike() {
        new Analytics().eventFeedTractionOnHomePage(getContext(),AppConstants.EVENT_disLIKE );
    }

    @Override
    public void stopVideo() {
        //  mFragmentCommunityBinding.videoPlayer.stopPlayer();
    }

    @Override
    public void actionBtClick() {

        actionData(firstPost.getButton().getAction().getData(), 1, 1);
    }

    @Override
    public void postLikeClick() {


        if (mCommunityViewModel.postLike.get()) {
            Communities.removeReaction(Reactions.LIKE, firstPost.getId(), new CompletionCallback() {
                @Override
                public void onSuccess() {
                    mCommunityViewModel.postLike.set(false);
                    new Analytics().eventFeedTractionOnHomePage(getContext(),AppConstants.EVENT_disLIKE );
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });

        } else {
            Communities.addReaction(Reactions.LIKE, firstPost.getId(), new CompletionCallback() {
                @Override
                public void onSuccess() {
                    mCommunityViewModel.postLike.set(true);
                    new Analytics().eventFeedTractionOnHomePage(getContext(),AppConstants.EVENT_LIKE );
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });
        }

    }

    @Override
    public void creditInfoClick() {


        /*if (mCommunityViewModel.showCreditsInfo.get()) {


            if (myToolTipView != null) {
                myToolTipView.remove();
                myToolTipView = null;
                return;
            }

            ToolTip toolTip = new ToolTip()
                    .withContentView(LayoutInflater.from(DailylocallyApp.getInstance()
                    ).inflate(R.layout.tool_tip_info, null))
                    //  .withText(mCommunityViewModel.creditInfoText.get())
                    .withColor(DailylocallyApp.getInstance().getResources().getColor(R.color.lgray))
                    .withShadow()
                    .withTextColor(Color.BLACK)
                    .withAnimationType(ToolTip.AnimationType.NONE);
            myToolTipView = mFragmentCommunityBinding.communityToolTipLayout.showToolTipForView(toolTip, mFragmentCommunityBinding.creditInfo);
            //   myToolTipView = relativeLayout.showToolTipForView(toolTip,imageView);
            TextView title = myToolTipView.findViewById(R.id.info);
            title.setText(mCommunityViewModel.creditInfoText.get());

            myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
                @Override
                public void onToolTipViewClicked(ToolTipView toolTipView) {

                    if (myToolTipView != null) {
                        myToolTipView.remove();

                    }
                }
            });
        }*/

    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void changeProfile() {
        /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_JOIN);*/

        saveHomeCloseAnalytics();
        if (checkIfAlreadyhavePermission()) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setAspectRatio(1, 1)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(getBaseActivity());
        } else {
            ActivityCompat.requestPermissions(getBaseActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void refreshProfile() {
        communityPostListAdapter.notifyItemChanged(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case AppConstants.STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setAspectRatio(1, 1)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .start(getBaseActivity());

                }
        }
    }


    @Override
    public void homeDataLoaded() {
        GetSocial.addOnInitializeListener(new Runnable() {
            @Override
            public void run() {
                // GetSocial is ready to be used

                showRecentPosts();

            }
        });
        Communities.isFollowing(UserId.currentUser(), FollowQuery.topics(mCommunityViewModel.homeEventTopic, mCommunityViewModel.topic), new im.getsocial.sdk.Callback<Map<String, Boolean>>() {
            @Override
            public void onSuccess(Map<String, Boolean> stringBooleanMap) {
                try {
                    if (stringBooleanMap != null) {

                        if (!stringBooleanMap.get(mCommunityViewModel.homeEventTopic)) {

                            Communities.follow(FollowQuery.topics(mCommunityViewModel.homeEventTopic), new im.getsocial.sdk.Callback<Integer>() {
                                @Override
                                public void onSuccess(Integer integer) {

                                }
                            }, new FailureCallback() {
                                @Override
                                public void onFailure(GetSocialError getSocialError) {

                                }
                            });

                        }

                        if (!stringBooleanMap.get(mCommunityViewModel.topic)) {

                            Communities.follow(FollowQuery.topics(mCommunityViewModel.topic), new im.getsocial.sdk.Callback<Integer>() {
                                @Override
                                public void onSuccess(Integer integer) {

                                }
                            }, new FailureCallback() {
                                @Override
                                public void onFailure(GetSocialError getSocialError) {

                                }
                            });

                        }

                    }
                } catch (Exception eee) {
                    eee.printStackTrace();
                }

            }
        }, new FailureCallback() {
            @Override
            public void onFailure(GetSocialError getSocialError) {

            }
        });


        Communities.follow(FollowQuery.topics(mCommunityViewModel.homeEventTopic, mCommunityViewModel.topic), new im.getsocial.sdk.Callback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {

            }
        }, new FailureCallback() {
            @Override
            public void onFailure(GetSocialError getSocialError) {

            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunityViewModel.setNavigator(this);
        communityPostListAdapter.setListener(this);
        subscribeToLiveData();


        createGetSocialIdentity();


        /*Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_HOME);*/
    }

    public void createGetSocialIdentity() {


        try {


            String userId = mCommunityViewModel.getDataManager().getCurrentUserId(); // get user ID on your login provider
            String accessToken = ""; // see the example of such a function below

            Identity identity = Identity.custom(mCommunityViewModel.getDataManager().getCurrentUserName(), userId, accessToken);
            CurrentUser currentUser = GetSocial.getCurrentUser();

            if (currentUser == null) {
                // you can't add identity before SDK is initialized
                return;
            }
            currentUser.addIdentity(identity, new CompletionCallback() {
                @Override
                public void onSuccess() {

                }

            }, null, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });

            UserUpdate batchUpdate = new UserUpdate()
                    .updateAvatarUrl(mCommunityViewModel.profilePic.get())
                    .updateDisplayName(mCommunityViewModel.getDataManager().getCurrentUserName());
            //   .setPublicProperty(publicProperty1, newPublicValue)
            //   .removePublicProperty(publicProperty2)
            //    .setPrivateProperty(privateProperty1, newPrivateValue)
            //     .removePrivateProperty(privateProperty2)


            currentUser.updateDetails(batchUpdate, new CompletionCallback() {
                @Override
                public void onSuccess() {

                }

            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });
            Notifications.registerDevice();

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    @Override
    public void searchClick() {
        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/
        ((MainActivity) getActivity()).openExplore(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentCommunityBinding = getViewDataBinding();
        // mFragmentHomeBinding.loader.start();
        //  mFragmentCommunityBinding.loader.startShimmerAnimation();
        mCommunityViewModel.categoryLoading.set(true);
        mFragmentCommunityBinding.loader.startShimmerAnimation();

        /*RecyclerView.LayoutManager layoutManager = new  LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mFragmentCommunityBinding.postList.setLayoutManager(layoutManager);*/

      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentCommunityBinding.postList.setLayoutManager(new LinearLayoutManager(getContext()));*/
        mFragmentCommunityBinding.postList.setAdapter(communityPostListAdapter);
        //  mFragmentCommunityBinding.content.setSmoothScrollingEnabled(true);
        // mFragmentCommunityBinding.postList.setNestedScrollingEnabled(false);
        // mActivityEventBinding.recyclerPost.setHasFixedSize(true);
        mFragmentCommunityBinding.postList.setItemViewCacheSize(50);
        mFragmentCommunityBinding.postList.setDrawingCacheEnabled(true);
        mFragmentCommunityBinding.postList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        //  mActivityEventBinding.recyclerPost.addOnScrollListener(recyclerViewOnScrollListener);

        mFragmentCommunityBinding.postList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int previousCount = mCommunityViewModel.getSocialActivities.size();
                if (!mCommunityViewModel.loading.get()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mCommunityViewModel.getSocialActivities.size() - 1) {
                        //bottom of list!
                       /* loadMore();
                        isLoading = true;*/

                        if (pagingResult != null && pagingQuery != null)
                            if (!pagingResult.isLastPage()) {
                                pagination++;
                                mCommunityViewModel.loading.set(true);
                                Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                    pagingResult = result;
                                    final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                    mCommunityViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                    mCommunityViewModel.loading.set(false);

                                    mFragmentCommunityBinding.postList.scrollToPosition(previousCount + 1);


                                }, exception -> {
                                    mCommunityViewModel.loading.set(false);
                                    // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                });


                            }
                    }
                }
            }
        });
        /*   ViewCompat.setNestedScrollingEnabled(  mFragmentCommunityBinding.postList, false);*/
    /*
        mFragmentCommunityBinding.content.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                }
                if ((i >= (mFragmentCommunityBinding.content.getChildAt(mFragmentCommunityBinding.content.getChildCount() - 1).getMeasuredHeight() - mFragmentCommunityBinding.content.getMeasuredHeight())) &&
                        i > i3) {

                    int previousCount=mCommunityViewModel.getSocialActivities.size();

                    if (mCommunityViewModel.getSocialActivities.size() > 20) {
                        if (!mCommunityViewModel.loading.get()) {
                            //  productListAdapter.addLoader();
                            if (pagingResult != null && pagingQuery != null)
                                if (!pagingResult.isLastPage()) {
                                    mCommunityViewModel.loading.set(true);
                                    Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                        pagingResult = result;
                                        final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                        mCommunityViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                        mCommunityViewModel.loading.set(false);

                                        mFragmentCommunityBinding.postList.scrollToPosition(previousCount+1);


                                    }, exception -> {
                                        mCommunityViewModel.loading.set(false);
                                        // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                    });


                                }
                        }
                    }

                }

            }


        });

*/
        /*mFragmentCommunityBinding.content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                }
                if ((scrollY >= (mFragmentCommunityBinding.content.getChildAt(mFragmentCommunityBinding.content.getChildCount() - 1).getMeasuredHeight() - mFragmentCommunityBinding.content.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {

                    int previousCount=mCommunityViewModel.getSocialActivities.size();

                    if (mCommunityViewModel.getSocialActivities.size() > 5) {
                        if (!mCommunityViewModel.loading.get()) {
                            //  productListAdapter.addLoader();
                            if (pagingResult != null && pagingQuery != null)
                                if (!pagingResult.isLastPage()) {
                                    mCommunityViewModel.loading.set(true);
                                    Communities.getActivities(pagingQuery.next(pagingResult.nextCursor()), result -> {
                                        pagingResult = result;
                                        final List<GetSocialActivity> getSocialActivities = result.getEntries();
                                        mCommunityViewModel.socialActivitiesListLiveData.postValue(getSocialActivities);
                                        mCommunityViewModel.loading.set(false);

                                        mFragmentCommunityBinding.postList.scrollToPosition(previousCount+1);


                                    }, exception -> {
                                        mCommunityViewModel.loading.set(false);
                                        // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
                                    });


                                }
                        }
                    }

                }


            }
        });*/

      /*  Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_COMMUNITY);*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void subscribeToLiveData() {
        mCommunityViewModel.getsocialActivitiesListLiveData().observe(this,
                postItemViewModel -> mCommunityViewModel.addCommunityPostToList(postItemViewModel));
    }

    private void showRecentPosts() {


        //   final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(_item.getId()).byUser(UserId.currentUser());
        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(mCommunityViewModel.homeEventTopic);
        // final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic("community_event");


        //  ActivityFeedViewBuilder.create(query).show();


        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(25);
        Communities.getActivities(pagingQuery, result -> {
            this.pagingQuery = pagingQuery;
            this.pagingResult = result;
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            //mCommunityViewModel.getSocialActivities.addAll(getSocialActivities);

            //Get first post
           /* if (result.getEntries() != null && result.getEntries().size() > 0) {
                firstPost = result.getEntries().get(0);
                mCommunityViewModel.postTitle.set(firstPost.getSource().getTitle());
                mCommunityViewModel.postDes.set(firstPost.getText());
                mCommunityViewModel.postDate.set(getDate(firstPost.getCreatedAt()));
                if (firstPost.getButton() != null) {
                    mCommunityViewModel.actionText.set(firstPost.getButton().getTitle());
                    mCommunityViewModel.showAction.set(firstPost.getButton().getAction() != null);

                }

                mCommunityViewModel.icon.set(firstPost.getAuthor().getAvatarUrl());

                //   String ssss=firstPost.getMyReactions().

                if (firstPost.getMyReactions() != null) {

                    List<String> list = new ArrayList<>(firstPost.getMyReactions());

                    if (list != null && list.size() > 0) {

                        if (list.get(0).equals("like"))
                            mCommunityViewModel.postLike.set(true);

                    }
                }


                if (firstPost.getAttachments() != null)
                    if (firstPost.getAttachments().size() > 0) {
                        mCommunityViewModel.attachmentAvailable.set(true);
                        for (int i = 0; i < firstPost.getAttachments().size(); i++) {
                            final MediaAttachment attachment = firstPost.getAttachments().get(i);
                            if (attachment.getImageUrl() != null) {
                                if (i == 0) {
                                    mCommunityViewModel.singleImage.set(true);
                                    mCommunityViewModel.image1.set(attachment.getImageUrl());
                                } else {
                                    mCommunityViewModel.singleImage.set(false);
                                    mCommunityViewModel.image2.set(attachment.getImageUrl());
                                }
                            }
                        }
                    } else {
                        mCommunityViewModel.attachmentAvailable.set(false);
                    }


            }*/

           /* if (getSocialActivities.size() > 0) {
                getSocialActivities.remove(0);
            }*/

            getSocialActivities.add(0, result.getEntries().get(0));
            getSocialActivities.add(2, result.getEntries().get(0));

            mCommunityViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);

            mCommunityViewModel.categoryLoading.set(false);
        }, exception -> {
            mCommunityViewModel.categoryLoading.set(false);
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void actionData(Map<String, String> actionDatas, int position, int type) {


        //TYPE 1 = POST
        //TYPE 2 = 5 tiles

        if (type == 2) {

            if (mCommunityViewModel.communityUser.get()) {
                new Analytics().eventTractionDLEBannerTile(getContext(), position);
            } else {
                new Analytics().eventTractionNonDLEBannerTile(getContext(), position);
            }
        }else {
            new Analytics().eventFeedTractionOnHomePage(getContext(),AppConstants.EVENT_CLICK );
        }
        saveHomeCloseAnalytics();

        String pageid = actionDatas.get("pageid");
        switch (pageid) {

            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                Intent intent = CategoryL1Activity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST);
                intent.putExtra("catid", actionDatas.get("catid"));
                startActivity(intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                Intent l2intent = CategoryL2Activity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST, AppConstants.SCREEN_NAME_SUB_CATEGORY_L2_PRODUCTS);
                l2intent.putExtra("catid", actionDatas.get("catid"));
                l2intent.putExtra("scl1id", actionDatas.get("scl1id"));
                startActivity(l2intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                Intent catintent = CatProductActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_VIEW_ALL_PRODUCTS);
                catintent.putExtra("catid", actionDatas.get("catid"));
                startActivity(catintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:

                ((MainActivity) getActivity()).openCommunityCat();

                break;


            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                Intent tLintent = TransactionHistoryActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.SCREEN_NAME_TRANSACTION);
                startActivity(tLintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                Intent tDintent = TransactionDetailsActivity.newIntent(getBaseActivity(),AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.SCREEN_NAME_TRANS_DETAILS);
                tDintent.putExtra("orderid", actionDatas.get("orderid"));
                startActivity(tDintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                Intent pintent = ProductDetailsActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
                pintent.putExtra("vpid", actionDatas.get("vpid"));
                startActivity(pintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                Intent cintent = CollectionDetailsActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_COLLECTION_DETAIL);
                cintent.putExtra("cid", actionDatas.get("cid"));
                startActivity(cintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            case AppConstants.NOTIFY_ABOUT_US_ACTV:
                Intent auintent = AboutUsActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_ABOUT_US, AppConstants.SCREEN_NAME_COMMUNITY);
                startActivity(auintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case AppConstants.NOTIFY_VIDEO_ACTV:
                Intent vintent = VideoActivity.newIntent(getBaseActivity(), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_VIDEO);
                vintent.putExtra("video", actionDatas.get("video"));
                startActivity(vintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            case AppConstants.NOTIFY_WHATSAPP:

                if (mCommunityViewModel.getDataManager().getUserDetails() != null) {
                    Gson sGson = new GsonBuilder().create();
                    CommunityUserDetailsResponse communityUserDetailsResponse = sGson.fromJson(mCommunityViewModel.getDataManager().getUserDetails(), CommunityUserDetailsResponse.class);
                    if (communityUserDetailsResponse != null) {
                        if (communityUserDetailsResponse.getResult() != null) {
                            if (communityUserDetailsResponse.getResult().size() > 0) {
                                CommunityUserDetailsResponse.Result result = communityUserDetailsResponse.getResult().get(0);

                                if (result.getCommunityStatus() != null) {
                                    String url = result.getGroupUrl();
                                    if (url != null)
                                        if (!url.isEmpty())
                                            try {
                                                Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                                                //String url = "https://chat.whatsapp.com/<group_link>";
                                                intentWhatsapp.setData(Uri.parse(url));
                                                intentWhatsapp.setPackage("com.whatsapp");
                                                startActivity(intentWhatsapp);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                }


                            }
                        }

                    }

                }


                break;

            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:

                Intent eintent = EventActivity.newIntent(getContext(), actionDatas.get("topic"), actionDatas.get("title"), AppConstants.SCREEN_NAME_COMMUNITY, AppConstants.SCREEN_NAME_POST_DETAILS);
                startActivity(eintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;


            default:
                Intent sintent = SplashActivity.newIntent(getBaseActivity());
                startActivity(sintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                //mActivityOnboardingBinding.flagCameraOrUpload.set(true);
                if (data != null) {
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    Uri selectedImage = result.getUri();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    assert imageBitmap != null;
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    //mActivityOnboardingBinding.imgJoin.setImageBitmap(null);
                    //mActivityOnboardingBinding.imgJoin.setImageBitmap(imageBitmap);

                    Bitmap bitmap = imageBitmap;
                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                    BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    paint.setAntiAlias(true);
                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                    //mActivityOnboardingBinding.imgJoin.setBackgroundResource(0);
                    //mActivityOnboardingBinding.imgJoin.setImageBitmap(circleBitmap);


                    //mActivityOnboardingBinding.flagCameraOrUpload.set(true);
                    mCommunityViewModel.uploadImage(imageBitmap);
                    //mOnBoardingActivityViewModel.flagRemovePicJoin.set(true);
                    //mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("area", "area");
                communityPostListAdapter.notifyItemChanged(0);
            }
        }

    }


}
