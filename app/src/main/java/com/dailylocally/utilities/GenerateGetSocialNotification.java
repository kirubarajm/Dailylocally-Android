package com.dailylocally.utilities;

import android.annotation.TargetApi;
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

import androidx.core.app.NotificationCompat;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class GenerateGetSocialNotification extends AsyncTask<String, Void, Bitmap> {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final int NOTIFICATION_ID = 134345;
    private static final String ZD_REQUEST_ID_KEY = "ticket_id";
    private static final String ZD_MESSAGE_KEY = "message";
    im.getsocial.sdk.notifications.Notification snotification;
    private Context mContext;
    private String title, message, imageUrl;
    private int numMessages = 0;

    public GenerateGetSocialNotification(Context context, im.getsocial.sdk.notifications.Notification notification) {
        super();
        this.mContext = DailylocallyApp.getInstance();
        this.snotification = notification;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (snotification.getAttachment() != null)
            if (snotification.getAttachment().getImageUrl() != null) {
                InputStream in;
                try {
                    URL url = new URL(snotification.getAttachment().getImageUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    in = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(in);
                    return myBitmap;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);


        Bundle bundle = new Bundle();
        Intent intent = null;
        Map<String, String> actionDatas = null;

        String pageId = "0";
        if (snotification.getAction() != null) {
            actionDatas = snotification.getAction().getData();
            if (snotification.getAction().getData().get("pageid") != null) {
                pageId = snotification.getAction().getData().get("pageid");
            }

        }

        if (pageId == null) pageId = "0";
        switch (pageId) {
            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                intent = CategoryL1Activity.newIntent(mContext);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = CategoryL2Activity.newIntent(mContext);
                bundle.putString("catid", actionDatas.get("catid"));
                bundle.putString("scl1id", actionDatas.get("scl1id"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                intent = CatProductActivity.newIntent(mContext);
                bundle.putString("catid", actionDatas.get("catid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                intent = MainActivity.newIntent(mContext, AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = TransactionHistoryActivity.newIntent(mContext);
                break;
            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                intent = TransactionDetailsActivity.newIntent(mContext);
                bundle.putString("orderid", actionDatas.get("orderid"));

                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                intent = ProductDetailsActivity.newIntent(mContext);
                bundle.putString("vpid", actionDatas.get("vpid"));

                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                intent = CollectionDetailsActivity.newIntent(mContext);
                bundle.putString("cid", actionDatas.get("cid"));

                break;
            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                intent = EventActivity.newIntent(mContext, actionDatas.get("topic"), actionDatas.get("title"));

                break;

            default:
                intent = SplashActivity.newIntent(mContext);


        }

        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, mContext.getString(R.string.notification_channel_id))
                .setContentTitle(snotification.getTitle())
                .setContentText(snotification.getText())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(snotification.getText()))
                .setSmallIcon(R.drawable.ic_dl_logo)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("")
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_dl_logo))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // .setFullScreenIntent(pendingIntent,true)
                .setNumber(++numMessages);

        //  .setStyle(new NotificationCompat.BigTextStyle().bigText(message))


        if (snotification.getAttachment() != null && snotification.getAttachment().getImageUrl() != null) {

            notificationBuilder.setStyle(
                    new NotificationCompat.BigPictureStyle().bigPicture(result));

        }


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    mContext.getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
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