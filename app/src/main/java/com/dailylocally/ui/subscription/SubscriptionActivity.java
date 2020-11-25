package com.dailylocally.ui.subscription;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySubscriptionBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.datepicker.DatePickerActivity;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class SubscriptionActivity extends BaseActivity<ActivitySubscriptionBinding, SubscriptionViewModel> implements SubscriptionNavigator, PlansAdapter.planListener {

    @Inject
    SubscriptionViewModel mSubscriptionViewModel;
    ActivitySubscriptionBinding mActivitySubscriptionBinding;

    String pid;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };

    public static Intent newIntent(Context context,String fromPage,String ToPage) {
        Intent intent = new Intent(context, SubscriptionActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void subscribed() {
        ArrayList<String> weekdays = new ArrayList<>();

        if (mSubscriptionViewModel.monClicked.get())
            weekdays.add("Monday");

        if (mSubscriptionViewModel.tueClicked.get())
            weekdays.add("Tuesday");

        if (mSubscriptionViewModel.wedClicked.get())
            weekdays.add("Wednesday");

        if (mSubscriptionViewModel.thuClicked.get())
            weekdays.add("Thursday");

        if (mSubscriptionViewModel.friClicked.get())
            weekdays.add("Friday");

        if (mSubscriptionViewModel.satClicked.get())
            weekdays.add("saturday");

        if (mSubscriptionViewModel.sunClicked.get())
            weekdays.add("sunday");


        String price = "";
        if (mSubscriptionViewModel.products.isDiscountCostStatus()) {
            price =mSubscriptionViewModel.products.getMrpDiscountAmount();
        } else {
            price = mSubscriptionViewModel.products.getMrp();
        }





        new Analytics().eventUserSubscribe(SubscriptionActivity.this,mSubscriptionViewModel.products.getProductname(),
                mSubscriptionViewModel.products.getCatName(),mSubscriptionViewModel.products.getSubCat1(),mSubscriptionViewModel.products.getSubCat2(),
                price,
                "",mSubscriptionViewModel.totalCart(),mSubscriptionViewModel.quantity,weekdays,
                mSubscriptionViewModel.days,mSubscriptionViewModel.startDate.get(),mSubscriptionViewModel.pageType);


        Intent intent = new Intent();
        intent.putExtra("pid", pid);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void selectDate(String startdate) {

        Intent intent = DatePickerActivity.newIntent(SubscriptionActivity.this);
        intent.putExtra("date", startdate);
        startActivityForResult(intent, AppConstants.DATE_REQUESTCODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private int getIndex(AppCompatSpinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }


    @Override
    public void plans(SubscriptionResponse subscriptionPlan) {

        PlansAdapter plansAdapter = new PlansAdapter(subscriptionPlan.getSubscriptionPlan(), SubscriptionActivity.this, this);
        mActivitySubscriptionBinding.plans.setAdapter(plansAdapter);

        mActivitySubscriptionBinding.plans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSubscriptionViewModel.planId = mSubscriptionViewModel.mSubscriptionResponse.getSubscriptionPlan().get(position).getSpid();
                mSubscriptionViewModel.days= mSubscriptionViewModel.mSubscriptionResponse.getSubscriptionPlan().get(position).getNumberofdays();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                if (mSubscriptionViewModel.mSubscriptionResponse != null && mSubscriptionViewModel.mSubscriptionResponse.getSubscriptionPlan() != null && mSubscriptionViewModel.mSubscriptionResponse.getSubscriptionPlan().size() > 0)
                    mSubscriptionViewModel.planId = mSubscriptionViewModel.mSubscriptionResponse.getSubscriptionPlan().get(0).getSpid();
            }
        });


    }

    @Override
    public void selectedplan(int planid, SubscriptionResponse subscriptionPlan) {


        if (subscriptionPlan.getSubscriptionPlan().size() > 0)
            for (int i = 0; i < subscriptionPlan.getSubscriptionPlan().size(); i++) {
                if (planid == subscriptionPlan.getSubscriptionPlan().get(i).getSpid()) {
                    mActivitySubscriptionBinding.plans.setSelection(i);
                }

            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.subscriptionViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_subscription;
    }

    @Override
    public SubscriptionViewModel getViewModel() {
        return mSubscriptionViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptionViewModel.setNavigator(this);
        mActivitySubscriptionBinding = getViewDataBinding();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            pid = intent.getExtras().getString("pid");
            mSubscriptionViewModel.fetchProductDetails(pid);
        }

        if (intent.getExtras().getString(AppConstants.FROM, "nil").equals(AppConstants.SCREEN_NAME_PRODUCT_DETAIL)){
            mSubscriptionViewModel.pageType="item_detail_page";
        }else {
            mSubscriptionViewModel.pageType="listing_page";
        }

        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_SUBSCRIPTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
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
    public void selectedPlan(SubscriptionResponse.SubscriptionPlan subscriptionPlan) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.DATE_REQUESTCODE) {

            if (resultCode == Activity.RESULT_OK) {
                mSubscriptionViewModel.startDate.set(mSubscriptionViewModel.parseDateToYYYYMMDD(   data.getStringExtra("date")));
                mSubscriptionViewModel.showDate.set(mSubscriptionViewModel.parseDate(data.getStringExtra("date"),AppConstants.DATE_FORMAT_DDMMYYYY,AppConstants.DATE_FORMAT_DDMMMYYYY));
            }

        }

    }
}
