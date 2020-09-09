package com.dailylocally.ui.joinCommunity.viewProfilePic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityViewPhotoBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;


public class ViewPhotoActivity extends BaseActivity<ActivityViewPhotoBinding, ViewPhotoViewModel> implements ViewPhotoNavigator {

    @Inject
    ViewPhotoViewModel mOnBoardingActivityViewModel;
    Analytics analytics;
    String pageName = "contact WhatsApp";
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    private ActivityViewPhotoBinding mActivityViewPhotoBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ViewPhotoActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void edit() {
        finish();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_JOIN);
    }

    @Override
    public void remove() {

    }

    @Override
    public int getBindingVariable() {
        return BR.viewPhotoViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_photo;
    }

    @Override
    public ViewPhotoViewModel getViewModel() {
        return mOnBoardingActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewPhotoBinding = getViewDataBinding();
        mOnBoardingActivityViewModel.setNavigator(this);
        String strUrl = "";
        Bundle  bundle = getIntent().getExtras();
        if (bundle!=null){
            strUrl = bundle.getString("imgurl");
        }
        Glide.with(getApplicationContext()).load(strUrl).placeholder(null).centerCrop()
                .error(R.drawable.ic_group_482).into(mActivityViewPhotoBinding.imgPreview);
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
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

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    @Override
    public void canceled() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
