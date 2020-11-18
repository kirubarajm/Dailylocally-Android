package com.dailylocally.ui.community.catlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCommunityCatBinding;
import com.dailylocally.ui.address.type.CommunitySearchActivity;
import com.dailylocally.ui.address.viewAddress.ViewAddressActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.home.CategoriesAdapter;
import com.dailylocally.ui.home.HomepageResponse;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.promotion.bottom.PromotionFragment;
import com.dailylocally.ui.rating.RatingActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import static com.dailylocally.utilities.AppConstants.STORAGE_PERMISSION_REQUEST_CODE;

public class CommunityCatFragment extends BaseFragment<FragmentCommunityCatBinding, CommunityCatViewModel> implements CommunityCatNavigator, CategoriesAdapter.CategoriesAdapterListener, InstallStateUpdatedListener, OnSuccessListener<AppUpdateInfo> {
    private static final int RC_APP_UPDATE = 55669;
    @Inject
    CategoriesAdapter categoriesAdapter;
    @Inject
    CommunityCatViewModel mCommunityCatViewModel;
    FragmentCommunityCatBinding mFragmentHomeBinding;
    boolean downloading;
    AppUpdateManager appUpdateManager;
    AppUpdateInfo appUpdateInfo;
    GridLayoutManager gridLayoutManager;
    Bitmap imageBitmap;
    VideoView videoView;
    public static CommunityCatFragment newInstance(String fromPage, String toPage) {
        Bundle args = new Bundle();
        CommunityCatFragment fragment = new CommunityCatFragment();
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.communityCatViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_community_cat;
    }

