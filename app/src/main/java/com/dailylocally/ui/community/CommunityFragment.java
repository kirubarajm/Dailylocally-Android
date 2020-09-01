package com.dailylocally.ui.community;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCommunityBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.promotion.bottom.PromotionFragment;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.utilities.AppConstants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import im.getsocial.sdk.Communities;
import im.getsocial.sdk.GetSocial;
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.media.MediaAttachment;
import im.getsocial.sdk.ui.communities.ActivityFeedViewBuilder;

public class CommunityFragment extends BaseFragment<FragmentCommunityBinding, CommunityViewModel> implements CommunityNavigator {
    private static final int RC_APP_UPDATE = 55669;


    @Inject
    CommunityPostListAdapter communityPostListAdapter;
    @Inject
    CommunityViewModel mCommunityViewModel;

    FragmentCommunityBinding mFragmentCommunityBinding;

    public static CommunityFragment newInstance() {
        Bundle args = new Bundle();
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

        PromotionFragment bottomSheetFragment = new PromotionFragment();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());

    }

    @Override
    public void gotoCommunityHome() {
        ((MainActivity) getActivity()).openCommunityCat();
    }

    @Override
    public void whatsAppGroup() {

        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
        //String url = "https://chat.whatsapp.com/<group_link>";
        String url = mCommunityViewModel.whatsappgroupLink;
        intentWhatsapp.setData(Uri.parse(url));
        intentWhatsapp.setPackage("com.whatsapp");
        startActivity(intentWhatsapp);

    }

    @Override
    public void sneakPeak() {
        if (mCommunityViewModel.sneakpeakVideoUrl != null) {
            mCommunityViewModel.showVideo.set(true);
            mFragmentCommunityBinding.videoPlayer.setSource(mCommunityViewModel.sneakpeakVideoUrl);
         //   mFragmentCommunityBinding.videoPlayer.setSource("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
       }
    }

    @Override
    public void aboutUs() {

        Intent intent = FaqActivity.newIntent(getContext());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void communityEvent() {
        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic("test");
        ActivityFeedViewBuilder.create(query).show();
    }

    @Override
    public void stopVideo() {
        mFragmentCommunityBinding.videoPlayer.stopPlayer();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunityViewModel.setNavigator(this);
        subscribeToLiveData();


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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentCommunityBinding.postList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentCommunityBinding.postList.setAdapter(communityPostListAdapter);


        GetSocial.addOnInitializeListener(new Runnable() {
            @Override
            public void run() {
                // GetSocial is ready to be used

                showRecentPosts();

            }
        });
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
        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic("test");


      //  ActivityFeedViewBuilder.create(query).show();


        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(5);
        Communities.getActivities(pagingQuery, result -> {
            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            mCommunityViewModel.getSocialActivities.addAll(getSocialActivities);
            //Get first post
            if (getSocialActivities != null && getSocialActivities.size() > 0) {
                GetSocialActivity firstPost = getSocialActivities.get(0);
                mCommunityViewModel.postTitle.set(firstPost.getAuthor().getDisplayName());
                mCommunityViewModel.postDes.set(firstPost.getText());
                mCommunityViewModel.postDate.set(getDate(firstPost.getCreatedAt()));
                mCommunityViewModel.actionText.set(firstPost.getButton().getTitle());
                mCommunityViewModel.showAction.set(firstPost.getButton().getAction() != null);
                mCommunityViewModel.icon.set(firstPost.getAuthor().getAvatarUrl());

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


            }


        }, exception -> {
            // _log.logErrorAndToast("Failed to load activities, error: " + exception.getMessage());
        });
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return formateddate(date);
    }
}
