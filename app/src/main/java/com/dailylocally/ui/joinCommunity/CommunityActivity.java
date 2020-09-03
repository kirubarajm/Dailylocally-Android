package com.dailylocally.ui.joinCommunity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.joinCommunity.communityLocation.CommunityAddressActivity;
import com.dailylocally.ui.joinCommunity.contactWhatsapp.ContactWhatsAppActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.quicksand.ButtonTextView;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;


public class CommunityActivity extends BaseActivity<ActivityCommunityBinding, CommunityActivityViewModel>
        implements CommunityActivityNavigator,CommunityAdapter.TransactionHistoryInfoListener, OnMapReadyCallback {

    @Inject
    CommunityActivityViewModel mOnBoardingActivityViewModel;
    Analytics analytics;
    String pageName = "community";
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    private ActivityCommunityBinding mActivityOnboardingBinding;
    private TextView[] dots;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private PrefManager prefManager;
    boolean flagFirstTime;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    private GoogleMap mMap;
    Bitmap imageBitmap,imageBitmap1;
    ProgressDialog progress,progress1;
    String imageUrl = "",imageUrl1="",strCommunityLat="",strCommunityLng="",area="";

    @Inject
    CommunityAdapter mCommunityAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, CommunityActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        if (mOnBoardingActivityViewModel.register.get()) {
            onBackPressed();
        }else {
            mOnBoardingActivityViewModel.register.set(true);
            mOnBoardingActivityViewModel.completeRegistration.set(false);
            mOnBoardingActivityViewModel.joinExpandView.set(false);
            mOnBoardingActivityViewModel.joinTheCommunity.set(false);
        }
    }

    @Override
    public void registrationClick() {
        /*mActivityOnboardingBinding.btnRegister.setVisibility(View.GONE);
        mActivityOnboardingBinding.btnCompleteReg.setVisibility(View.VISIBLE);
        mActivityOnboardingBinding.btnJoinComm.setVisibility(View.GONE);
        mActivityOnboardingBinding.btnJoinTheComm.setVisibility(View.GONE);


        mActivityOnboardingBinding.relCompleteRegistration.setVisibility(View.VISIBLE);
        mActivityOnboardingBinding.relJoinCommunityExpandView.setVisibility(View.GONE);
        mActivityOnboardingBinding.relJoinTheCommunity.setVisibility(View.GONE);*/

        Intent intent = CommunityAddressActivity.newIntent(CommunityActivity.this);
        startActivityForResult(intent, AppConstants.COMMUNITY_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void joinClick() {
        mOnBoardingActivityViewModel.register.set(false);
        mOnBoardingActivityViewModel.completeRegistration.set(false);
        mOnBoardingActivityViewModel.joinExpandView.set(false);
        mOnBoardingActivityViewModel.joinTheCommunity.set(true);
        mActivityOnboardingBinding.edtHouse.setText("");
        mActivityOnboardingBinding.edtFloorNo.setText("");
    }

    @Override
    public void uploadJoinImageClick() {
        imageBitmap = null;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_JOIN);
    }

    @Override
    public void uploadRegisterImageClick() {
        imageBitmap1 = null;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_REGISTRATION);
    }

    @Override
    public void joinTheCommunityClick() {
        String houseFlatNo = mActivityOnboardingBinding.edtHouse.getText().toString();
        String floorNo = mActivityOnboardingBinding.edtFloorNo.getText().toString();
        if (validJoin()) {
            mOnBoardingActivityViewModel.joinTheCommunityAPI(imageUrl, houseFlatNo, floorNo);
        }
    }

    public boolean validJoin(){
        if (mActivityOnboardingBinding.edtHouse.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please enter House/Flat no", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mActivityOnboardingBinding.edtFloorNo.getText().toString().trim().equals("")){
            Toast.makeText(this, "Floor no", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean validRegistration(){

        if (mActivityOnboardingBinding.edtCommunityName.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please fill community name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mActivityOnboardingBinding.edtNoOfApartments.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please fill no of apartments", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mActivityOnboardingBinding.edtHouseFlatNo.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please fill House/Flat No", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mActivityOnboardingBinding.edtFloorNoRe.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please fill floor no", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mActivityOnboardingBinding.edtCommunityAddress.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please fill community address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void completeRegistrationClick() {
        if (validRegistration()) {
            String commName = mActivityOnboardingBinding.edtCommunityName.getText().toString();
            String noOfApartments = mActivityOnboardingBinding.edtNoOfApartments.getText().toString();
            String houseFlatNo = mActivityOnboardingBinding.edtHouseFlatNo.getText().toString();
            String floorNo = mActivityOnboardingBinding.edtFloorNoRe.getText().toString();
            String commAddress = mActivityOnboardingBinding.edtCommunityAddress.getText().toString();
            mOnBoardingActivityViewModel.completeRegistrationAPI(commName, strCommunityLat, strCommunityLng, "", imageUrl1, noOfApartments,
                    houseFlatNo, floorNo, commAddress,area);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.communityViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community;
    }

    @Override
    public CommunityActivityViewModel getViewModel() {
        return mOnBoardingActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOnboardingBinding = getViewDataBinding();
        mOnBoardingActivityViewModel.setNavigator(this);
        mCommunityAdapter.setListener(this);

        mOnBoardingActivityViewModel.register.set(true);

        MapFragment mapFragment1 = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment1.getMapAsync(this);

        prefManager = new PrefManager(this);

        layouts = new int[]{
                R.layout.community_slide_1,
                R.layout.community_slide_2,
                R.layout.community_slide_3};

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        mActivityOnboardingBinding.viewPager.setAdapter(myViewPagerAdapter);

        int firstTime = mOnBoardingActivityViewModel.getDataManager().getFirstTimeLaunchCommunity();
        if (firstTime==0){
            mActivityOnboardingBinding.relViewPager.setVisibility(View.VISIBLE);
        }else {
            mActivityOnboardingBinding.relViewPager.setVisibility(View.GONE);
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mActivityOnboardingBinding.recyclerCommunity.setLayoutManager(mLayoutManager);
        mActivityOnboardingBinding.recyclerCommunity.setAdapter(mCommunityAdapter);

        subscribeToLiveData();

        mActivityOnboardingBinding.searchCommunity.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                /*mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
                mFragmentSearchBinding.before.setVisibility(View.VISIBLE);*/
                return false;
            }
        });

        mActivityOnboardingBinding.searchCommunity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    if (s.length()>1){
                        mActivityOnboardingBinding.recyclerCommunity.setVisibility(View.VISIBLE);
                        //mActivityOnboardingBinding.before.setVisibility(View.GONE);
                    }else {
                        //mActivityOnboardingBinding.recyclerCommunity.setVisibility(View.VISIBLE);
                        //mActivityOnboardingBinding.before.setVisibility(View.VISIBLE);
                    }
                    mOnBoardingActivityViewModel.quickSearch(s);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    if (s.length()>1){
                        //mActivityOnboardingBinding.recyclerCommunity.setVisibility(View.GONE);
                        mActivityOnboardingBinding.recyclerCommunity.setVisibility(View.VISIBLE);
                        //mActivityOnboardingBinding.before.setVisibility(View.GONE);
                    }else {
                        //mActivityOnboardingBinding.recyclerviewProduct.setVisibility(View.GONE);
                        //mActivityOnboardingBinding.recyclerCommunity.setVisibility(View.GONE);
                        //mActivityOnboardingBinding.before.setVisibility(View.VISIBLE);
                        //mActivityOnboardingBinding.searchNotFound.setVisibility(View.GONE);
                    }
                    mOnBoardingActivityViewModel.quickSearch(s);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        progress = new ProgressDialog(CommunityActivity.this);
        progress.setMessage("Uploading please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);

        progress1 = new ProgressDialog(CommunityActivity.this);
        progress1.setMessage("Uploading please wait...");
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setIndeterminate(true);
        progress1.setCancelable(false);
    }

    private void subscribeToLiveData() {
        mOnBoardingActivityViewModel.getCommunityListItemsLiveData().observe(this,
                catregoryItemViewModel -> mOnBoardingActivityViewModel.addCommunityListItemsToList(catregoryItemViewModel));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            try {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                mMap = googleMap;

                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.style_silver));

                if (!success) {
                    //Log.e(TAG, "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                //Log.e(TAG, "Can't find style. Error: ", e);
            }

        if (mOnBoardingActivityViewModel.getDataManager().getCurrentLat()!=null && mOnBoardingActivityViewModel.getDataManager().getCurrentLng()!=null) {
            String lat = mOnBoardingActivityViewModel.getDataManager().getCurrentLat();
            String lon = mOnBoardingActivityViewModel.getDataManager().getCurrentLng();
            // Add a marker in Sydney and move the camera
            LatLng currentLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            //mMap.addMarker(new
                    //MarkerOptions().position(currentLocation).title("Current location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mOnBoardingActivityViewModel.getCommunityList();
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

    @Override
    public void onItemClick(CommunityResponse.Result result) {
        try {
        mOnBoardingActivityViewModel.register.set(false);
        mOnBoardingActivityViewModel.completeRegistration.set(false);
        mOnBoardingActivityViewModel.joinExpandView.set(true);
        mOnBoardingActivityViewModel.joinTheCommunity.set(false);

        mActivityOnboardingBinding.txtCommunityName.setText(result.getCommunityname());
        mActivityOnboardingBinding.txtLocation.setText(result.getCommunityAddress());
        mActivityOnboardingBinding.txtNoOfApartments.setText(result.getNoOfApartments());
        mActivityOnboardingBinding.txtStatus.setText(result.getStatus_msg());
        /*if (result.getStatus()!=null && result.getStatus()==1) {
            mActivityOnboardingBinding.txtStatus.setText(result.getStatus_msg());
        }else {
            mActivityOnboardingBinding.txtStatus.setText("Un-Live");
        }*/

        mOnBoardingActivityViewModel.cmId.set(String.valueOf(result.getComid()));

        LatLng currentLocation = new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.get_long()));
        mMap.addMarker(new
                MarkerOptions().position(currentLocation).title(result.getCommunityname()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            if (position==2) {
                ButtonTextView textView = view.findViewById(R.id.btn_get_started);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBoardingActivityViewModel.getDataManager().setFirstTimeLaunchCommunity(1);
                        //launchHomeScreen();
                        mActivityOnboardingBinding.relViewPager.setVisibility(View.GONE);

                    }
                });
            }

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(true);

        mOnBoardingActivityViewModel.checkUpdate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void uploading() {
        progress.show();
    }

    @Override
    public void uploading1() {
        progress1.show();
    }

    @Override
    public void uploaded(String imageUrl) {
        progress.dismiss();
        this.imageUrl = imageUrl;
    }

    @Override
    public void uploaded1(String imageUrl1) {
        progress1.dismiss();
        this.imageUrl1 = imageUrl1;
    }

    @Override
    public void whatAppScreenSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Intent intent = ContactWhatsAppActivity.newIntent(CommunityActivity.this);
        startActivity(intent);
    }

    @Override
    public void communityJoined(String message) {

        Intent intent = MainActivity.newIntent(CommunityActivity.this,AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_COMMUNITY_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void whatAppScreenFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mapLatLngArray(List<CommunityResponse.Result> markersArray) {
        try {
        for(int i = 0 ; i < markersArray.size() ; i++) {
            createMarker(markersArray.get(i).getLat(), markersArray.get(i).get_long(), markersArray.get(i).getCommunityname(), "", 0);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createMarker(String latitude, String longitude, String title, String snippet, int iconResID) {
        try {
        LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);

        Bitmap bitmap = getBitmapFromVectorDrawable(CommunityActivity.this,R.drawable.ic_map_marker);
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(descriptor);
        //     markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        //mMap.clear();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        mMap.addMarker(markerOptions);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable =  AppCompatResources.getDrawable(context, drawableId);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.COMMUNITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //mActivitySignupBinding.acceptTandC.setChecked(true);

                area = data.getStringExtra("area");
                strCommunityLat = data.getStringExtra("lat");
                strCommunityLng = data.getStringExtra("lng");
                Log.e("area",area);

                mOnBoardingActivityViewModel.register.set(false);
                mOnBoardingActivityViewModel.joinExpandView.set(false);
                mOnBoardingActivityViewModel.joinTheCommunity.set(false);
                mOnBoardingActivityViewModel.completeRegistration.set(true);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //mActivitySignupBinding.acceptTandC.setChecked(false);
                Log.e("area","area");
            }
        }
        else if (requestCode == AppConstants.IMAGE_UPLOAD_JOIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    Uri selectedImage = data.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    assert imageBitmap != null;
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    mActivityOnboardingBinding.imgJoin.setImageBitmap(null);
                    mActivityOnboardingBinding.imgJoin.setImageBitmap(imageBitmap);
                    //mActivityOnboardingBinding.flagCameraOrUpload.set(true);
                    mOnBoardingActivityViewModel.uploadImage(imageBitmap, AppConstants.IMAGE_UPLOAD_JOIN);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("area","area");
            }
        }
        else if (requestCode == AppConstants.IMAGE_UPLOAD_REGISTRATION && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                assert extras != null;
                Uri selectedImage = data.getData();
                try {
                    imageBitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                assert imageBitmap1 != null;
                imageBitmap1.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                mActivityOnboardingBinding.imgRegistration.setImageBitmap(null);
                mActivityOnboardingBinding.imgRegistration.setImageBitmap(imageBitmap1);
                //mOnBoardingActivityViewModel.flagCameraOrUpload1.set(true);
                mOnBoardingActivityViewModel.uploadImage(imageBitmap1, AppConstants.IMAGE_UPLOAD_REGISTRATION);
            } else {
                mActivityOnboardingBinding.imgRegistration.setImageBitmap(null);
                //mOnBoardingActivityViewModel.flagCameraOrUpload1.set(false);
            }
        }else {

        }
    }
}
