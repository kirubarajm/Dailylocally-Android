package com.dailylocally.ui.address.viewAddress;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityViewAddressBinding;
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;


public class ViewAddressActivity extends BaseActivity<ActivityViewAddressBinding, ViewAddressViewModel> implements
        ViewAddressNavigator {


    public ActivityViewAddressBinding mActivityViewAddressBinding;
    @Inject
    public ViewAddressViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;
    SupportMapFragment mapFragment;
    GoogleMap map;
    Double lat,lon;

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

    public static Intent newIntent(Context context) {
        return new Intent(context, ViewAddressActivity.class);
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
        Intent intent = GoogleAddressActivity.newIntent(this);
        intent.putExtra("edit", "1");
        startActivity(intent);
    }

    @Override
    public void getAddressSuccess(UserAddressResponse.Result result) {
        try {
            if (result!=null){
                lat = result.getLat();
                lon = result.getLon();

                mActivityViewAddressBinding.txtLandmark.setText(result.getLandmark());
                mActivityViewAddressBinding.txtLandmark.setText(result.getLandmark());
                if (result.getAddressType()==1){
                    mActivityViewAddressBinding.txtAddressType.setText("Apartment Or Gated Society");


                    String completeAddress="No."+result.getFlatHouseNo()+", "+result.getBlockName()+", "+result.getApartmentName()+","+result.getCompleteAddress();
                    mActivityViewAddressBinding.txtFullAddress.setText(completeAddress);


                }else {
                    mActivityViewAddressBinding.txtAddressType.setText("Independent House");
                    String completeAddress="No."+result.getPlotHouseNo()+", Floor-"+result.getFloor()+", "+result.getCompleteAddress();
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
                   //     markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        map = googleMap;
                        map.clear();
                        map.getUiSettings().setZoomControlsEnabled(true);
                        //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        map.addMarker(markerOptions);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e) {
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

        analytics = new Analytics(this, pageName);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentViewAddress);
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