    @Override
    public CommunityCatViewModel getViewModel() {
        return mCommunityCatViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void dataLoaded() {
        gridLayoutManager = new GridLayoutManager(getBaseActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mCommunityCatViewModel.categoryList.get(position).getTileType().equals("2")) {
                    return 2;
                } else return 1;
            }
        });
        mFragmentHomeBinding.categoryList.setLayoutManager(gridLayoutManager);
        categoriesAdapter = new CategoriesAdapter(mCommunityCatViewModel.categoryList);
        mFragmentHomeBinding.categoryList.setAdapter(categoriesAdapter);
        categoriesAdapter.setListener(this);
        // mFragmentHomeBinding.loader.stop();
        mFragmentHomeBinding.loader.stopShimmerAnimation();

    }

    @Override
    public void dataLoading() {

        //  mFragmentHomeBinding.loader.start();
        mFragmentHomeBinding.loader.startShimmerAnimation();

    }

    @Override
    public void gotoOrders() {

        ((MainActivity) getActivity()).openOrders();


    }

    @Override
    public void changeAddress() {
        /*Intent intent = GoogleAddressActivity.newIntent(getContext());
        intent.putExtra("edit", "1");
        startActivity(intent);*/
        Intent intent = ViewAddressActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_MY_ACCOUNT,AppConstants.SCREEN_NAME_ADDRESS_VIEW);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void changeHeaderText(String headerContent) {
        //  mFragmentHomeBinding.welcomeText.setText(Html.fromHtml(headerContent));
    }

    @Override
    public void showPromotions(String url, boolean fullScreen, int type, int promotionid) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PROMOTION_TYPE, type);
        bundle.putInt(AppConstants.PROMOTION_ID, promotionid);
        bundle.putString(AppConstants.PROMOTION_URL, url);
        bundle.putString(AppConstants.FROM,AppConstants.SCREEN_NAME_COMMUNITY);
        bundle.putString(AppConstants.PAGE,AppConstants.SCREEN_NAME_PROMOTION);

        PromotionFragment bottomSheetFragment = new PromotionFragment();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());

    }

    @Override
    public void searchClick() {
        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/
        ((MainActivity) getActivity()).openExplore(false);
    }

    @Override
    public void ratingClick() {
        Intent intent = RatingActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_CALENDAR,AppConstants.SCREEN_NAME_RATING);
        intent.putExtra("doid", mCommunityCatViewModel.ratingDOID);
        startActivityForResult(intent, AppConstants.RATING_REQUEST_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunityCatViewModel.setNavigator(this);
        subscribeToLiveData();

    }

    @Override
    public void onPause() {
        if (videoView!=null)
            videoView.pause();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();
        // mFragmentHomeBinding.loader.start();
        mFragmentHomeBinding.loader.startShimmerAnimation();

        mCommunityCatViewModel.fetchCategoryList();
        mCommunityCatViewModel.getPromotions();
        mCommunityCatViewModel.getRatings();

        appUpdateManager = AppUpdateManagerFactory.create(getContext());

        if (mCommunityCatViewModel.updateAvailable.get())
            updatePopup(getString(R.string.update_available), getString(R.string.update), false, true, false);


        /*Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_CATEGORY_PAGE);*/

    }

    @Override
    public void onResume() {
        if (videoView!=null)
            videoView.start();
        mCommunityCatViewModel.updateAddressTitle();
        appUpdateManager.registerListener(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(this);
        super.onResume();
    }

    private void subscribeToLiveData() {
        mCommunityCatViewModel.getCategoryListLiveData().observe(this,
                catregoryItemViewModel -> mCommunityCatViewModel.addCategoryToList(catregoryItemViewModel));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void categoryItemClicked(HomepageResponse.Result result, TextView view, VideoView videoView,int pos) {

       /* if (result.getCollectionStatus()) {
            Intent intent = CollectionDetailsActivity.newIntent(getContext());
            intent.putExtra("cid", result.getCid());
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else {
            Intent intent = CategoryL1Activity.newIntent(getBaseActivity());
            intent.putExtra("catid", String.valueOf(result.getCatid()));
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }*/



     //   this.videoView=videoView;
        if (videoView.isPlaying())
            videoView.pause();

        String type = "";
        if (result.getTileType().equals("1")){
            type = "vertical";
        }else if (result.getTileType().equals("2")){
            type = "horizontal";
        }

        if (result.getType()==1){

            new Analytics().eventCategoryTile(getContext(),result.getName(),type,String.valueOf(pos));

            Intent intent = CategoryL1Activity.newIntent(getBaseActivity(),AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST);
            intent.putExtra("catid", String.valueOf(result.getCatid()));
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



        }else if (result.getType()==2){

            new Analytics().eventCollectionTile(getContext(),result.getName(),type,String.valueOf(pos),result.getCid());

            Intent intent = CollectionDetailsActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_COMMUNITY_CAT_LIST,AppConstants.SCREEN_NAME_COLLECTION_DETAIL);
            intent.putExtra("cid", result.getCid());
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }else {


            new Analytics().eventDLEregistrationPageOnCategoryPage(getContext());

            if (mCommunityCatViewModel.getDataManager().isCommunityOnboardSeen()){
                Intent inIntent = CommunitySearchActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.URL_COMMUNITY_SEARCH);
                inIntent.putExtra("newuser", false);
                inIntent.putExtra("lat", mCommunityCatViewModel.getDataManager().getCurrentLat());
                inIntent.putExtra("lng", mCommunityCatViewModel.getDataManager().getCurrentLng());
                startActivityForResult(inIntent, AppConstants.SELECT_COMMUNITY_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }else {
                Intent inIntent = CommunityOnBoardingActivity.newIntent(getContext());
                inIntent.putExtra("next", false);
                startActivity(inIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }





           /* Intent inIntent = CommunityOnBoardingActivity.newIntent(getContext());
            inIntent.putExtra("newuser", false);
            startActivity(inIntent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            /*if (result.getJoinStatus()&&!result.getApprovalStatus()) {
                Intent intent = ContactWhatsAppActivity.newIntent(getContext());
                startActivity(intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {

                Intent intent = CommunityActivity.newIntent(getContext());
                startActivity(intent);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }*/

        }

        /*if (result.getCollectionStatus()) {
            Intent intent = CollectionDetailsActivity.newIntent(getContext());
            intent.putExtra("cid", result.getCid());
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else {
            Intent intent = CategoryL1Activity.newIntent(getBaseActivity());
            intent.putExtra("catid", String.valueOf(result.getCatid()));
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }*/


    }

    @Override
    public void updateVideoView(VideoView videoView) {
        this.videoView=videoView;
    }

    @Override
    public void onStateUpdate(InstallState installState) {
        int installStatus = installState.installStatus();
        switch (installStatus) {
            case InstallStatus.DOWNLOADING:
                // updateTextView.setText(R.string.downloading_text);
                //  updateButton.setVisibility(View.INVISIBLE);
                if (downloading) {
                    updatePopup("Downloading...", "", false, false, false);
                    downloading = false;
                }


                break;
            case InstallStatus.DOWNLOADED:
                //   updateTextView.setText(R.string.downloaded_text);
                //  updateButton.setVisibility(View.INVISIBLE);
                //   aboutUsText.setText(R.string.success_text);
                //  placeholderText.setText(R.string.flexible_update_downloaded);
                //  popupSnackbarForCompleteUpdate();
                downloading = false;
                updatePopup(getString(R.string.download_completed), getString(R.string.install), false, true, false);

                break;
            case InstallStatus.FAILED:
                //   updateTextView.setText(R.string.download_failed_text);
                updatePopup(getString(R.string.download_failed), getString(R.string.retry), true, true, false);
                break;
            case InstallStatus.CANCELED:
                //  updateTextView.setText(R.string.download_canceled_text);

                break;
        }
    }

    @Override
    public void onSuccess(AppUpdateInfo appUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo;
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            // If the update is downloaded but not installed,
            // notify the user to complete the update.
            //   popupSnackbarForCompleteUpdate();

            //  updatePopup(getString(R.string.download_completed),getString(R.string.install),false);

        } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

            /*if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                Toast.makeText(this, "flexible", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "update available", Toast.LENGTH_SHORT).show();
            }*/

            /* && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)*/
            //show flexible update available UI and on response from that, start flexible update

            //   updatePopup(getString(R.string.update_available), getString(R.string.update), false, true);
            this.appUpdateInfo = appUpdateInfo;
        } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADING) {


        }
    }

    private void startUpdate(AppUpdateInfo appUpdateInfo, int appUpdateType) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                    appUpdateType,
                    getBaseActivity(),
                    RC_APP_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void updatePopup(String message, String action, boolean error, boolean isUpdate, boolean forceUpdate) {


        mCommunityCatViewModel.updateAvailable.set(isUpdate);
        mCommunityCatViewModel.updateTitle.set(message);
        mCommunityCatViewModel.updateAction.set(action);
        mCommunityCatViewModel.enableLater.set(forceUpdate);
        mCommunityCatViewModel.update.set(isUpdate);





       /* if (error) {
            mFragmentHomeBinding.action.setTextColor(getResources().getColor(R.color.red));
        } else {
            mFragmentHomeBinding.action.setTextColor(getResources().getColor(R.color.dl_green));
        }*/

        mFragmentHomeBinding.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (action.equals(getString(R.string.install))) {

                    appUpdateManager.completeUpdate();

                } else if (action.equals(getString(R.string.update))) {

                    if (appUpdateInfo != null && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                        downloading = true;
                        mCommunityCatViewModel.updateAvailable.set(false);
                    } else {
                        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        //   finish();


                        ((MainActivity) getActivity()).finishActivity();

                    }

                } else if (action.isEmpty()) {
                    //  startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                } else if (action.equals(getString(R.string.retry))) {
                    if (appUpdateInfo != null && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                        downloading = true;
                        mCommunityCatViewModel.updateAvailable.set(false);
                    } else {
                        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        ((MainActivity) getActivity()).finishActivity();
                    }

                } else {
                    return;
                }

            }
        });
        mFragmentHomeBinding.later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommunityCatViewModel.updateAvailable.set(false);
                mCommunityCatViewModel.update.set(false);
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.RATING_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mCommunityCatViewModel.showRating.set(false);
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
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
                    mCommunityCatViewModel.uploadImage(imageBitmap);
                    //mOnBoardingActivityViewModel.flagRemovePicJoin.set(true);
                    //mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("area", "area");
            }
        }
    }


}
