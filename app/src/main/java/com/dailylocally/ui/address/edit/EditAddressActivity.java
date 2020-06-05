package com.dailylocally.ui.address.edit;

import android.Manifest;
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

import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.dailylocally.BR;
import com.dailylocally.R;

import com.dailylocally.databinding.ActivityEditAddressBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.GpsUtils;

import com.dailylocally.utilities.SingleShotLocationProvider;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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

public class EditAddressActivity extends BaseActivity<ActivityEditAddressBinding, EditAddressViewModel> implements EditAddressNavigator, LocationListener {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int ADDRESS_SEARCH_CODE = 15545;
    public ActivityEditAddressBinding mActivityEditAddressBinding;
    @Inject
    public EditAddressViewModel mEditAddressViewModel;
    protected LocationManager locationManager;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    LatLng lastPosition;
    boolean isFirstTime;
    Location mLocation;
    String aid;
    String atype;
    boolean isGPS;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_EDIT_ADDRESS;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, EditAddressActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.editAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public EditAddressViewModel getViewModel() {

        return mEditAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void addressSaved() {
        hideKeyboard();
        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void emptyFields() {


        Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void myLocationn() {
        turnOnGps();
    }

    @Override
    public void setLatLng(String lat, String lng) {
        if (map != null) {
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            lastPosition = latLng;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        }
    }

    @Override
    public void searchAddress() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.map_key));
        }

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME, com.google.android.libraries.places.api.model.Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, ADDRESS_SEARCH_CODE);
    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEditAddressBinding = getViewDataBinding();
        mEditAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);
        isFirstTime = true;

        //check gps and turn on gps alert
        new GpsUtils(this, AppConstants.GPS_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                isGPS = isGPSEnable;
            }
        });


        Intent intent = getIntent();

       aid=mEditAddressViewModel.getDataManager().getAddressId();
       atype="1";

        mEditAddressViewModel.fetchUserDetails();


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                initCameraIdle();
                if (lastPosition != null)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 20));
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    private void initCameraIdle() {
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                center = map.getCameraPosition().target;
                getAddressFromLocation(center.latitude, center.longitude);
            }
        });
    }

    public void turnOnGps() {

        new GpsUtils(this, AppConstants.GPS_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(EditAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                    } else {
                        initCameraIdle();
                        getLocation();
                    }

                } else {

                    turnOnGps();
                }
            }
        });

    }

    public void getLocation() {


        SingleShotLocationProvider.requestSingleUpdate(EditAddressActivity.this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {


                        LatLng latLng = new LatLng(location.latitude, location.longitude);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        initCameraIdle();

                    }
                });
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        new AsyncTaskAddress().execute(latitude, longitude);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_SEARCH_CODE) {
            if (resultCode == RESULT_OK) {
                //   Place place = PlaceAutocomplete.getPlace(this, data);

                Place place = Autocomplete.getPlaceFromIntent(data);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 20));
                initCameraIdle();

            /*    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                initCameraIdle();*/

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                printToast("Error in retrieving place info");

            }
        }
    }

    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // mEditAddressViewModel.fetchAddress(aid);
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    @Override
    public void canceled() {

    }

    private class AsyncTaskAddress extends AsyncTask<Double, Address, Address> {


        @Override
        protected Address doInBackground(Double... doubles) {
            Geocoder geocoder = new Geocoder(EditAddressActivity.this, Locale.ENGLISH);

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(doubles[0], doubles[1], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null)
                if (addresses.size() > 0) {
                    Address fetchedAddress = addresses.get(0);

                    return fetchedAddress;

                }


            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Address fetchedAddress) {
            super.onPostExecute(fetchedAddress);

            if (fetchedAddress != null) {
                mEditAddressViewModel.locationAddress.set(fetchedAddress.getAddressLine(0));

                mEditAddressViewModel.area.set(fetchedAddress.getSubLocality());
                // mEditAddressViewModel.house.set(fetchedAddress.getFeatureName());


                mEditAddressViewModel.saveAddress(String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode(), aid);


                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                }

            } else {

                if (mEditAddressViewModel.locationAddress.get() == null || mEditAddressViewModel.locationAddress.get().isEmpty())
                    printToast("Unable to find your address please mark your location on map..");
            }
        }
    }


}