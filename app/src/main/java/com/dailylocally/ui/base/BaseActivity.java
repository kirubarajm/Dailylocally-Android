package com.dailylocally.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.dailylocally.R;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CancelListener;
import com.dailylocally.utilities.CommonUtils;
import com.dailylocally.utilities.NetworkUtils;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Map;

import dagger.android.AndroidInjection;
import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.notifications.NotificationContext;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements
        BaseFragment.Callback, CancelListener {



    private ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;




    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public abstract V getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*Notifications.setOnNotificationClickedListener(new OnNotificationClickedListener() {
            @Override
            public void onNotificationClicked(im.getsocial.sdk.notifications.Notification notification, NotificationContext
                    notificationContext) {
                // GetSocial.handle(notification.getAction());


                Bundle bundle = new Bundle();
                Intent intent = null;
                Map<String, String> actionDatas = null;

                String pageId = "0";
                if (notification.getAction() != null) {
                    actionDatas = notification.getAction().getData();
                    if (notification.getAction().getData().get("pageid") != null) {
                        pageId = notification.getAction().getData().get("pageid");
                    }

                }

                if (pageId == null) pageId = "0";
                switch (pageId) {
                    case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                        intent = CategoryL1Activity.newIntent(getApplicationContext());
                        bundle.putString("catid", actionDatas.get("catid"));
                        break;
                    case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                        intent = CategoryL2Activity.newIntent(getApplicationContext());
                        bundle.putString("catid", actionDatas.get("catid"));
                        bundle.putString("scl1id", actionDatas.get("scl1id"));
                        break;
                    case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                        intent = CatProductActivity.newIntent(getApplicationContext());
                        bundle.putString("catid", actionDatas.get("catid"));
                        break;
                    case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                        intent = MainActivity.newIntent(getApplicationContext(), AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                        break;
                    case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                        intent = TransactionHistoryActivity.newIntent(getApplicationContext());
                        break;
                    case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                        intent = TransactionDetailsActivity.newIntent(getApplicationContext());
                        bundle.putString("orderid", actionDatas.get("orderid"));

                        break;
                    case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                        intent = ProductDetailsActivity.newIntent(getApplicationContext());
                        bundle.putString("vpid", actionDatas.get("vpid"));

                        break;
                    case AppConstants.NOTIFY_COLLECTION_ACTV:
                        intent = CollectionDetailsActivity.newIntent(getApplicationContext());
                        bundle.putString("cid", actionDatas.get("cid"));

                        break;
                    case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                        intent = EventActivity.newIntent(getApplicationContext(), actionDatas.get("topic"), actionDatas.get("title"));

                        break;

                    default:
                        intent = SplashActivity.newIntent(getApplicationContext());


                }

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent,bundle);
            }
        });*/

    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());

    }

    public void openActivityOnTokenExpire() {

    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        /*try {
            unregisterReceiver(dataReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    protected void onStart() {
        super.onStart();
       /* IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver, intentFilter);
        ActiveActivitiesTracker.activityStarted();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
