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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCommunityBinding;
import com.dailylocally.ui.aboutus.AboutUsActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.promotion.bottom.PromotionFragment;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.ui.video.VideoActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import im.getsocial.sdk.common.PagingQuery;
import im.getsocial.sdk.communities.ActivitiesQuery;
import im.getsocial.sdk.communities.CurrentUser;
import im.getsocial.sdk.communities.GetSocialActivity;
import im.getsocial.sdk.communities.Identity;
import im.getsocial.sdk.communities.Reactions;
import im.getsocial.sdk.communities.UserUpdate;
import im.getsocial.sdk.media.MediaAttachment;

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
    }

    @Override
    public void sneakPeak() {
        if (mCommunityViewModel.sneakpeakVideoUrl != null) {

            Intent tDintent = VideoActivity.newIntent(getBaseActivity());
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

        Intent intent = AboutUsActivity.newIntent(getContext());
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

        Intent intent = EventActivity.newIntent(getContext(), mCommunityViewModel.topic, mCommunityViewModel.eventTitle);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void stopVideo() {
        //  mFragmentCommunityBinding.videoPlayer.stopPlayer();
    }

    @Override
    public void actionBtClick() {

        actionData(firstPost.getButton().getAction().getData());
    }

    @Override
    public void postLikeClick() {


        if (mCommunityViewModel.postLike.get()) {
            Communities.removeReaction(Reactions.LIKE, firstPost.getId(), new CompletionCallback() {
                @Override
                public void onSuccess() {
                    mCommunityViewModel.postLike.set(false);
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
        }

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


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunityViewModel.setNavigator(this);
        communityPostListAdapter.setListener(this);
        subscribeToLiveData();


        createGetSocialIdentity();


    }

    public void createGetSocialIdentity() {

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentCommunityBinding.postList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentCommunityBinding.postList.setAdapter(communityPostListAdapter);


        mFragmentCommunityBinding.content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                }
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
        final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic(mCommunityViewModel.homeEventTopic);
        // final ActivitiesQuery query = ActivitiesQuery.activitiesInTopic("community_event");


        //  ActivityFeedViewBuilder.create(query).show();


        final PagingQuery<ActivitiesQuery> pagingQuery = new PagingQuery<>(query).withLimit(100);
        Communities.getActivities(pagingQuery, result -> {

            final List<GetSocialActivity> getSocialActivities = result.getEntries();
            //mCommunityViewModel.getSocialActivities.addAll(getSocialActivities);
            mCommunityViewModel.socialActivitiesListLiveData.setValue(getSocialActivities);
            //Get first post
            if (getSocialActivities != null && getSocialActivities.size() > 0) {
                firstPost = getSocialActivities.get(0);
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


            }

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
    public void actionData(Map<String, String> actionDatas) {


        String pageid = actionDatas.get("pageid");
        switch (pageid) {

            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                Intent intent = CategoryL1Activity.newIntent(getBaseActivity());
                intent.putExtra("catid", actionDatas.get("catid"));
                startActivity(intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                Intent l2intent = CategoryL2Activity.newIntent(getBaseActivity());
                l2intent.putExtra("catid", actionDatas.get("catid"));
                l2intent.putExtra("scl1id", actionDatas.get("scl1id"));
                startActivity(l2intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                Intent catintent = CatProductActivity.newIntent(getBaseActivity());
                catintent.putExtra("catid", actionDatas.get("catid"));
                startActivity(catintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:

                ((MainActivity) getActivity()).openCommunityCat();

                break;


            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                Intent tLintent = TransactionHistoryActivity.newIntent(getContext());
                startActivity(tLintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                Intent tDintent = TransactionDetailsActivity.newIntent(getBaseActivity());
                tDintent.putExtra("orderid", actionDatas.get("orderid"));
                startActivity(tDintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                Intent pintent = ProductDetailsActivity.newIntent(getBaseActivity());
                pintent.putExtra("vpid", actionDatas.get("vpid"));
                startActivity(pintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                Intent cintent = CollectionDetailsActivity.newIntent(getBaseActivity());
                cintent.putExtra("cid", actionDatas.get("cid"));
                startActivity(cintent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


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
            }
        }

    }


}
