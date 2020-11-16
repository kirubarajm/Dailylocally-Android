package com.dailylocally.ui.orderplaced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;

import com.dailylocally.databinding.OrderPlacedBinding;
import com.dailylocally.ui.aboutus.AboutUsActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;

public class OrderPlacedActivity extends BaseActivity<OrderPlacedBinding, OrderPlacedViewModel> implements OrderPlacedNavigator {

    @Inject
    OrderPlacedViewModel mOrderPlacedViewModel;
    OrderPlacedBinding mOrderPlacedBinding;
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
    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, OrderPlacedActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, ToPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void gotoOrders() {

        Intent intent = MainActivity.newIntent(OrderPlacedActivity.this,AppConstants.NOTIFY_MY_ORDER_FRAG,AppConstants.NOTIFY_ORDER_PLACED_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
       overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void goHome() {
        Intent intent = MainActivity.newIntent(OrderPlacedActivity.this,AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_ORDER_PLACED_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
     overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public int getBindingVariable() {
        return BR.orderPlacedViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.order_placed;
    }

    @Override
    public OrderPlacedViewModel getViewModel() {
        return mOrderPlacedViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderPlacedViewModel.setNavigator(this);
        mOrderPlacedBinding = getViewDataBinding();

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_ORDER_PLACED);
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
        new Handler().postDelayed(() -> {
            mOrderPlacedBinding.check.check();
        }, 800);
    }
}
