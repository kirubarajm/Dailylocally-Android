package com.dailylocally.utilities.nointernet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentNoInternetBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.DailylocallyApp;

import javax.inject.Inject;


public class InternetErrorFragment extends BaseActivity<FragmentNoInternetBinding, InternetErrorViewModel> implements InternetErrorNavigator {
    @Inject
    InternetErrorViewModel mInternetErrorViewModel;
    FragmentNoInternetBinding mFragmentNoInternetBinding;
    boolean doubleBackToExitPressedOnce = false;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (checkWifiConnect()) {
                setActivityResult();
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, InternetErrorFragment.class);
    }

    public void setActivityResult() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentNoInternetBinding = getViewDataBinding();
        mInternetErrorViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.internetErrorViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_no_internet;
    }

    @Override
    public InternetErrorViewModel getViewModel() {
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mInternetErrorViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void retry() {
        // internetListener.isInternet(mInternetErrorViewModel.checkInternet());

        if (mInternetErrorViewModel.checkInternet()) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (manager != null)
            networkInfo = manager.getActiveNetworkInfo();

        /*NetworkInfo activeNetwork = null;
        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);*/
       /* if (cm != null)
            activeNetwork = cm.getActiveNetworkInfo();*/
        /*boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/

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
    protected void onStart() {
        super.onStart();
        registerWifiReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterWifiReceiver();
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            if (mInternetErrorViewModel.checkInternet()) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to close app", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }


}
