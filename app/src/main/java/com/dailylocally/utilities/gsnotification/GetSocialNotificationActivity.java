package com.dailylocally.utilities.gsnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityGetsocialNotificationBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.notifications.Notification;
import im.getsocial.sdk.notifications.NotificationContext;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;

public class GetSocialNotificationActivity extends BaseActivity<ActivityGetsocialNotificationBinding, GetSocialNotificationViewModel> implements GetSocialNotificationNavigator {

    @Inject
    GetSocialNotificationViewModel mGetSocialNotificationViewModel;
    ActivityGetsocialNotificationBinding mOrderPlacedBinding;
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

    public static Intent newIntent(Context context) {
        return new Intent(context, GetSocialNotificationActivity.class);
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
    public void gotoOrders() {
       /* Intent intent = MainActivity.newIntent(OrderPlacedActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("page", SCREEN_MY_ORDERS);
        intent.putExtra("screenName", AppConstants.SCREEN_ORDER_PLACED);
        startActivity(intent);
        finish();*/

        Intent intent = MainActivity.newIntent(GetSocialNotificationActivity.this, AppConstants.NOTIFY_MY_ORDER_FRAG, AppConstants.NOTIFY_ORDER_PLACED_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public void goHome() {
        Intent intent = MainActivity.newIntent(GetSocialNotificationActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_ORDER_PLACED_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public int getBindingVariable() {
        return BR.getSocialNotificationViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_getsocial_notification;
    }

    @Override
    public GetSocialNotificationViewModel getViewModel() {
        return mGetSocialNotificationViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGetSocialNotificationViewModel.setNavigator(this);
        mOrderPlacedBinding = getViewDataBinding();


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
            Toast.makeText(this, "data", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
        }

        Notifications.setOnNotificationClickedListener(new OnNotificationClickedListener() {
            @Override
            public void onNotificationClicked(Notification notification, NotificationContext notificationContext) {
                Toast.makeText(GetSocialNotificationActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void canceled() {

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
}
