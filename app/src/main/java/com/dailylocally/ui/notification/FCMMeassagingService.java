package com.dailylocally.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.cart.CartRequest;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.favourites.FavActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.ui.signup.opt.OtpActivity;
import com.dailylocally.ui.signup.privacy.PrivacyActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.signup.registration.TokenRequest;
import com.dailylocally.ui.signup.tandc.TermsAndConditionActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.GenerateGetSocialNotification;
import com.dailylocally.utilities.GetSocialNotifyResponse;
import com.dailylocally.utilities.PushUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zendesk.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.notifications.NotificationContext;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;
import im.getsocial.sdk.notifications.OnNotificationReceivedListener;

public class FCMMeassagingService extends FirebaseMessagingService {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final int NOTIFICATION_ID = 134345;
    private static final String ZD_REQUEST_ID_KEY = "ticket_id";
    private static final String ZD_MESSAGE_KEY = "message";
    private int numMessages = 0;
    private Boolean socialNofication = true;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();


    //    Gson sGson = new GsonBuilder().create();
     // GetSocialNotifyResponse getSocialNotifyResponse = sGson.fromJson(remoteMessage.getData().get("gs_data"), GetSocialNotifyResponse.class);




       // sendSNotification(remoteMessage.getData());

        /*Notifications.setOnNotificationReceivedListener(new OnNotificationReceivedListener() {
            @Override
            public void onNotificationReceived(im.getsocial.sdk.notifications.Notification snotification) {
               // socialNofication = true;
             //   sendSocialNotification(snotification);
          // new GenerateGetSocialNotification(getApplicationContext(),snotification).execute();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendSocialNotification(snotification);
                    }
                },1);

               *//* new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        sendSocialNotification(snotification);
                    }
                };*//*

                *//*AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
                        sendSocialNotification(snotification);

                    }
                });*//*


            }
        });*/
       // testNotify();





       // if (notification != null) {
            Map<String, String> data = remoteMessage.getData();
            //   Log.d("FROM", remoteMessage.getFrom());

            final String requestId = remoteMessage.getData().get(ZD_REQUEST_ID_KEY);
            final String message = remoteMessage.getData().get(ZD_MESSAGE_KEY);
            final String pageId = remoteMessage.getData().get("pageid");


            Log.e("Remote message data", data.toString());

            String chatData = remoteMessage.getData().get("data");

