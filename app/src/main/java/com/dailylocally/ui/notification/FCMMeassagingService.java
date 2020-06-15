package com.dailylocally.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.ui.account.MyAccountFragment;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.address.add.AddAddressActivity;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.home.HomeFragment;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.signup.registration.TokenRequest;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.PushUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zendesk.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class FCMMeassagingService extends FirebaseMessagingService {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final int NOTIFICATION_ID = 134345;
    private static final String ZD_REQUEST_ID_KEY = "ticket_id";
    private static final String ZD_MESSAGE_KEY = "message";
    private int numMessages = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();


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
                    }
                } else {
                    sendNotification(data);
                }
            } else {

                if (notification != null)
                    sendNotification(notification);

            }
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        saveToken(s);
        PushUtils.registerWithZendesk();
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

        Bundle bundle = new Bundle();
        Intent intent = null;
        String pageId = data.get("pageid");
        String title = data.get("title");
        String message = data.get("message");

        if (pageId == null) pageId = "0";

        switch (pageId) {
            case "1":
                intent = new Intent(this, SplashActivity.class);
                break;
            case "2":
                intent = new Intent(this, SignUpActivity.class);
                break;
            case "3":
                intent = new Intent(this, RegistrationActivity.class);
                break;
            case "4":
                intent = new Intent(this, AddAddressActivity.class);
                break;
            case "5":
                intent = new Intent(this, MainActivity.class);
                break;
            case "6":
                /*HomeFragment*/
                break;
            case "7":
                intent = new Intent(this, ReferralsActivity.class);
                break;
            case "8":
                /*MyAccountFragment*/
                break;
            case "9":
                intent = new Intent(this, CalendarActivity.class);
                break;
            case "11":
                intent = new Intent(this, CategoryL1Activity.class);
                break;
                case "12":
                intent = new Intent(this, CategoryL2Activity.class);
                break;

            default:
                intent = new Intent(this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        intent.putExtras(bundle);

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

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);


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
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(String.valueOf(userIdMain), token), new Response.Listener<CommonResponse>() {
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
        }catch (Exception e){
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

    private void showNotification(String requestId, Map<String, String> data) {

        String title = data.get("title");
        String message = data.get("message");

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final String channelId = getApplicationContext().getResources().getString(R.string.app_name);
        createNotificationChannel(notificationManager, channelId);

        final Intent requestIntent = getDeepLinkIntent(requestId);
        final PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, requestIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_dl_logo)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
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

        Bundle bundle = new Bundle();
        bundle.putBoolean("chat", true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("chat", true);
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

    private void createNotificationChannel(NotificationManager notificationManager, String channelId) {
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

}