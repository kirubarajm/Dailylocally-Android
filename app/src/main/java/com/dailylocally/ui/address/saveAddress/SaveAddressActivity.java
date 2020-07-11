package com.dailylocally.ui.address.saveAddress;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySaveAddressBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SaveAddressActivity extends BaseActivity<ActivitySaveAddressBinding, SaveAddressViewModel> implements
        SaveAddressNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    public ActivitySaveAddressBinding mActivitySaveAddressBinding;
    @Inject
    public SaveAddressViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = "";
    String apartmentOrIndividual="",apartment="",towerBlock="",houseFlatNo="",housePlatNo="",floor="",address=""
            ,landmark="",googleAddress="",pinCode="",area="",lon="",lat="",aId="",edit="";

    SupportMapFragment mapFragment;
    GoogleMap map;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(SaveAddressActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, SaveAddressActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.saveAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_save_address;
    }

    @Override
    public SaveAddressViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void saveClick() {
        mAddAddressViewModel.saveAddress(apartmentOrIndividual,googleAddress,address,houseFlatNo,housePlatNo,area,pinCode,
                lat,lon,landmark,floor,towerBlock,apartment,aId);
    }

    @Override
    public void editClick() {
        onBackPressed();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void saveAddressFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String msg,boolean trueOrFalse) {
        if (trueOrFalse){
            Intent intent = MainActivity.newIntent(SaveAddressActivity.this,AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_ADDRESS_ACTV);
            startActivity(intent);
            finish();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySaveAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            apartmentOrIndividual = bundle.getString("apartmentOrIndividual");
            apartment = bundle.getString("apartment");
            towerBlock = bundle.getString("towerBlock");
            houseFlatNo = bundle.getString("houseFlatNo");
            housePlatNo = bundle.getString("housePlatNo");
            floor = bundle.getString("floor");
            address = bundle.getString("address");
            landmark = bundle.getString("landmark");
            googleAddress = bundle.getString("googleAddress");
            pinCode = bundle.getString("pinCode");
            area = bundle.getString("area");
            lat = bundle.getString("lat");
            lon = bundle.getString("lon");
            edit = bundle.getString("edit");
            aId = bundle.getString("aid");

            if (edit!=null) {
                mAddAddressViewModel.flagAddressEdit.set(true);
            }else {
                mAddAddressViewModel.flagAddressEdit.set(false);
            }


            mAddAddressViewModel.landmark.set(landmark);
            if (apartmentOrIndividual.equals("1")){
                mAddAddressViewModel.addressType.set("Apartment or Gated Society");

                String completeAddress="House/Flat No."+houseFlatNo+", "+towerBlock+", "+apartment+","+address;
                mAddAddressViewModel.address.set(completeAddress);

            }else {
                mAddAddressViewModel.addressType.set("Individual House");

                String completeAddress="House/Plot No."+housePlatNo+", Floor-"+floor+", "+address;
                mAddAddressViewModel.address.set(completeAddress);

            }
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentSaveAddress);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
              //  markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                map = googleMap;
                map.clear();
                map.getUiSettings().setZoomControlsEnabled(true);
                //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                map.addMarker(markerOptions);
            }
        });
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
}