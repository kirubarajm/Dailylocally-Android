package com.dailylocally.ui.address.viewAddress;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityViewAddressBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.address.type.CommunitySearchActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import javax.inject.Inject;


public class ViewAddressActivity extends BaseActivity<ActivityViewAddressBinding, ViewAddressViewModel> implements
        ViewAddressNavigator {


    public ActivityViewAddressBinding mActivityViewAddressBinding;
    @Inject
    public ViewAddressViewModel mAddAddressViewModel;

    SupportMapFragment mapFragment;
    GoogleMap map;
    Double lat, lon;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(ViewAddressActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    public static Intent newIntent(Context context,String fromPage,String ToPage) {
        Intent intent = new Intent(context, ViewAddressActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, ToPage);
        return intent;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_address;
    }

    @Override
    public ViewAddressViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void updateClick() {
        Intent intents = new Intent();
        Intent intent = AddressNewActivity.newIntent(this,intents.getExtras().getString(AppConstants.FROM),AppConstants.SCREEN_EDIT_ADDRESS);
        intent.putExtra("edit", "1");
        intent.putExtra("newuser", false);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void getAddressSuccess(UserAddressResponse.Result result) {
        try {
            if (result != null) {
                lat = result.getLat();
                lon = result.getLon();

                mActivityViewAddressBinding.txtLandmark.setText(result.getLandmark());
                mActivityViewAddressBinding.txtLandmark.setText(result.getLandmark());
                if (result.getAddressType() == 1) {
                    mActivityViewAddressBinding.txtAddressType.setText("Apartment Or Gated Society");


                    String completeAddress = "No." + result.getFlatHouseNo() + ", " + result.getBlockName() + ", " + result.getApartmentName() + "," + result.getCompleteAddress();
                    mActivityViewAddressBinding.txtFullAddress.setText(completeAddress);


                } else {
                    mActivityViewAddressBinding.txtAddressType.setText("Independent House");
                    String completeAddress = "No." + result.getPlotHouseNo() + ", Floor-" + result.getFloor() + ", " + result.getCompleteAddress();
                    mActivityViewAddressBinding.txtFullAddress.setText(completeAddress);
                }
            }

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    try {
                        LatLng latLng = new LatLng(lat, lon);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        Bitmap bitmap = getBitmapFromVectorDrawable(ViewAddressActivity.this, R.drawable.ic_map_marker);
                        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                        markerOptions.icon(descriptor);


                        View googleLogo = mActivityViewAddressBinding.frame.findViewWithTag("GoogleWatermark");
                        RelativeLayout.LayoutParams glLayoutParams = (RelativeLayout.LayoutParams) googleLogo.getLayoutParams();
                        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        glLayoutParams.setMargins(10, 0, 0, 100);
                        googleLogo.setLayoutParams(glLayoutParams);

                        map = googleMap;
                        map.clear();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        map.addMarker(markerOptions);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAddressFailure() {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentViewAddress);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_ADDRESS_VIEW);
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
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

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void canceled() {

    }
}