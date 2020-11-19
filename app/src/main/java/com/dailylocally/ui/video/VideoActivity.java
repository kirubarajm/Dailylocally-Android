package com.dailylocally.ui.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityVideoBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;


public class VideoActivity extends BaseActivity<ActivityVideoBinding, VideoViewModel> implements VideoNavigator {

    @Inject
    VideoViewModel mVideoViewModel;
    ActivityVideoBinding mActivityVideoBinding;
    int lastPosition = 0;
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

    public static Intent newIntent(Context context,String fromPage,String ToPage) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivityVideoBinding.videoPlayer.stopPlayback();

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

            mActivityVideoBinding.progressBar.setVisibility(View.VISIBLE);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mActivityVideoBinding.videoPlayer);
            mActivityVideoBinding.videoPlayer.setMediaController(mediaController);

            mActivityVideoBinding.videoPlayer.setVideoURI(Uri.parse(bundle.getString("video")));

            mActivityVideoBinding.videoPlayer.start();


            mActivityVideoBinding.videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mActivityVideoBinding.progressBar.setVisibility(View.GONE);
                }
            });
            mActivityVideoBinding.videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    finish();
                }

            });

        }
        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_VIDEO);

    }


    @Override
    protected void onPause() {
        super.onPause();

        lastPosition = mActivityVideoBinding.videoPlayer.getCurrentPosition();
        mActivityVideoBinding.videoPlayer.pause();


        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mActivityVideoBinding.progressBar.setVisibility(View.VISIBLE);
        mActivityVideoBinding.videoPlayer.seekTo(lastPosition);
        mActivityVideoBinding.videoPlayer.start();

        registerWifiReceiver();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityVideoBinding.videoPlayer.stopPlayback();
    }

    @Override
    public void canceled() {

    }
}
