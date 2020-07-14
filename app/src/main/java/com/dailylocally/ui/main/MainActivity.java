package com.dailylocally.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityMainBinding;
import com.dailylocally.ui.account.MyAccountFragment;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarFragment;
import com.dailylocally.ui.cart.CartFragment;
import com.dailylocally.ui.home.HomeFragment;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.search.SearchFragment;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.PushUtils;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import zendesk.support.request.RequestActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, PaymentResultListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;

    protected LocationManager locationManager;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog progressDialog;
    boolean cart = false;
    String pageid = "";
    String navigationPage;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_HOME;
    double clatitude;
    double clongitude;
    InstallStateUpdatedListener installStateUpdatedListener;
    int getAddressCount = 0;
    AppUpdateManager appUpdateManager;
    AppUpdateInfo appUpdateInfo;
    String screenName = "";
    Snackbar snackbar;

    String orderId;


    boolean downloading;

    boolean forceLocation = false;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MainActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }

        }
    };
    Dialog locationDialog;
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;

    public static Intent newIntent(Context context, String ToPage, String fromPage) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);

        return intent;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openCart(String screenName) {
        mMainViewModel.screenName.set(AppConstants.SCREEN_HOME);
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_CART);

        try {
            Bundle bundle = new Bundle();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CartFragment fragment = new CartFragment();
            fragment.setArguments(bundle);
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(CartActivity.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Cart");
        mMainViewModel.titleVisible.set(true);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(true);
        mMainViewModel.isMyAccount.set(false);
        mMainViewModel.updateAvailable.set(false);
    }


    @Override
    public void openHome() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GO_HOME);

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //  CalendarFragment fragment = new CalendarFragment();
            HomeFragment fragment = new HomeFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Home");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(true);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isOrder.set(false);
        mMainViewModel.isMyAccount.set(false);

        if (mMainViewModel.update.get()) {
            if (!mMainViewModel.isLiveOrder.get()) {
                mMainViewModel.updateAvailable.set(true);
            }
        }

    }

    @Override
    public void openOrders() {

       /* Intent intent = CalendarActivity.newIntent(MainActivity.this);
        startActivity(intent);*/

        mMainViewModel.toolbarTitle.set("My Orders");
        mMainViewModel.titleVisible.set(true);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isOrder.set(true);
        mMainViewModel.isMyAccount.set(false);
        mMainViewModel.updateAvailable.set(false);


        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GO_HOME);

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CalendarFragment fragment = new CalendarFragment();
            //HomeFragment fragment = new HomeFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    @Override
    public void openExplore() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_SEARCH);
        // stopLoader();
        try {
            mMainViewModel.isExplore.set(true);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SearchFragment fragment = new SearchFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        mMainViewModel.toolbarTitle.set("Search");

        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(true);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);

        mMainViewModel.updateAvailable.set(false);


    }


    @Override
    public void openAccount() {
        //stopLoader();
        try {
            //new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_MY_ACCOUNT);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyAccountFragment fragment = new MyAccountFragment();
            transaction.replace(R.id.content_main, fragment);
            // transaction.addToBackStack(MyAccountFragment.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("My Account");
        mMainViewModel.titleVisible.set(true);
        mMainViewModel.updateAvailable.set(false);

    }

    @Override
    public void paymentSuccessed(boolean status) {

        if (status) {
            Intent newIntent = OrderPlacedActivity.newIntent(MainActivity.this);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        /*if (mMainViewModel.isExplore.get()) {
            openHome();
        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_BACK_BUTTON);
            if (doubleBackToExitPressedOnce) {
                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_EXIT_APP);
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }*/


        if (mMainViewModel.isHome.get()) {

            if (doubleBackToExitPressedOnce) {
                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_EXIT_APP);
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            openHome();
        }


    }


    public void finishActivity(){
        finish();
    }

    public void saveFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mMainViewModel.saveToken(token);
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        PushUtils.registerWithZendesk();

        // openHome();
        saveFcmToken();

        //  updateUIalert();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            switch (intent.getExtras().getString(AppConstants.PAGE, "")) {

                case AppConstants.NOTIFY_CART_FRAG:
                    openCart(intent.getExtras().getString(AppConstants.PAGE, ""));
                    break;

                case AppConstants.NOTIFY_MYACCOUNT_FRAG:
                    openAccount();
                    break;

                case AppConstants.NOTIFY_MY_ORDER_FRAG:
                    openOrders();
                    break;

                case AppConstants.NOTIFY_HOME_FRAG:
                    openHome();
                    break;

                case AppConstants.NOTIFY_SEARCH_FRAG:
                    openExplore();
                    break;

                case AppConstants.NOTIFY_CHAT:
                    openChat();
                    break;
                default:
                    openHome();

            }
            if (intent.getExtras().getString("requestId") != null) {
                RequestActivity.builder()
                        .withRequestId(intent.getExtras().getString("requestId"))
                        .show(this);

            }

        }

        /*Intent intent = getIntent();
        if (intent.getExtras() != null) {
            cart = intent.getExtras().getBoolean("cart");
            navigationPage = intent.getExtras().getString("page");
            screenName = intent.getExtras().getString("screenName");
            if (null != intent.getExtras().getString("pageid")) {
                pageid = intent.getExtras().getString("pageid");
            }
            if (intent.getExtras().getBoolean("chat")) {


                ZopimChat.init(getString(R.string.zopim_account_id));
                final VisitorInfo.Builder build = new VisitorInfo.Builder()
                        .email(mMainViewModel.getDataManager().getCurrentUserEmail())
                        .name(mMainViewModel.getDataManager().getCurrentUserName())
                        .phoneNumber(mMainViewModel.getDataManager().getCurrentUserPhNo());
                ZopimChat.setVisitorInfo(build.build());

// build pre chat form config
                PreChatForm preChatForm = new PreChatForm.Builder()
                        .name(PreChatForm.Field.NOT_REQUIRED)
                        .email(PreChatForm.Field.NOT_REQUIRED)
                        .phoneNumber(PreChatForm.Field.NOT_REQUIRED)
                        .department(PreChatForm.Field.NOT_REQUIRED)
                        .message(PreChatForm.Field.NOT_REQUIRED)
                        .build();

// build session config
                ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                        .preChatForm(preChatForm);
                ZopimChatActivity.startActivity(this, config);
            }

            if (intent.getExtras().getString("requestId") != null) {
                RequestActivity.builder()
                        .withRequestId(intent.getExtras().getString("requestId"))
                        .show(this);

            }

        }


        if (intent != null && intent.getData() != null
                && (intent.getData().getScheme().equals(getString(R.string.deferred_deeplink_scheme)))) {
            Uri data = intent.getData();
            List<String> pathSegments = data.getPathSegments();
            if (data.getLastPathSegment().equals("cart")) cart = true;

        } else if (intent != null && intent.getData() != null
                && (intent.getData().getScheme().equals(getString(R.string.deeplink_scheme)))) {
            Uri data = intent.getData();
            List<String> pathSegments = data.getPathSegments();
            if (data.getLastPathSegment().equals("cart")) cart = true;

        }*/
        /*if (!mMainViewModel.isAddressAdded()) {
            startLoader();
            startLocationTracking();
        }*/
        /*if (mMainViewModel.isAddressAdded()) {
            if (cart) {
                cart = false;
                mMainViewModel.gotoCart(screenName);
            } else if (pageid.equals("") && pageid.equals("9")) {
*//*
                Intent repliesIntent = RepliesActivity.newIntent(MainActivity.this);
                startActivity(repliesIntent);*//*
            } else {

                if (navigationPage != null && navigationPage.equals(AppConstants.SCREEN_MY_ORDERS)) {
                    gotoMyOrders();
                } else if (navigationPage != null && navigationPage.equals(AppConstants.SCREEN_CART_PAGE)) {
                    openCart("L2");
                } else {

                    if (mMainViewModel.isHome.get()) {


                    } else if (mMainViewModel.isMyAccount.get()) {


                    } else if (mMainViewModel.isExplore.get()) {


                    } else if (mMainViewModel.isCart.get()) {


                    } else {

                        openHome();
                    }
                }
            }

        }*/
    }


    public void statusUpdate() {
        mMainViewModel.totalCart();
    }

    public void openChat() {
        ZopimChat.init(getString(R.string.zopim_account_id));
        final VisitorInfo.Builder build = new VisitorInfo.Builder()
                .email(mMainViewModel.getDataManager().getCurrentUserEmail())
                .name(mMainViewModel.getDataManager().getCurrentUserName())
                .phoneNumber(mMainViewModel.getDataManager().getCurrentUserPhNo());
        ZopimChat.setVisitorInfo(build.build());
        // build pre chat form config
        PreChatForm preChatForm = new PreChatForm.Builder()
                .name(PreChatForm.Field.NOT_REQUIRED)
                .email(PreChatForm.Field.NOT_REQUIRED)
                .phoneNumber(PreChatForm.Field.NOT_REQUIRED)
                .department(PreChatForm.Field.NOT_REQUIRED)
                .message(PreChatForm.Field.NOT_REQUIRED)
                .build();
        // build session config
        ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                .preChatForm(preChatForm);
        ZopimChatActivity.startActivity(this, config);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


   /* @Override
    public void isInternet(boolean available) {
        if (available) {
            openHome();

        }
    }*/

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
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
    public void onDestroy() {
        super.onDestroy();

        mMainViewModel.getDataManager().appStartedAgain(false);
        /*try {
            unregisterReceiver(dataReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void gotoMyOrders() {
      /*  Intent intent = CurrentOrderActivity.newIntent(MainActivity.this);
        startActivity(intent);
        finish();*/
    }

    @Override
    protected void onResume() {
        super.onResume();

       /* appUpdateManager.registerListener(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(this);
*/

        statusUpdate();

       /* IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver, intentFilter);
        registerWifiReceiver();*/



       /* mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            RC_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();

        cart = false;
        pageid = "";

/*
        try {
            unregisterReceiver(dataReceiver);
            unregisterWifiReceiver();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*/

    }


    @Override
    public void canceled() {

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onPaymentSuccess(String s) {
        mMainViewModel.paymentSuccess(orderId, s, 1);
    }

    @Override
    public void onPaymentError(int i, String s) {
        mMainViewModel.paymentSuccess(orderId, s, 0);
    }

    public void makePayment(String orderId, String customerId, String amount) {
        this.orderId = orderId;


        JSONObject options;

        final Activity activity = MainActivity.this;

        final Checkout co = new Checkout();

        //  co.setImage(R.mipmap.ic_launcher);

        co.setFullScreenDisable(true);
        try {
            options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            // options.put("image", "https://eattovo.s3.amazonaws.com/upload/admin/makeit/product/1587203475912-Daily-ICons-04.png");
            options.put("description", getString(R.string.orderid) + orderId);
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            //  options.put("order_id","order_DSJqzwMe7ULv5L");
            options.put("amount", Integer.parseInt(amount) * 100);
            options.put("customer_id", customerId);
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            options.put("readonly", ReadOnly);

            co.open(activity, options);


        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
