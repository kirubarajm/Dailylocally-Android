package com.dailylocally.ui.address.addressNew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityAddressNewBinding;
import com.dailylocally.ui.address.add.UserAddressResponse;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.GpsUtils;
import com.dailylocally.utilities.SingleShotLocationProvider;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.poppins.ButtonTextView;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddressNewActivity extends BaseActivity<ActivityAddressNewBinding, AddressNewViewModel> implements AddressNewNavigator {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int ADDRESS_SEARCH_CODE = 15545;
    public ActivityAddressNewBinding mActivityAddressNewBinding;
    @Inject
    public AddressNewViewModel mAddAddressViewModel;
    protected LocationManager locationManager;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    Location mLocation;

    FusedLocationProviderClient fusedLocationClient;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    String address = null;
    String aid = null;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(AddressNewActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, AddressNewActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.addressNewViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_new;
    }

    @Override
    public AddressNewViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void apartmentClick() {
        mAddAddressViewModel.apartmentOrIndividual.set(true);
    }

    @Override
    public void individualClick() {
        mAddAddressViewModel.apartmentOrIndividual.set(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressNewBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        //  startLocationTracking();

        analytics = new Analytics(this, pageName);


        // dialog.show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
            }
        });

        mAddAddressViewModel.apartmentOrIndividual.set(true);
        mActivityAddressNewBinding.radio1.setChecked(true);
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