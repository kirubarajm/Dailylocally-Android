package com.dailylocally.utilities;

import android.annotation.TargetApi;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.dailylocally.R;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.calendarView.CalendarActivity;
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
import com.dailylocally.ui.signup.tandc.TermsAndConditionActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class GenerateNotification extends AsyncTask<String, Void, Bitmap> {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final int NOTIFICATION_ID = 134345;
    private static final String ZD_REQUEST_ID_KEY = "ticket_id";
    private static final String ZD_MESSAGE_KEY = "message";
    Map<String, String> data;
    private Context mContext;
    private String title, message, imageUrl;
    private int numMessages = 0;

    public GenerateNotification (Context context, Map<String, String> data) {
        super();
        this.mContext = context;
        this.data = data;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

            if ( data.get("image") != null) {
                InputStream in;
                try {
                    URL url = new URL( data.get("image"));
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
        String pageId = data.get("pageid");
        String date = "2020-08-07 12:32:22";
        if (data.get("date") != null) {
            date = parseDateToddMMyyyy(data.get("date"));
        }

        String title = data.get("title");
        String message = data.get("message");

        if (pageId == null) pageId = "0";

        switch (pageId) {
            case AppConstants.NOTIFY_SPLASH_ACTV:
                intent = new Intent(mContext, SplashActivity.class);
                break;
            case AppConstants.NOTIFY_SIGN_UP_ACTV:
                intent = new Intent(mContext, SignUpActivity.class);
                break;
            case AppConstants.NOTIFY_REGISTRATION_ACTV:
                intent = new Intent(mContext, RegistrationActivity.class);
                break;
            case AppConstants.NOTIFY_ADDRESS_ACTV:
                intent = new Intent(mContext, GoogleAddressActivity.class);
                break;
            case AppConstants.NOTIFY_MAIN_ACTV:
                intent = new Intent(mContext, MainActivity.class);
                break;
            case AppConstants.NOTIFY_HOME_FRAG:
                /*HomeFragment*/
                intent = new Intent(mContext, MainActivity.class);
                break;
            case AppConstants.NOTIFY_REFERRAL_ACTV:
                intent = new Intent(mContext, ReferralsActivity.class);
                break;
            case AppConstants.NOTIFY_MYACCOUNT_FRAG:
                /*MyAccountFragment*/
                intent = new Intent(mContext, MainActivity.class);
                break;
            case AppConstants.NOTIFY_CALENDAR_ACTV:
                intent = new Intent(mContext, CalendarActivity.class);
                intent.putExtra("pageid", pageId);
                intent.putExtra("date", date);
                break;
            case AppConstants.NOTIFY_CART_FRAG:
                //intent = new Intent(mContext, CartFragment.class);
                intent = new Intent(mContext, MainActivity.class);
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                intent = new Intent(mContext, CategoryL1Activity.class);
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = new Intent(mContext, CategoryL2Activity.class);
                break;
            case AppConstants.NOTIFY_PRODUCTS_FRAG:
                //intent = new Intent(mContext, ProductsFragment.class);l2
                intent = new Intent(mContext, CategoryL2Activity.class);
                break;
            case AppConstants.NOTIFY_FAVORITES_ACTV:
                intent = new Intent(mContext, FavActivity.class);
                break;
            case AppConstants.NOTIFY_ORDER_PLACED_ACTV:
                intent = new Intent(mContext, OrderPlacedActivity.class);
                break;
            case AppConstants.NOTIFY_SEARCH_FRAG:
                //intent = new Intent(mContext, SearchFragment.class);
                intent = new Intent(mContext, MainActivity.class);
                break;
            case AppConstants.NOTIFY_FAQ_ACTV:
                intent = new Intent(mContext, FaqActivity.class);
                break;
            case AppConstants.NOTIFY_OTP_ACTV:
                intent = new Intent(mContext, OtpActivity.class);
                break;
            case AppConstants.NOTIFY_PRIVACY_ACTV:
                intent = new Intent(mContext, PrivacyActivity.class);
                break;
            case AppConstants.NOTIFY_TERMS_AND_CONDITION_ACTV:
                intent = new Intent(mContext, TermsAndConditionActivity.class);
                break;
            case AppConstants.NOTIFY_SUBSCRIPTION_ACTV:
                intent = new Intent(mContext, SubscriptionActivity.class);
                break;
            case AppConstants.NOTIFY_UPDATE_ACTV:
                intent = new Intent(mContext, UpdateActivity.class);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = new Intent(mContext, TransactionHistoryActivity.class);
                intent.putExtra("pageid", pageId);
                break;


            default:
                intent = new Intent(mContext, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        bundle.putString("pageid", pageId);
        bundle.putString("date", date);
        bundle.putString("title", title);
        bundle.putString("message", message);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, mContext.getString(R.string.notification_channel_id))
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
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_dl_logo))
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
}