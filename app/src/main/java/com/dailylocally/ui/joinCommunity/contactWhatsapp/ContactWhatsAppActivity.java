package com.dailylocally.ui.joinCommunity.contactWhatsapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityContactWhatsAppBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.joinCommunity.communityLocation.CommunityAddressActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;


public class ContactWhatsAppActivity extends BaseActivity<ActivityContactWhatsAppBinding, ContactWhatsAppViewModel> implements ContactWhatsAppNavigator  {

    @Inject
    ContactWhatsAppViewModel mOnBoardingActivityViewModel;
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
    private ActivityContactWhatsAppBinding mActivityContactWhatsAppBinding;

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, ContactWhatsAppActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        Intent intent = MainActivity.newIntent(ContactWhatsAppActivity.this,"","",AppConstants.SCREEN_NAME_WHATSAPP,AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onWhatsAppClick() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = mOnBoardingActivityViewModel.whatsAppLink.get();
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void callClick() {
        try {
            if (mOnBoardingActivityViewModel.phoneno!=null) {
                String number = mOnBoardingActivityViewModel.phoneno.get();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void changeHeader(String headerContent) {
        mActivityContactWhatsAppBinding.txtName.setText(Html.fromHtml(
                "<small>" + headerContent + "</small>" +"<b>" + " "+mOnBoardingActivityViewModel.getDataManager().getCurrentUserName() + "</b>" +","));
    }

    @Override
    public int getBindingVariable() {
        return BR.contactWhatsAppViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_whats_app;
    }

    @Override
    public ContactWhatsAppViewModel getViewModel() {
        return mOnBoardingActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContactWhatsAppBinding = getViewDataBinding();
        mOnBoardingActivityViewModel.setNavigator(this);

       /* Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_WHATSAPP);
*/
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
        mOnBoardingActivityViewModel.fetchData();
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
        goBack();
    }

    public void makeCallKitchenIntent(Context context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
    }

}
