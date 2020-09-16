package com.dailylocally.ui.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityVideoBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.halilibo.bvpkotlin.captions.CaptionsView;

import java.net.URI;

import javax.inject.Inject;


public class VideoActivity extends BaseActivity<ActivityVideoBinding, VideoViewModel> implements VideoNavigator {

    @Inject
    VideoViewModel mVideoViewModel;
    ActivityVideoBinding mActivityVideoBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_PLACED;
    int lastPosition=0;
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
    public static Intent newIntent(Context context) {
        return new Intent(context, VideoActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void back() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivityVideoBinding.videoPlayer.stop();

        finish();
    }

    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.videoViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public VideoViewModel getViewModel() {
        return mVideoViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoViewModel.setNavigator(this);
        mActivityVideoBinding = getViewDataBinding();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            /*mActivityVideoBinding.videoPlayer.setSource( bundle.getString("video"));
            mActivityVideoBinding.videoPlayer.setPlayWhenReady(true);*/


           // mActivityVideoBinding.videoPlayer.setCaptions(bundle.getString("video"), CaptionsView.SubMime.SUBRIP); ;
            mActivityVideoBinding.videoPlayer.start();


           // mActivityVideoBinding.videoPlayer.setOrientation(LinearLayout.HORIZONTAL);


        }





    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mActivityVideoBinding.videoPlayer!=null){
            lastPosition= mActivityVideoBinding.videoPlayer.getCurrentPosition();
            mActivityVideoBinding.videoPlayer.pause();
        }

        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (mActivityVideoBinding.videoPlayer!=null){
            mActivityVideoBinding.videoPlayer.start();
            mActivityVideoBinding.videoPlayer.seekTo(lastPosition);
        }

        registerWifiReceiver();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityVideoBinding.videoPlayer.stop();
    }

    @Override
    public void canceled() {

    }
}