            String author = null;
            String chatMessage = null;
            if (chatData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(chatData);
                    author = jsonObject.getString("author");
                    chatMessage = jsonObject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (author != null) {
                if (chatMessage != null && !chatMessage.isEmpty())
                    showZendeskChatNotification(getString(R.string.app_name), chatMessage);
            } else {
                if (data != null) {
                    if (pageId != null) {
                        if (pageId.equals("13")) {
                            if (StringUtils.hasLengthMany(requestId)) {
                                handleZendeskSdkPush(requestId, data);
                            }
                        } else {
                            sendNotification(data);

                            //  new GenerateNotification(getApplicationContext(),data).execute();

                        }
                    } else {
                        sendNotification(data);
                    }
                } else {

                    if (notification != null)
                        sendNotification(notification);

                }
            }
       // }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        saveToken(s);
        PushUtils.registerWithZendesk();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd hh:mm:ss";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    private void sendSocialNotification(im.getsocial.sdk.notifications.Notification notification) {

        socialNofication = false;
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
                intent = CategoryL1Activity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_CATEGORY_L1);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = CategoryL2Activity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_CATEGORY_L2);
                bundle.putString("catid", actionDatas.get("catid"));
                bundle.putString("scl1id", actionDatas.get("scl1id"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                intent = CatProductActivity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_CATEGORY_L2);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                intent = MainActivity.newIntent(this, AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = TransactionHistoryActivity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_TRANSACTION_HISTORY);
                break;
            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                intent = TransactionDetailsActivity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_TRANS_DETAILS);
                bundle.putString("orderid", actionDatas.get("orderid"));

                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                intent = ProductDetailsActivity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
                bundle.putString("vpid", actionDatas.get("vpid"));

                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                intent = CollectionDetailsActivity.newIntent(this,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_COLLECTION);
                bundle.putString("cid", actionDatas.get("cid"));

                break;
            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                intent = EventActivity.newIntent(this, actionDatas.get("topic"), actionDatas.get("title"),AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_COMMUNITY_EVENT);

                break;

            default:
                intent = SplashActivity.newIntent(this);


        }

        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getText())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notification.getText()))
                .setSmallIcon(R.drawable.ic_dl_logo)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // .setFullScreenIntent(pendingIntent,true)
                .setNumber(++numMessages);

        //  .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

        try {

            if (notification.getAttachment() != null && notification.getAttachment().getImageUrl() != null) {
                URL url = new URL(notification.getAttachment().getImageUrl());
                //  Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bigPicture = BitmapFactory.decodeStream(input);


                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendNotification(Map<String, String> data) {
        /*Pageid_eat_order_post:1,
                Pageid_eat_order_accept:2,
                Pageid_eat_order_preparing:3,
                Pageid_eat_order_Prepared:4,
                Pageid_eat_order_pickedup:5,
                Pageid_eat_order_reached:6,
                Pageid_eat_order_delivered:7,
                Pageid_eat_order_cancel:8,
                Pageid_eat_query_replay:9,
                Pageid_eat_rating:10
                Pageid_order_placed:11
                Pageid_promotion:12
                Pageid_zendesk:13*/
        socialNofication = false;
        Bundle bundle = new Bundle();
        Intent intent = null;
        String pageId = data.get("pageid");
        String date = "2020-08-07 12:32:22";
        if (data.get("date") != null) {
            date = parseDateToddMMyyyy(data.get("date"));
        }

        String title = data.get("title");
        String message = data.get("message");


        if (title==null||title.isEmpty())return;

        if (pageId == null) pageId = "0";

        switch (pageId) {
            case AppConstants.NOTIFY_SPLASH_ACTV:
                intent = new Intent(this, SplashActivity.class);
                break;
            case AppConstants.NOTIFY_SIGN_UP_ACTV:
                intent = new Intent(this, SignUpActivity.class);
                break;
            case AppConstants.NOTIFY_REGISTRATION_ACTV:
                intent = new Intent(this, RegistrationActivity.class);
                break;
            case AppConstants.NOTIFY_ADDRESS_ACTV:
                intent = new Intent(this, GoogleAddressActivity.class);
                break;
            case AppConstants.NOTIFY_MAIN_ACTV:
                intent = new Intent(this, MainActivity.class);
                break;
            case AppConstants.NOTIFY_HOME_FRAG:
                /*HomeFragment*/
                intent = new Intent(this, MainActivity.class);
                break;
            case AppConstants.NOTIFY_REFERRAL_ACTV:
                intent = new Intent(this, ReferralsActivity.class);
                break;
            case AppConstants.NOTIFY_MYACCOUNT_FRAG:
                /*MyAccountFragment*/
                intent = new Intent(this, MainActivity.class);
                break;
            case AppConstants.NOTIFY_CALENDAR_ACTV:
                intent = new Intent(this, CalendarActivity.class);
                intent.putExtra("pageid", pageId);
                intent.putExtra("date", date);
                break;
            case AppConstants.NOTIFY_CART_FRAG:
                //intent = new Intent(this, CartFragment.class);
                intent = new Intent(this, MainActivity.class);
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                intent = new Intent(this, CategoryL1Activity.class);
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = new Intent(this, CategoryL2Activity.class);
                break;
            case AppConstants.NOTIFY_PRODUCTS_FRAG:
                //intent = new Intent(this, ProductsFragment.class);l2
                intent = new Intent(this, CategoryL2Activity.class);
                break;
            case AppConstants.NOTIFY_FAVORITES_ACTV:
                intent = new Intent(this, FavActivity.class);
                break;
            case AppConstants.NOTIFY_ORDER_PLACED_ACTV:
                intent = new Intent(this, OrderPlacedActivity.class);
                break;
            case AppConstants.NOTIFY_SEARCH_FRAG:
                //intent = new Intent(this, SearchFragment.class);
                intent = new Intent(this, MainActivity.class);
                break;
            case AppConstants.NOTIFY_FAQ_ACTV:
                intent = new Intent(this, FaqActivity.class);
                break;
            case AppConstants.NOTIFY_OTP_ACTV:
                intent = new Intent(this, OtpActivity.class);
                break;
            case AppConstants.NOTIFY_PRIVACY_ACTV:
                intent = new Intent(this, PrivacyActivity.class);
                break;
            case AppConstants.NOTIFY_TERMS_AND_CONDITION_ACTV:
                intent = new Intent(this, TermsAndConditionActivity.class);
                break;
            case AppConstants.NOTIFY_SUBSCRIPTION_ACTV:
                intent = new Intent(this, SubscriptionActivity.class);
                break;
            case AppConstants.NOTIFY_UPDATE_ACTV:
                intent = new Intent(this, UpdateActivity.class);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = new Intent(this, TransactionHistoryActivity.class);
                intent.putExtra("pageid", pageId);
                break;


            default:
                intent = new Intent(this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        bundle.putString("pageid", pageId);
        bundle.putString("date", date);
        bundle.putString("title", title);
        bundle.putString("message", message);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setSmallIcon(R.drawable.ic_dl_logo)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // .setFullScreenIntent(pendingIntent,true)
                .setNumber(++numMessages);

        //  .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

        try {
            String picture = data.get("image");
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(message)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(RemoteMessage.Notification notification) {
        socialNofication = false;
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (notification.getTitle()==null)return;



        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                /*  .setContentInfo("Hello")*/
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_dl_logo);

        try {
            if (notification.getImageUrl() != null) {
                String picture = notification.getImageUrl().getPath();
                if (picture != null && !"".equals(picture)) {
                    URL url = new URL(picture);
                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    notificationBuilder.setStyle(
                            new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void saveToken(String token) {
        try {
            AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
            long userIdMain = Integer.parseInt(appPreferencesHelper.getCurrentUserId());

            // if (!MvvmApp.getInstance().onCheckNetWork()) return;
            if (userIdMain == 0) return;
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_FCM_TOKEN, CommonResponse.class, new TokenRequest(String.valueOf(userIdMain), token), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (response.isStatus()) {

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleZendeskSdkPush(String requestId, Map<String, String> data) {
        /*// Initialise the SDK
        // This IntentService could be called and any point. So, if the main app was killed,
        // there won't be any Zendesk login information. Moreover, we presume at this point, that
        // an valid identity was set.
        if (!Zendesk.INSTANCE.isInitialized()) {
            Context context = getApplicationContext();
            Zendesk.INSTANCE.init(context, context.getString(R.string.zd_url), context.getString(R.string.zd_appid), context.getString(R.string.zd_oauth));
            Support.INSTANCE.init(Zendesk.INSTANCE);
        }

        // If the Fragment with the pushed request id is visible,
        // this will cause a reload of the screen.
        // #refreshRequest(id) will return true if it was successful.
        if (Support.INSTANCE.refreshRequest(requestId, getApplicationContext())) {
            return;
        }

        showNotification(requestId, data);*/
        socialNofication = false;

        String pageId = data.get("pageid");
        String title = data.get("title");
        String message = data.get("message");


        Bundle bundle = new Bundle();
        bundle.putString("requestId", requestId);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("requestId", requestId);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                /*  .setContentInfo("Hello")*/
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_dl_logo);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());


    }


    private void showZendeskChatNotification(String title, String message) {


       /* final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final String channelId = getApplicationContext().getResources().getString(R.string.app_name);
        createNotificationChannel(notificationManager, channelId);

        Intent requestIntent = new Intent(this, SplashActivity.class);
        requestIntent.putExtra("chat", true);


        final PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, requestIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_eat)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);*/
        socialNofication = false;
        Bundle bundle = new Bundle();
        bundle.putBoolean("chat", true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("chat", true);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                /*  .setContentInfo("Hello")*/
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_dl_logo);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());


    }

    private void createNotificationChannel(NotificationManager notificationManager, String channelId) {
        socialNofication = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create the notification channel. As per the documentation, "Attempting to create an
            // existing notification channel with its original values performs no operation, so it's safe
            // to perform the above sequence of steps when starting an app."
            // The user-visible name of the channel.
            CharSequence name = getString(R.string.app_name);
            // The user-visible description of the channel.
            String description = getString(R.string.push_notification_fallback_title);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            // Configure the notification channel.
            channel.setDescription(description);
            channel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Intent getDeepLinkIntent(String requestId) {
        socialNofication = false;
        // Utilize SDK's deep linking functionality to get an Intent which opens a specified request.
        // We'd like to achieve a certain behaviour, if the user navigates back from the request activity.
        // Expected: [Request] --> [Request list] -> [MainActivity | HelpFragment]

        // ZendeskDeepLinking.INSTANCE.getRequestIntent automatically pushed the request list activity into
        // backstack. So we just have to add MainActivity.

        final Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        mainActivity.putExtra("requestId", requestId);

        return mainActivity;

        // mainActivity.putExtra(MainActivity.EXTRA_VIEWPAGER_POSITION, MainActivity.POS_HELP);
      /*  return RequestActivity.builder()
                .withRequestId(requestId)
                .deepLinkIntent(getApplicationContext(), mainActivity);*/
    }

    public void testNotify() {
        Bundle bundle = new Bundle();
        Intent intent = null;
        Map<String, String> actionDatas = null;


        intent = new Intent(this, CategoryL1Activity.class);
        //   intent = new Intent("click_action");
        bundle.putString("catid", "1");
        //      intent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        //    intent.setAction(Intent. ACTION_MAIN ) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
       /* intent = new Intent(this, CatProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);*/
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, this.getString(R.string.notification_channel_id))
                .setContentTitle("sadg")
                .setContentText("dasfmhdgf")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("afdshgahb"))
                .setSmallIcon(R.drawable.ic_dl_logo)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // .setFullScreenIntent(pendingIntent,true)
                .setNumber(++numMessages);

        //  .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

        /*try {
                URL url = new URL("");
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(message)
                );

        } catch (IOException e) {
            e.printStackTrace();
        }*/


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    this.getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());


    }


}