package com.dailylocally.ui.productDetail.productDetailCancel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductCancelBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.productDetail.dialogProductCancel.DialogProductCancel;
import com.dailylocally.ui.productDetail.dialogProductCancel.ProductCancelListenerCallBack;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class ProductCancelActivity extends BaseActivity<ActivityProductCancelBinding, ProductCancelViewModel> implements
        ProductCancelNavigator, HasSupportFragmentInjector, ProductCancelListenerCallBack {


    public ActivityProductCancelBinding mActivityProductCancelBinding;
    @Inject
    public ProductCancelViewModel mAddAddressViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

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
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;
    String doId ="",dayOrderPId="";

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductCancelActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.productCancelViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cancel;
    }

    @Override
    public ProductCancelViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void cancelProductClick() {
        Bundle bundle=new Bundle();
        bundle.putString("doid",doId);
        bundle.putString("dayOrderPid",dayOrderPId);

        DialogProductCancel filterFragment = new DialogProductCancel();
        filterFragment.setArguments(bundle);
        filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());
    }

    @Override
    public void success(int isCancelable,String date) {
        try {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
            SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = currentFormat.parse(date);
            String datesdf = dateDayFormat.format(date1);

            mAddAddressViewModel.productDate.set(datesdf);
            if (isCancelable==0){
                mAddAddressViewModel.isCancel.set(true);
            }else {
                mAddAddressViewModel.isCancel.set(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProductCancelBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            doId = bundle.getString("doid");
            dayOrderPId = bundle.getString("dayorderpid");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mAddAddressViewModel.getProductCancelDetails(doId,dayOrderPId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void sendData(boolean trueOrFalse) {
        //mAddAddressViewModel.getProductCancelDetails(doId,dayOrderPId);
      //  mAddAddressViewModel.isCancel.set(true);
        finish();
    }
}