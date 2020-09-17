package com.dailylocally.ui.video;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityVideoBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;


public class VideoActivity extends BaseActivity<ActivityVideoBinding, VideoViewModel> implements VideoNavigator {

    @Inject
    VideoViewModel mVideoViewModel;
    ActivityVideoBinding mActivityVideoBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_PLACED;
    int lastPosition = 0;
    Boolean isInPipMode = false;
    String mUrl;
    SimpleExoPlayer player;
    Long videoPosition = 0L;
    Boolean isPIPModeeEnabled = true; //Has the user disabled PIP mode in AppOpps?
    Boolean returnResultOnce = true;
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
        return new Intent(context, VideoActivity.class);
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
        //  mActivityVideoBinding.videoPlayer.stopPlayback();

        finish();
    }

    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
                && isPIPModeeEnabled) {
            enterPIPMode();
        } else {
            super.onBackPressed();
        }

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

            mUrl = bundle.getString("video");

            /*mActivityVideoBinding.videoPlayer.setSource( bundle.getString("video"));
            mActivityVideoBinding.videoPlayer.setPlayWhenReady(true);*/
           /* mActivityVideoBinding.progressBar.setVisibility(View.VISIBLE);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mActivityVideoBinding.videoPlayer);
            mActivityVideoBinding.videoPlayer.setMediaController(mediaController);

            mActivityVideoBinding.videoPlayer.setVideoURI(Uri.parse(bundle.getString("video")));

            mActivityVideoBinding.videoPlayer.start();*/
            /*mActivityVideoBinding.videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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

            });*/
            // mActivityVideoBinding.videoPlayer.setOrientation(LinearLayout.HORIZONTAL);


        }

        if (savedInstanceState != null) {
            videoPosition = savedInstanceState.getLong("position");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


        if (mUrl != null) {


            player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());

            mActivityVideoBinding.videoPlayer.setPlayer(player);

            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getApplicationInfo().loadLabel(getPackageManager()).toString()));

            switch (Util.inferContentType(Uri.parse(mUrl))) {

                case C.TYPE_HLS:
                    HlsMediaSource mediaSource = new HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(mUrl));
                    player.prepare(mediaSource);


                case C.TYPE_OTHER:

                    ExtractorMediaSource mediaSource1 = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(mUrl));
                    player.prepare(mediaSource1);

              /*  default:
                    setResult(Activity.RESULT_CANCELED);
                    finish();*/
            }
            player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == Player.STATE_READY && returnResultOnce) {
                        setResult(Activity.RESULT_OK);
                        returnResultOnce = false;
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    setResult(Activity.RESULT_CANCELED);
                    finishAndRemoveTask();
                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }

            });
            player.setPlayWhenReady(true);

            MediaSessionCompat mediaSession = new MediaSessionCompat(this, getPackageName());
            MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSession);
            mediaSessionConnector.setPlayer(player, null);
            mediaSession.setActive(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPosition = player.getCurrentPosition();


        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (videoPosition > 0L && !isInPipMode) {
            player.seekTo(videoPosition);
        }
        mActivityVideoBinding.videoPlayer.setUseController(true);
        registerWifiReceiver();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();
        player.release();
        //PIPmode activity.finish() does not remove the activity from the recents stack.
        //Only finishAndRemoveTask does this.
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            finishAndRemoveTask();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong("position", player.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        videoPosition = savedInstanceState.getLong("position");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void canceled() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onPictureInPictureModeChanged(Boolean isInPictureInPictureMode, Configuration newConfig) {
        if (newConfig != null) {
            videoPosition = player.getCurrentPosition();
            isInPipMode = isInPictureInPictureMode;
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        enterPIPMode();
    }

    public void enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            videoPosition = player.getCurrentPosition();
            mActivityVideoBinding.videoPlayer.setUseController(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
                this.enterPictureInPictureMode(params.build());
            } else {
                this.enterPictureInPictureMode();
            }
            /* We need to check this because the system permission check is publically hidden for integers for non-manufacturer-built apps
               https://github.com/aosp-mirror/platform_frameworks_base/blob/studio-3.1.2/core/java/android/app/AppOpsManager.java#L1640

               ********* If we didn't have that problem *********
                val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                if(appOpsManager.checkOpNoThrow(AppOpManager.OP_PICTURE_IN_PICTURE, packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).uid, packageName) == AppOpsManager.MODE_ALLOWED)

                30MS window in even a restricted memory device (756mb+) is more than enough time to check, but also not have the system complain about holding an action hostage.
             */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPIPPermission();
                }
            }, 30);

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    public void checkPIPPermission() {

        isPIPModeeEnabled = isInPictureInPictureMode();
        if (!isInPictureInPictureMode()) {
            onBackPressed();
        }
    }
}
