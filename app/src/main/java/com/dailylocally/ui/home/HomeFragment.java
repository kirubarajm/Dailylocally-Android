package com.dailylocally.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentHomeBinding;
import com.dailylocally.ui.address.viewAddress.ViewAddressActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.joinCommunity.CommunityActivity;
import com.dailylocally.ui.joinCommunity.contactWhatsapp.ContactWhatsAppActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.promotion.bottom.PromotionFragment;
import com.dailylocally.ui.rating.RatingActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.utilities.AppConstants;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, CategoriesAdapter.CategoriesAdapterListener, InstallStateUpdatedListener, OnSuccessListener<AppUpdateInfo> {
    private static final int RC_APP_UPDATE = 55669;
    @Inject
    CategoriesAdapter categoriesAdapter;
    @Inject
    HomeViewModel mHomeViewModel;
    FragmentHomeBinding mFragmentHomeBinding;
    boolean downloading;
    AppUpdateManager appUpdateManager;
    AppUpdateInfo appUpdateInfo;
    GridLayoutManager gridLayoutManager;
    VideoView videoView;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        return mHomeViewModel;
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
                if (mHomeViewModel.categoryList.get(position).getTileType().equals("2")) {
                    return 2;
                } else return 1;
            }
        });
        mFragmentHomeBinding.categoryList.setLayoutManager(gridLayoutManager);
        categoriesAdapter = new CategoriesAdapter(mHomeViewModel.categoryList);
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
        Intent intent = ViewAddressActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void changeHeaderText(String headerContent) {
        mFragmentHomeBinding.welcomeText.setText(Html.fromHtml(headerContent));
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
    public void searchClick() {
        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/
        ((MainActivity) getActivity()).openExplore(true);
    }

    @Override
    public void ratingClick() {
        Intent intent = RatingActivity.newIntent(getContext());
        intent.putExtra("doid", mHomeViewModel.ratingDOID);
        startActivityForResult(intent, AppConstants.RATING_REQUEST_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel.setNavigator(this);
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

        mHomeViewModel.fetchCategoryList();
        mHomeViewModel.getPromotions();
        mHomeViewModel.getRatings();

        appUpdateManager = AppUpdateManagerFactory.create(getContext());

        if (mHomeViewModel.updateAvailable.get())
            updatePopup(getString(R.string.update_available), getString(R.string.update), false, true, false);





    }

    @Override
    public void onResume() {
        mHomeViewModel.updateAddressTitle();
        appUpdateManager.registerListener(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(this);

        if (videoView!=null)
            videoView.start();

        super.onResume();
    }

    private void subscribeToLiveData() {
        mHomeViewModel.getCategoryListLiveData().observe(this,
                catregoryItemViewModel -> mHomeViewModel.addCategoryToList(catregoryItemViewModel));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void categoryItemClicked(HomepageResponse.Result result, TextView view, VideoView videoView) {


        /*if (videoView!=null){

            this.videoView=videoView;
            if (videoView.isPlaying())
                videoView.pause();
        }*/


        if (result.getType()==1){

            Intent intent = CategoryL1Activity.newIntent(getBaseActivity());
            intent.putExtra("catid", String.valueOf(result.getCatid()));
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        }else if (result.getType()==2){

            Intent intent = CollectionDetailsActivity.newIntent(getContext());
            intent.putExtra("cid", result.getCid());
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }else {
            Intent inIntent = CommunityOnBoardingActivity.newIntent(getContext());
            inIntent.putExtra("newuser", false);
            startActivity(inIntent);
           getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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


        mHomeViewModel.updateAvailable.set(isUpdate);
        mHomeViewModel.updateTitle.set(message);
        mHomeViewModel.updateAction.set(action);
        mHomeViewModel.enableLater.set(forceUpdate);
        mHomeViewModel.update.set(isUpdate);





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
                        mHomeViewModel.updateAvailable.set(false);
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
                        mHomeViewModel.updateAvailable.set(false);
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
                mHomeViewModel.updateAvailable.set(false);
                mHomeViewModel.update.set(false);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.RATING_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mHomeViewModel.showRating.set(false);
            }

        }
    }
}
