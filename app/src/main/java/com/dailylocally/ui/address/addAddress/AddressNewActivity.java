package com.dailylocally.ui.address.addAddress;

import android.Manifest;
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
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityAddressNewBinding;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.address.saveAddress.SaveAddressActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.GpsUtils;
import com.dailylocally.utilities.SingleShotLocationProvider;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.poppins.ButtonTextView;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class AddressNewActivity extends BaseActivity<ActivityAddressNewBinding, AddressNewViewModel> implements
        AddressNewNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


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
    Dialog locationDialog;
    ProgressDialog dialog;

    FusedLocationProviderClient fusedLocationClient;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    String address = null;
    String aid = null;
    Bundle bundle = null;
    String lat="",lon="", googleAddress = "",pinCode = "",area="",aId="",edit="";


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
        mActivityAddressNewBinding.edtApartmentName.requestFocus();
    }

    @Override
    public void individualClick() {
        mAddAddressViewModel.apartmentOrIndividual.set(false);
        mActivityAddressNewBinding.edtHousePlotNo.requestFocus();
    }

    @Override
    public void confirmClick() {
        if (validation()){

        String apartment = mActivityAddressNewBinding.edtApartmentName.getText().toString();
        String towerBlock = mActivityAddressNewBinding.edtTowerBlock.getText().toString();
        String houseFlatNo = mActivityAddressNewBinding.edtFlatHouseNo.getText().toString();

        String housePlatNo = mActivityAddressNewBinding.edtHousePlotNo.getText().toString();
        String floor = mActivityAddressNewBinding.edtFloor.getText().toString();

        String address = mActivityAddressNewBinding.edtAddress.getText().toString();
        String landmark = mActivityAddressNewBinding.edtLandmark.getText().toString();
        String apartmentOrIndividual="";
        if (mActivityAddressNewBinding.radio1.isChecked()){
            apartmentOrIndividual = "1";
        }else {
            apartmentOrIndividual = "2";
        }

        Intent intent = SaveAddressActivity.newIntent(AddressNewActivity.this);
        intent.putExtra("apartmentOrIndividual",apartmentOrIndividual);
        intent.putExtra("apartment",apartment);
        intent.putExtra("towerBlock",towerBlock);
        intent.putExtra("houseFlatNo",houseFlatNo);
        intent.putExtra("housePlatNo",housePlatNo);
        intent.putExtra("floor",floor);
        intent.putExtra("address",address);
        intent.putExtra("landmark",landmark);
        intent.putExtra("googleAddress",googleAddress);
        intent.putExtra("pinCode",pinCode);
        intent.putExtra("area",area);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putExtra("edit",edit);
        intent.putExtra("aid",mAddAddressViewModel.aId.get());
        startActivity(intent);
        }
    }

    @Override
    public void getAddressSuccess(UserAddressResponse.Result result) {
        try {
            if (result!=null){
                if (result.getAddressType()==1){
                    mActivityAddressNewBinding.radio1.setChecked(true);
                    mAddAddressViewModel.apartmentOrIndividual.set(true);
                    mActivityAddressNewBinding.edtApartmentName.setText(result.getApartmentName());
                    mActivityAddressNewBinding.edtTowerBlock.setText(result.getBlockName());
                    mActivityAddressNewBinding.edtFlatHouseNo.setText(result.getFlatHouseNo());
                }else {
                    mAddAddressViewModel.apartmentOrIndividual.set(false);
                    mActivityAddressNewBinding.radio2.setChecked(true);
                    mActivityAddressNewBinding.edtHousePlotNo.setText(result.getPlotHouseNo());
                    mActivityAddressNewBinding.edtFloor.setText(result.getFloor());
                }
                mActivityAddressNewBinding.edtAddress.setText(result.getCompleteAddress());
                mActivityAddressNewBinding.edtLandmark.setText(result.getLandmark());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAddressFailure() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressNewBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        //analytics = new Analytics(this, pageName);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        bundle = getIntent().getExtras();
        if (bundle!=null){
            lat = bundle.getString("lat");
            lon = bundle.getString("lon");
            googleAddress = bundle.getString("locationAddress");
            googleAddress = bundle.getString("pinCode");
            area = bundle.getString("area");
            edit = bundle.getString("edit");
            aId = bundle.getString("aid");

            mAddAddressViewModel.fetchUserDetails();
        }

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                if (bundle!=null) {
                    turnOnGps();
                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                    initCameraIdle();
                }
            }
        });

        // dialog.show();

        mAddAddressViewModel.apartmentOrIndividual.set(true);
        mActivityAddressNewBinding.radio1.setChecked(true);
        mActivityAddressNewBinding.edtApartmentName.setFocusable(true);
    }

    public void turnOnGps() {

        new GpsUtils(this, AppConstants.GPS_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(AddressNewActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddressNewActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddressNewActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);
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

        SingleShotLocationProvider.requestSingleUpdate(AddressNewActivity.this,
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
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                                initCameraIdle();
                            }
                    }
                });
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

    private class AsyncTaskAddress extends AsyncTask<Double, Address, Address> {


        @Override
        protected Address doInBackground(Double... doubles) {
            Geocoder geocoder = new Geocoder(AddressNewActivity.this, Locale.ENGLISH);

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

        }
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public boolean validation(){

        if (mActivityAddressNewBinding.radio1.isChecked()){
            if (mActivityAddressNewBinding.edtApartmentName.getText().toString().equals("")){
                mActivityAddressNewBinding.edtApartmentName.setError("");
                return false;
            }else if (mActivityAddressNewBinding.edtTowerBlock.getText().toString().equals("")){
                mActivityAddressNewBinding.edtTowerBlock.setError("");
                return false;
            }else if (mActivityAddressNewBinding.edtFlatHouseNo.getText().toString().equals("")){
                mActivityAddressNewBinding.edtFlatHouseNo.setError("");
                return false;
            }
        }

        if (mActivityAddressNewBinding.radio2.isChecked()){
            if (mActivityAddressNewBinding.edtHousePlotNo.getText().toString().equals("")){
                mActivityAddressNewBinding.edtHousePlotNo.setError("");
                return false;
            }else if (mActivityAddressNewBinding.edtFloor.getText().toString().equals("")){
                mActivityAddressNewBinding.edtFloor.setError("");
                return false;
            }
        }

        if (mActivityAddressNewBinding.edtAddress.getText().toString().equals("")){
            mActivityAddressNewBinding.edtAddress.setError("");
            return false;
        }else if (mActivityAddressNewBinding.edtLandmark.getText().toString().equals("")){
            mActivityAddressNewBinding.edtLandmark.setError("");
            return false;
        }

        return true;
    }
}