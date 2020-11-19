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
import android.util.Log;
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
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.CommunityFragment;
import com.dailylocally.ui.community.catlist.CommunityCatFragment;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.search.SearchFragment;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DependenciesContainer;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import im.getsocial.sdk.CompletionCallback;
import im.getsocial.sdk.FailureCallback;
import im.getsocial.sdk.GetSocial;
import im.getsocial.sdk.GetSocialError;
import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.notifications.Notification;
import im.getsocial.sdk.notifications.NotificationContext;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;
import zendesk.chat.Chat;
import zendesk.chat.ChatConfiguration;
import zendesk.chat.ChatEngine;
import zendesk.chat.ChatProvider;
import zendesk.chat.ProfileProvider;
import zendesk.chat.VisitorInfo;
import zendesk.messaging.MessagingActivity;


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

    String orderId, customerId, amount;
    JSONObject options;
    boolean downloading;
    String totalCharge;
    Context context;
    String dlMethod;
    String pMode;
    int cartSize;
    String cartValue;
    String gst;
    int delCharges;
    String couponName;
    String globalScreenName = "";
    boolean forceLocation = false;
    boolean searchfromHome = false;
    DependenciesContainer _dependenciesContainer;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MainActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        }
    };
    Dialog locationDialog;
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;

    public static Intent newIntent(Context context, String ToPage, String fromPage,String fPage,String tPage) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.FROM_PAGE, fPage);
        intent.putExtra(AppConstants.To_PAGE, tPage);

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

        new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_CART);
        globalScreenName = AppConstants.SCREEN_NAME_CART;

        mMainViewModel.screenName.set(AppConstants.SCREEN_HOME);


        try {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_CART);
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
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isCommunityCat.set(false);
        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(true);
        mMainViewModel.isMyAccount.set(false);
        mMainViewModel.updateAvailable.set(false);
    }


    @Override
    public void openCommunity() {

        try {
            new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_HOME);
            globalScreenName = AppConstants.SCREEN_NAME_HOME;


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //  CalendarFragment fragment = new CalendarFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_COMMUNITY);
            CommunityFragment fragment = new CommunityFragment();
            fragment.setArguments(bundle);
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Community");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isCommunity.set(true);
        mMainViewModel.isCommunityCat.set(false);
        mMainViewModel.isHome.set(false);

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
    public void openCommunityCat() {

        try {
            new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_CATEGORY_PAGE);
            globalScreenName = AppConstants.SCREEN_NAME_CATEGORY_PAGE;


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //  CalendarFragment fragment = new CalendarFragment();
            CommunityCatFragment fragment = new CommunityCatFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_COMMUNITY_CAT_LIST);
            fragment.setArguments(bundle);

            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Community");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isCommunityCat.set(true);
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isOrder.set(false);
        mMainViewModel.isMyAccount.set(false);

        if (mMainViewModel.update.get()) {
            if (!mMainViewModel.isLiveOrder.get()) {
                mMainViewModel.updateAvailable.set(false);
            }
        }

    }

    public void homeNavigation() {
        if (mMainViewModel.isCommunityUser.get()) {
            openCommunityCat();
        } else {
            openHome();
        }
    }


    @Override
    public void openHome() {
        openCommunity();

      /*  try {
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
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isCommunityCat.set(false);
        mMainViewModel.isHome.set(true);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isOrder.set(false);
        mMainViewModel.isMyAccount.set(false);

        if (mMainViewModel.update.get()) {
            if (!mMainViewModel.isLiveOrder.get()) {
                mMainViewModel.updateAvailable.set(true);
            }
        }*/

    }

    @Override
    public void openOrders() {

       /* Intent intent = CalendarActivity.newIntent(MainActivity.this);
        startActivity(intent);*/
        new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_CALENDAR);
        globalScreenName = AppConstants.SCREEN_NAME_CALENDAR;

        mMainViewModel.toolbarTitle.set("My Orders");
        mMainViewModel.titleVisible.set(true);
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isCommunityCat.set(false);
        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isOrder.set(true);
        mMainViewModel.isMyAccount.set(false);
        mMainViewModel.updateAvailable.set(false);


        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_CALENDAR);

            CalendarFragment fragment = new CalendarFragment();
            fragment.setArguments(bundle);
            //HomeFragment fragment = new HomeFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    @Override
    public void openExplore(Boolean isHome) {

        new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_SEARCH);
        globalScreenName = AppConstants.SCREEN_NAME_SEARCH;

        searchfromHome = isHome;

        // stopLoader();
        try {
            mMainViewModel.isExplore.set(true);
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_NAME_CALENDAR);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SearchFragment fragment = new SearchFragment();
            fragment.setArguments(bundle);
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();


        } catch (Exception ee) {
            ee.printStackTrace();
        }

        mMainViewModel.toolbarTitle.set("Search");

        mMainViewModel.titleVisible.set(false);
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isCommunityCat.set(false);
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
            new Analytics().eventPageOpens(this, globalScreenName, AppConstants.SCREEN_NAME_MY_ACCOUNT);
            globalScreenName = AppConstants.SCREEN_NAME_MY_ACCOUNT;


            //new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_MY_ACCOUNT);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyAccountFragment fragment = new MyAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.FROM, AppConstants.SCREEN_NAME_MAIN);
            bundle.putString(AppConstants.PAGE, AppConstants.SCREEN_MY_ACCOUNT);
            fragment.setArguments(bundle);
            transaction.replace(R.id.content_main, fragment);
            // transaction.addToBackStack(MyAccountFragment.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.isCommunity.set(false);
        mMainViewModel.isCommunityCat.set(false);
        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(true);
        mMainViewModel.toolbarTitle.set("My Account");
        mMainViewModel.titleVisible.set(true);
        mMainViewModel.updateAvailable.set(false);
    }

    @Override
    public void paymentSuccessed(boolean status) {

        if (pMode.equals("online"))
            new Analytics().eventPaymentCompleted(context, "", cartSize, cartValue, gst,delCharges,
                   couponName,totalCharge,orderId);

        if (status) {
            Intent newIntent = OrderPlacedActivity.newIntent(MainActivity.this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_ORDER_PLACED);
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

        if (mMainViewModel.isExplore.get()) {
            if (searchfromHome) {
                handleBackpress();

            } else {
                openCommunityCat();
            }
        } else {
            handleBackpress();
        }

    }


    public void handleBackpress() {

        if (mMainViewModel.isHome.get() || mMainViewModel.isCommunity.get()) {

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
            // openHome();
            if (mMainViewModel.isCommunityUser.get()) {
                openCommunity();
            } else {
                openHome();
            }
        }


    }


    public void finishActivity() {
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
        Log.e("OnCreate", "Created");

        GetSocial.addOnInitializeListener(new Runnable() {
            @Override
            public void run() {
                // GetSocial is ready to be used
            }
        });
        Notifications.registerDevice();
        Notifications.setOnNotificationClickedListener(new OnNotificationClickedListener() {
            @Override
            public void onNotificationClicked(Notification notification, NotificationContext notificationContext) {
                notificationToPage(notification);
            }
        });
        // openHome();
        saveFcmToken();

        //  updateUIalert();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            mMainViewModel.getUserDetails(intent.getExtras().getString(AppConstants.PAGE, ""));

           /* if (intent.getExtras().getString("requestId") != null) {
                RequestActivity.builder()
                        .withRequestId(intent.getExtras().getString("requestId"))
                        .show(this);

            }*/

        } else {
            if (mMainViewModel.isCommunityUser.get()) {
                mMainViewModel.getUserDetails(AppConstants.NOTIFY_COMMUNITY_HOME_FRAG);
            } else {
                mMainViewModel.getUserDetails(AppConstants.NOTIFY_HOME_FRAG);
            }
        }

        /*if (BuildConfig.ENABLE_DEBUG) {
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    AdvertisingIdClient.Info idInfo = null;
                    try {
                        idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                        idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String advertId = null;
                    try {
                        advertId = idInfo.getId();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    return advertId;
                }

                @Override
                protected void onPostExecute(String advertId) {
                    Toast.makeText(getApplicationContext(), "test code copied", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("id", advertId);
                    clipboard.setPrimaryClip(clip);
                }

            };
            task.execute();

        }*/

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

        globalScreenName = intent.getExtras().getString(AppConstants.FROM_PAGE, "nil");
    }

    @Override
    public void pageNavigation(String pages) {

        if (mMainViewModel.isCommunityUser.get()) {
            Notifications.registerDevice();

        } else {
            Notifications.setPushNotificationsEnabled(false, new CompletionCallback() {
                @Override
                public void onSuccess() {

                }
            }, new FailureCallback() {
                @Override
                public void onFailure(GetSocialError getSocialError) {

                }
            });
        }

        switch (pages) {

            case AppConstants.NOTIFY_CART_FRAG:
                openCart(pages);
                break;

            case AppConstants.NOTIFY_MYACCOUNT_FRAG:
                openAccount();
                break;

            case AppConstants.NOTIFY_MY_ORDER_FRAG:
                openOrders();
                break;

            case AppConstants.NOTIFY_HOME_FRAG:
                if (mMainViewModel.isCommunityUser.get()) {
                    openCommunity();
                } else {
                    openHome();
                }
                break;

            case AppConstants.NOTIFY_SEARCH_FRAG:
                openExplore(true);
                break;

            case AppConstants.NOTIFY_CHAT:
                openChat();
                break;
            case AppConstants.NOTIFY_COMMUNITY_HOME_FRAG:
                openCommunity();
                break;
            default:
                //openHome();
                if (mMainViewModel.isCommunityUser.get()) {
                    openCommunity();
                } else {
                    openHome();
                }

        }
    }


    public void statusUpdate() {
        mMainViewModel.totalCart();
    }

    public void openChat() {
       /* ZopimChat.init(getString(R.string.zopim_account_id));
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
        ZopimChatActivity.startActivity(this, config);*/


        ProfileProvider profileProvider = Chat.INSTANCE.providers().profileProvider();

      //  profileProvider.setVisitorNote(note, null);
        ArrayList<String> ltag = new ArrayList<>();
     //   ltag.add(tag);
    //    profileProvider.addVisitorTags(ltag, null);
        VisitorInfo visitorInfo = VisitorInfo.builder()
                .withEmail(mMainViewModel.getDataManager().getCurrentUserEmail())
                .withName(mMainViewModel.getDataManager().getCurrentUserName())
                .withPhoneNumber(mMainViewModel.getDataManager().getCurrentUserPhNo())
                .build();

        profileProvider.setVisitorInfo(visitorInfo, null);

        ChatProvider chatProvider = Chat.INSTANCE.providers().chatProvider();

        chatProvider.setDepartment("Daily locally", null);

        ChatConfiguration chatConfiguration = ChatConfiguration.builder()
                .withPreChatFormEnabled(false)
                .build();

        MessagingActivity.builder()
                .withEngines(ChatEngine.engine())
                .show(MainActivity.this);


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
        Log.e("onDestroy", "Destroyed");
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
        Log.e("onStart", "Started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "Stopped");
    }

    public void gotoMyOrders() {
      /*  Intent intent = CurrentOrderActivity.newIntent(MainActivity.this);
        startActivity(intent);
        finish();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        Log.e("onResume", "Resumed");
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
        unregisterWifiReceiver();
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

    public void makePayment(String orderId, String customerId, String amount, Context context, String dlMethod, String pMode, int cartSize, String cartValue,
                            String gst, int delCharges, String couponName,String totalCharge) {

        this.context = context;
        this.dlMethod = dlMethod;
        this.pMode = pMode;
        this.cartSize = cartSize;
        this.cartValue = cartValue;
        this.gst = gst;
        this.delCharges = delCharges;
        this.couponName = couponName;
        this.totalCharge = totalCharge;

        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    private void notificationToPage(im.getsocial.sdk.notifications.Notification notification) {
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
                intent = CategoryL1Activity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = CategoryL2Activity.newIntent(this, AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST, AppConstants.SCREEN_NAME_SUB_CATEGORY_L2_PRODUCTS);
                bundle.putString("catid", actionDatas.get("catid"));
                bundle.putString("scl1id", actionDatas.get("scl1id"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                intent = CatProductActivity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_VIEW_ALL_PRODUCTS);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                //   intent = MainActivity.newIntent(this, AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                openCommunityCat();
                return;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = TransactionHistoryActivity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_TRANSACTION);
                break;
            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                intent = TransactionDetailsActivity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_TRANS_DETAILS);
                bundle.putString("orderid", actionDatas.get("orderid"));

                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                intent = ProductDetailsActivity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
                bundle.putString("vpid", actionDatas.get("vpid"));

                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                intent = CollectionDetailsActivity.newIntent(this, AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_COLLECTION_DETAIL);
                bundle.putString("cid", actionDatas.get("cid"));

                break;
            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                intent = EventActivity.newIntent(this, actionDatas.get("topic"), actionDatas.get("title"), AppConstants.SCREEN_NAME_MAIN, AppConstants.SCREEN_NAME_COMMUNITY_EVENT);

                break;
            /*default:
                intent = SplashActivity.newIntent(this);*/
        }
        if (intent != null) {
            intent.putExtras(bundle);
            //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

}
