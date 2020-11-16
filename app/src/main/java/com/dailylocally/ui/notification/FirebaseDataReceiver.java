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

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import im.getsocial.sdk.GetSocial;
import im.getsocial.sdk.Notifications;
import im.getsocial.sdk.notifications.NotificationContext;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;
import im.getsocial.sdk.notifications.OnNotificationReceivedListener;


public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final int NOTIFICATION_ID = 134345;
    private static final String ZD_REQUEST_ID_KEY = "ticket_id";
    private static final String ZD_MESSAGE_KEY = "message";
    private final String TAG = "FirebaseDataReceiver";
    private int numMessages = 0;
    private Boolean socialNofication = true;

    public void onReceive(Context context, Intent intent) {

        /*Intent intent1 = new Intent(context, CategoryL1Activity.class);
        intent1.putExtra("catid", "1");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);*/

        /*Notifications.setOnNotificationReceivedListener(new OnNotificationReceivedListener() {
            @Override
            public void onNotificationReceived(im.getsocial.sdk.notifications.Notification snotification) {
                // socialNofication = true;
                //   sendSocialNotification(snotification);
                // new GenerateGetSocialNotification(getApplicationContext(),snotification).execute();
               *//* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendSocialNotification(context,snotification);
                    }
                }, 1);*//*


            }
        });*/

        /*Notifications.setOnNotificationClickedListener(new OnNotificationClickedListener() {
            @Override
            public void onNotificationClicked(im.getsocial.sdk.notifications.Notification notification, NotificationContext notificationContext) {
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
                        intent = CategoryL1Activity.newIntent(context);
                        bundle.putString("catid", actionDatas.get("catid"));
                        break;
                    case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                        intent = CategoryL2Activity.newIntent(context);
                        bundle.putString("catid", actionDatas.get("catid"));
                        bundle.putString("scl1id", actionDatas.get("scl1id"));
                        break;
                    case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                        intent = CatProductActivity.newIntent(context);
                        bundle.putString("catid", actionDatas.get("catid"));
                        break;
                    case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                        intent = MainActivity.newIntent(context, AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                        break;
                    case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                        intent = TransactionHistoryActivity.newIntent(context);
                        break;
                    case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                        intent = TransactionDetailsActivity.newIntent(context);
                        bundle.putString("orderid", actionDatas.get("orderid"));

                        break;
                    case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                        intent = ProductDetailsActivity.newIntent(context);
                        bundle.putString("vpid", actionDatas.get("vpid"));

                        break;
                    case AppConstants.NOTIFY_COLLECTION_ACTV:
                        intent = CollectionDetailsActivity.newIntent(context);
                        bundle.putString("cid", actionDatas.get("cid"));

                        break;
                    case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                        intent = EventActivity.newIntent(context, actionDatas.get("topic"), actionDatas.get("title"));

                        break;

                    default:
                        intent = SplashActivity.newIntent(context);


                }

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent,bundle);
            }
        });*/
        //     context.sendBroadcast(intent);


    }

    private void sendSocialNotification(Context context, im.getsocial.sdk.notifications.Notification notification) {

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
                intent = CategoryL1Activity.newIntent(context,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_CATEGORY_L1);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = CategoryL2Activity.newIntent(context,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_CATEGORY_L2);
                bundle.putString("catid", actionDatas.get("catid"));
                bundle.putString("scl1id", actionDatas.get("scl1id"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                intent = CatProductActivity.newIntent(context,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.EVENT_CATEGORY_PAGE);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                intent = MainActivity.newIntent(context, AppConstants.SCREEN_NAME_NOTIFICATION, AppConstants.NOTIFY_COMMUNITY_ACTV);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = TransactionHistoryActivity.newIntent(context);
                break;
            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                intent = TransactionDetailsActivity.newIntent(context);
                bundle.putString("orderid", actionDatas.get("orderid"));

                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                intent = ProductDetailsActivity.newIntent(context,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
                bundle.putString("vpid", actionDatas.get("vpid"));

                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                intent = CollectionDetailsActivity.newIntent(context,AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_COLLECTION);
                bundle.putString("cid", actionDatas.get("cid"));

                break;
            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                intent = EventActivity.newIntent(context, actionDatas.get("topic"), actionDatas.get("title"),AppConstants.SCREEN_NAME_NOTIFICATION,AppConstants.SCREEN_NAME_COMMUNITY_EVENT);

                break;

            default:
                intent = SplashActivity.newIntent(context);


        }

        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
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
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM);
        // .setFullScreenIntent(pendingIntent,true)


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

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    context.getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
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