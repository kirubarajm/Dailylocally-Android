package com.dailylocally.ui.joinCommunity.communityLocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityAddressBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.fandsupport.FeedbackSupportActivity;
import com.dailylocally.ui.joinCommunity.CommunityActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.GpsUtils;
import com.dailylocally.utilities.SingleShotLocationProvider;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.quicksand.ButtonTextView;
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
import java.util.Objects;

import javax.inject.Inject;

public class CommunityAddressActivity extends BaseActivity<ActivityCommunityAddressBinding, CommunityAddressViewModel>
        implements CommunityAddressNavigator, LocationListener {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int ADDRESS_SEARCH_CODE = 15545;
    public ActivityCommunityAddressBinding mActivityCommunityBinding;
    @Inject
    public CommunityAddressViewModel mAddAddressViewModel;
    protected LocationManager locationManager;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    Location mLocation;
    Dialog locationDialog;
    ProgressDialog dialog;
    FusedLocationProviderClient fusedLocationClient;

    String address = null;
    String pinCode = null;
    String aid = null,edit=null;
    String strCommunityLat="",strCommunityLng="";


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(CommunityAddressActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, CommunityAddressActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.communityAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_address;
    }

    @Override
    public CommunityAddressViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addressSaved() {
        Intent intent = MainActivity.newIntent(CommunityAddressActivity.this,"","",AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.SCREEN_NAME_HOME);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
        hideKeyboard();
    }

    @Override
    public void emptyFields() {
        Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validationForAddress() {
        if (mActivityCommunityBinding.location.getText().toString().equals("")){
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void myLocationn() {
        turnOnGps();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showAlert(String title, String message,String locationAddress,String area,String lat,
                          String lng,String pinCode) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message) .setTitle(title);

        //Setting message manually and performing action on button click
        builder.setCancelable(true)
                .setPositiveButton("Get in touch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       // confirmLocationClick(locationAddress,area,lat,lng,pinCode);
                        Intent intent = FeedbackSupportActivity.newIntent(CommunityAddressActivity.this,
                                AppConstants.SCREEN_NAME_ADDRESS,AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();



        }

    public void showLocationDialog() {
        locationDialog = new Dialog(this);
        locationDialog.setCancelable(false);
        locationDialog.setContentView(R.layout.dialog_get_location);

        ButtonTextView text = locationDialog.findViewById(R.id.allowgps);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                turnOnGps();

            }
        });

        ButtonTextView dialogButton = locationDialog.findViewById(R.id.cancelgps);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                finish();

            }
        });

        locationDialog.show();

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void searchAddress() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.map_key));
        }

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, ADDRESS_SEARCH_CODE);
    }

    @Override
    public void editLocality() {
        mActivityCommunityBinding.txtSubLocality.setFocusable(true);
        mActivityCommunityBinding.txtSubLocality.requestFocus();
        mActivityCommunityBinding.txtSubLocality.setEnabled(true);
    }

    @Override
    public void nextClick() {
        if (!mActivityCommunityBinding.txtSubLocality.getText().toString().trim().equals("")) {

            Intent intents = new Intent();
            Intent intent = AddressNewActivity.newIntent(this,AppConstants.SCREEN_NAME_ADDRESS,AppConstants.SCREEN_EDIT_ADDRESS);
            intent.putExtra("locationAddress", mActivityCommunityBinding.txtSubLocality.getText().toString());
            intent.putExtra("lat",strCommunityLat);
            intent.putExtra("lon",strCommunityLng);
            intent.putExtra("pinCode",pinCode);
            intent.putExtra("area",mActivityCommunityBinding.txtSubLocality.getText().toString());
            intent.putExtra("aid",mAddAddressViewModel.aId.get());
            intent.putExtra("edit",edit);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        }else {
            Toast.makeText(this, "Please fill area", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getAddressSuccess(UserAddressResponse.Result result) {
        try {
            if (result!=null) {
                mActivityCommunityBinding.house.setText(result.getFlatHouseNo());
                mActivityCommunityBinding.area.setText(result.getLandmark());
                mActivityCommunityBinding.landmark.setText(result.getLandmark());
                mActivityCommunityBinding.location.setText(result.getGoogleAddress());

                LatLng latLng = new LatLng(result.getLat(), result.getLon());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                initCameraIdle();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAddressFailure() {

    }

    @Override
    public void googleAddressClick() {
        mActivityCommunityBinding.location.setText(mAddAddressViewModel.locationAddress.get());
    }

    @Override
    public void confirmLocationClick(String locationAddress, String area,
                                     String lat,String lon,String pinCode) {
        Intent intents = new Intent();
        Intent intent = AddressNewActivity.newIntent(this,AppConstants.SCREEN_NAME_ADDRESS,AppConstants.SCREEN_EDIT_ADDRESS);
        intent.putExtra("locationAddress",locationAddress);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putExtra("pinCode",pinCode);
        intent.putExtra("area",area);
        intent.putExtra("aid",mAddAddressViewModel.aId.get());
        intent.putExtra("edit",edit);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCommunityBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        //  startLocationTracking();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            //aid = bundle.getString("aid");
            edit = bundle.getString("edit");
            if (edit!=null){
                mAddAddressViewModel.fetchUserDetails();
                mAddAddressViewModel.flagAddressEdit.set(true);
            }
        }

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");
        // dialog.show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                //map.getUiSettings().setZoomControlsEnabled(true);
                if (edit==null) {
                    turnOnGps();
                    initCameraIdle();
                }
            }
        });


    }

    public void turnOnGps() {

        new GpsUtils(this, AppConstants.GPS_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(CommunityAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CommunityAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CommunityAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);
                    } else {
                        if (!dialog.isShowing())
                            dialog.show();
                        getUserLocation();
                    }
                } else {
                    showLocationDialog();
                }
            }
        });

    }

    private void getUserLocation() {

        SingleShotLocationProvider.requestSingleUpdate(CommunityAddressActivity.this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                        if (dialog != null)
                            if (dialog.isShowing())
                                dialog.dismiss();
                        if (location != null)
                            if (map != null) {

                                //LatLng latLng = new LatLng(12.99447060,80.25593567);

                                LatLng latLng = new LatLng(location.latitude, location.longitude);
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                initCameraIdle();
                            }
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstants.GPS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    turnOnGps();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                }
                return;
            }
        }
    }

    private void initCameraIdle() {
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                center = map.getCameraPosition().target;

                if (center.latitude != 0.0)
                    getAddressFromLocation(center.latitude, center.longitude);
                //getAddressFromLocation(12.99447060,80.25593567);
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

                if (place.getLatLng() != null) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14);
                    if (map != null)
                        map.animateCamera(cameraUpdate);
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                printToast("Error in retrieving place info");

            }
        } else if (requestCode == AppConstants.GPS_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {


                if (!dialog.isShowing())
                    dialog.show();

                turnOnGps();


            } else {

                showLocationDialog();

            }


        } else if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {


        }
    }

    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        if (map != null) {
            if (location != null) {
                if (dialog.isShowing())
                    dialog.dismiss();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                initCameraIdle();
                if (locationManager != null)
                    locationManager.removeUpdates(this);
            }
        }
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
        if (dialog.isShowing()) dialog.dismiss();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
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

    private class AsyncTaskAddress extends AsyncTask<Double, Address, Address> {


        @Override
        protected Address doInBackground(Double... doubles) {
            Geocoder geocoder = new Geocoder(CommunityAddressActivity.this, Locale.ENGLISH);

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

            try {

            if (fetchedAddress != null) {

                address = fetchedAddress.getAddressLine(0);

                mAddAddressViewModel.locationAddress.set(address);

                ColorStateList colorStateList;
                if (fetchedAddress.getSubLocality()!=null){
                    mAddAddressViewModel.area.set(fetchedAddress.getSubLocality());
                    mActivityCommunityBinding.txtSubLocality.setEnabled(false);
                    mActivityCommunityBinding.txtSubLocality.setHint("");
                    colorStateList = ColorStateList.valueOf(Color.parseColor("#000000"));
                    ViewCompat.setBackgroundTintList(mActivityCommunityBinding.txtSubLocality, colorStateList);
                    mActivityCommunityBinding.txtMessage.setVisibility(View.GONE);
                    mActivityCommunityBinding.imgMarker.setBackgroundResource(R.drawable.ic_group_2);

                }else {
                    mAddAddressViewModel.area.set("");
                    mActivityCommunityBinding.txtSubLocality.setEnabled(true);
                    mActivityCommunityBinding.txtSubLocality.setHint("Unable to identify location");
                    mActivityCommunityBinding.txtSubLocality.setHintTextColor(Color.parseColor("#FF0001"));
                    mActivityCommunityBinding.txtMessage.setVisibility(View.VISIBLE);
                    printToast("Unable to find your area please mark your location on map..");

                    colorStateList = ColorStateList.valueOf(Color.parseColor("#FF0001"));
                    ViewCompat.setBackgroundTintList(mActivityCommunityBinding.txtSubLocality, colorStateList);

                    mActivityCommunityBinding.imgMarker.setBackgroundResource(R.drawable.ic_group_3);
                }


                mAddAddressViewModel.saveAddress(address,fetchedAddress.getSubLocality(),String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode());

                strCommunityLat = String.valueOf(fetchedAddress.getLatitude());
                strCommunityLng = String.valueOf(fetchedAddress.getLongitude());
                pinCode = fetchedAddress.getPostalCode();

                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");

                }

            } else {
            }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}