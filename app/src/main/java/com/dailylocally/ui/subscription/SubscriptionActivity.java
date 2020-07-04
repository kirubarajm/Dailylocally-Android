package com.dailylocally.ui.subscription;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySubscriptionBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.datepicker.DatePickerActivity;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class SubscriptionActivity extends BaseActivity<ActivitySubscriptionBinding, SubscriptionViewModel> implements SubscriptionNavigator, PlansAdapter.planListener {

    @Inject
    SubscriptionViewModel mSubscriptionViewModel;
    ActivitySubscriptionBinding mActivitySubscriptionBinding;


    Analytics analytics;
    String pageName = AppConstants.SCREEN_FAQS_AND_SUPPORT;

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
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, SubscriptionActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void selectDate(String startdate) {

        Intent intent = DatePickerActivity.newIntent(SubscriptionActivity.this);
        intent.putExtra("date",startdate);
        startActivityForResult(intent,AppConstants.DATE_REQUESTCODE);


        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sDay = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sMonth = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat sYear = new SimpleDateFormat("yyyy", Locale.getDefault());

        int year = 0;
        int month = 0;
        int day = 0;

        Date sDate = null;
        try {
            sDate = dateFormat.parse(startdate);

            if (sDate != null) {
                // parse input
                year = Integer.parseInt(sYear.format(sDate));
                month = Integer.parseInt(sMonth.format(sDate));
                day = Integer.parseInt(sDay.format(sDate));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        DatePickerDialog dialog = new DatePickerDialog(SubscriptionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mSubscriptionViewModel.startDate.set(dayOfMonth + "-" + month + "-" + year);


            }
        }, year, month - 1, day);

        dialog.getDatePicker().setMinDate(sDate.getTime());
        dialog.show();*/


    }
    private int getIndex(AppCompatSpinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
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



        if (subscriptionPlan.getSubscriptionPlan().size()>0)
            for (int i=0;i<subscriptionPlan.getSubscriptionPlan().size();i++){
                if (planid==subscriptionPlan.getSubscriptionPlan().get(i).getSpid()){
                    mActivitySubscriptionBinding.plans.setSelection(i);
                }

            }

    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAQS_AND_SUPPORT, AppConstants.CLICK_BACK_BUTTON);

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


        analytics = new Analytics(this, pageName);
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

        if (requestCode==AppConstants.DATE_REQUESTCODE){

            if (resultCode== Activity.RESULT_OK){

                mSubscriptionViewModel.startDate.set(data.getStringExtra("date"));

            }

        }



    }
}
