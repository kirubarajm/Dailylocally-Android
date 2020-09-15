package com.dailylocally.ui.joinCommunity;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.joinCommunity.communityLocation.CommunityAddressActivity;
import com.dailylocally.ui.joinCommunity.contactWhatsapp.ContactWhatsAppActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.quicksand.ButtonTextView;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;


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
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private PrefManager prefManager;
    boolean flagFirstTime;
    private GuideView mGuideView;
    //private GuideView.Builder builder;
    private GoogleMap mMap;
    Bitmap imageBitmap,imageBitmap1;
    ProgressDialog progress,progress1;
    String imageUrl = "",imageUrl1="",strCommunityLat="",strCommunityLng="",area="";

    @Inject
    CommunityAdapter mCommunityAdapter;
    int count =0;
    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;

    public static Intent newIntent(Context context) {
        return new Intent(context, CommunityActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        if (mOnBoardingActivityViewModel.register.get()) {
            super.onBackPressed();
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
        /*imageBitmap = null;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_JOIN);*/
/*
        try {
            if (imageBitmap == null){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_JOIN);
            }else {
                //Intent intent = ViewPhotoActivity.newIntent(this);
                //intent.putExtra("imgurl", imageUrl);
                //startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
*/


        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,2);

    }

    @Override
    public void close() {
        imageBitmap = null;
        imageUrl = "";
        mActivityOnboardingBinding.imgJoin.setImageBitmap(null);
        mActivityOnboardingBinding.imgJoin.setBackgroundResource(R.drawable.ic_group_482);
        mOnBoardingActivityViewModel.flagRemovePicJoin.set(false);
    }

    @Override
    public void closeReg() {
        imageBitmap1 = null;
        imageUrl1 = "";
        mActivityOnboardingBinding.imgRegistration.setImageBitmap(null);
        mActivityOnboardingBinding.imgRegistration.setBackgroundResource(R.drawable.ic_group_482);
        mOnBoardingActivityViewModel.flagRemovePicReg.set(false);
    }

    @Override
    public void uploadRegisterImageClick() {
        imageBitmap1=null;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_REGISTRATION);
/*
        try {
            if (imageBitmap1==null) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, AppConstants.IMAGE_UPLOAD_REGISTRATION);
            }else {
                //Intent intent = ViewPhotoActivity.newIntent(this);
                //intent.putExtra("imgurl", imageUrl1);
                //startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
*/
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

        requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

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
            mActivityOnboardingBinding.relViewPager.setVisibility(View.GONE);
            mActivityOnboardingBinding.relOnboardingAll.setVisibility(View.VISIBLE);
        }else {
            mActivityOnboardingBinding.relViewPager.setVisibility(View.GONE);
            mActivityOnboardingBinding.layoutDots.setVisibility(View.GONE);
            mActivityOnboardingBinding.relOnboardingAll.setVisibility(View.GONE);
        }




      /*  PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        //pickerLayoutManager.setScaleDownBy(0.99f);
        //pickerLayoutManager.setScaleDownDistance(0.8f);*/


        //SnapHelper snapHelper = new LinearSnapHelper();
        //snapHelper.attachToRecyclerView(mActivityOnboardingBinding.recyclerCommunity);

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mActivityOnboardingBinding.recyclerCommunity.setLayoutManager(mLayoutManager);
        mActivityOnboardingBinding.recyclerCommunity.setAdapter(mCommunityAdapter);


        mActivityOnboardingBinding.recyclerCommunity .setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.25f)
                .setMinScale(0.90f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());



        mActivityOnboardingBinding.recyclerCommunity.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

                CommunityResponse.Result result=  mOnBoardingActivityViewModel.communityItemViewModels.get(adapterPosition);

                if (result.getLat()!=null &&result.get_long()!=null&& mMap!=null) {

                    LatLng currentLocation = new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.get_long()));
                    //mMap.addMarker(new
                    //MarkerOptions().position(currentLocation).title(result.getCommunityname()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 3000, null);
                }
            }
        });

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

        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        addBottomDots(0);


        mActivityOnboardingBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position==2){
                    mActivityOnboardingBinding.layoutDots.setVisibility(View.GONE);
                }else {
                    mActivityOnboardingBinding.layoutDots.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


            Spannable wordtoSpan2 = new SpannableString("Join Your Apartment Community \nAnd Order With No Minimum Value");
            wordtoSpan2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 45, 62, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan2.setSpan(new ForegroundColorSpan(Color.WHITE), 5, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mActivityOnboardingBinding.txtContent.setText(wordtoSpan2);

            Spannable wordtoSpan = new SpannableString("0 minimum basket value");
            wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 13, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mActivityOnboardingBinding.txtMinimumBsktValue.setText(wordtoSpan);

            Spannable wordtoSpan1 = new SpannableString("Free delivery");
            wordtoSpan1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mActivityOnboardingBinding.txtFreeDelivery.setText(wordtoSpan1);

            Spannable wordtoSpan3 = new SpannableString("Register Your Community To Enjoy \nA Personalised Community Experience");
            wordtoSpan3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan3.setSpan(new ForegroundColorSpan(Color.WHITE), 9, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mActivityOnboardingBinding.txtRegisterContent.setText(wordtoSpan3);

        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
           mActivityOnboardingBinding.relOnboardingAll.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   count++;
                   if (count==0){
                       mActivityOnboardingBinding.relOnboarding1.setVisibility(View.VISIBLE);
                       mActivityOnboardingBinding.relOnboarding2.setVisibility(View.GONE);
                       mActivityOnboardingBinding.relOnboarding3.setVisibility(View.GONE);
                   }else if (count==1){
                       mActivityOnboardingBinding.relOnboarding1.setVisibility(View.GONE);
                       mActivityOnboardingBinding.relOnboarding2.setVisibility(View.VISIBLE);
                       mActivityOnboardingBinding.relOnboarding3.setVisibility(View.GONE);


                       mActivityOnboardingBinding.relOnboarding1.startAnimation(aniFadeOut);
                       mActivityOnboardingBinding.relOnboarding2.startAnimation(aniFade);
                   }else if (count==2){
                       mActivityOnboardingBinding.relOnboarding1.setVisibility(View.GONE);
                       mActivityOnboardingBinding.relOnboarding2.setVisibility(View.GONE);
                       mActivityOnboardingBinding.relOnboarding3.setVisibility(View.VISIBLE);

                       mActivityOnboardingBinding.relOnboarding2.startAnimation(aniFadeOut);
                       mActivityOnboardingBinding.relOnboarding3.startAnimation(aniFade);
                   }

               }
           });

           mActivityOnboardingBinding.getStarted.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mOnBoardingActivityViewModel.getDataManager().setFirstTimeLaunchCommunity(1);
                   mActivityOnboardingBinding.relOnboarding1.setVisibility(View.GONE);
                   mActivityOnboardingBinding.relOnboarding2.setVisibility(View.GONE);
                   mActivityOnboardingBinding.relOnboarding3.setVisibility(View.GONE);
                   mActivityOnboardingBinding.relOnboardingAll.setVisibility(View.GONE);
               }
           });

    }

    private void subscribeToLiveData() {
        mOnBoardingActivityViewModel.getCommunityListItemsLiveData().observe(this,
                catregoryItemViewModel -> mOnBoardingActivityViewModel.addCommunityListItemsToList(catregoryItemViewModel));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            try {

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
        if (result.getStatus()==0){
            mActivityOnboardingBinding.txtStatus.setTextColor(getResources().getColor(R.color.black));
        }else {
            mActivityOnboardingBinding.txtStatus.setTextColor(getResources().getColor(R.color.dl_green));
        }
        /*if (result.getStatus()!=null && result.getStatus()==1) {
            mActivityOnboardingBinding.txtStatus.setText(result.getStatus_msg());
        }else {
            mActivityOnboardingBinding.txtStatus.setText("Un-Live");
        }*/

        mOnBoardingActivityViewModel.cmId.set(String.valueOf(result.getComid()));

        LatLng currentLocation = new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.get_long()));
        //mMap.addMarker(new
                //MarkerOptions().position(currentLocation).title(result.getCommunityname()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
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
            TextView txtContent = view.findViewById(R.id.txt_content);
            TextView minimumBsktValue = view.findViewById(R.id.txt_minimum_bskt_value);
            TextView freeDelivery = view.findViewById(R.id.txt_free_delivery);
            TextView register = view.findViewById(R.id.txt_register_content);
            if (position==2) {
                ButtonTextView textView = view.findViewById(R.id.get_started);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBoardingActivityViewModel.getDataManager().setFirstTimeLaunchCommunity(1);
                        //launchHomeScreen();
                        mActivityOnboardingBinding.relViewPager.setVisibility(View.GONE);
                        mActivityOnboardingBinding.layoutDots.setVisibility(View.GONE);
                    }
                });
            }

            /*if (position==1) {
                Spannable wordtoSpan = new SpannableString("Join Your Apartment Community \nAnd Order With No Minimum Value");
                wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 45, 62, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 5, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtContent.setText(wordtoSpan);
            }
            if (position==0) {
                Spannable wordtoSpan = new SpannableString("0 minimum basket value");
                wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 13, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                minimumBsktValue.setText(wordtoSpan);

                Spannable wordtoSpan1 = new SpannableString("Free delivery");
                wordtoSpan1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                freeDelivery.setText(wordtoSpan1);
            }
            if (position==2) {
                Spannable wordtoSpan = new SpannableString("Register Your Community To Enjoy \nA Personalised Community Experience");
                wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 9, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                register.setText(wordtoSpan);
            }*/

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
        goBack();
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
            builder = new LatLngBounds.Builder();
        for(int i = 0 ; i < markersArray.size() ; i++) {
            createMarker(markersArray.get(i).getLat(), markersArray.get(i).get_long(), markersArray.get(i).getCommunityname(), "", 0);
        }
            bounds = builder.build();
            /*CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
            mMap.animateCamera(cu);*/


            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            mMap.animateCamera(cu);



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
        //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        mMap.addMarker(markerOptions);
            builder.include(markerOptions.getPosition());

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
                    //mActivityOnboardingBinding.imgJoin.setImageBitmap(imageBitmap);

                    Bitmap bitmap = imageBitmap;
                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                    BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    paint.setAntiAlias(true);
                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
                    mActivityOnboardingBinding.imgJoin.setBackgroundResource(0);
                    mActivityOnboardingBinding.imgJoin.setImageBitmap(circleBitmap);


                    //mActivityOnboardingBinding.flagCameraOrUpload.set(true);
                    mOnBoardingActivityViewModel.uploadImage(imageBitmap, AppConstants.IMAGE_UPLOAD_JOIN);
                    mOnBoardingActivityViewModel.flagRemovePicJoin.set(true);
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
                //mActivityOnboardingBinding.imgRegistration.setImageBitmap(imageBitmap1);


                Bitmap bitmap = imageBitmap1;
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

                mActivityOnboardingBinding.imgRegistration.setBackgroundResource(0);
                mActivityOnboardingBinding.imgRegistration.setImageBitmap(circleBitmap);
                //mOnBoardingActivityViewModel.flagCameraOrUpload1.set(true);
                mOnBoardingActivityViewModel.uploadImage(imageBitmap1, AppConstants.IMAGE_UPLOAD_REGISTRATION);
                mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
            } else {
                mActivityOnboardingBinding.imgRegistration.setImageBitmap(null);
                //mOnBoardingActivityViewModel.flagCameraOrUpload1.set(false);
            }
        }else {

        }

        if (requestCode == 2){
            Uri ii = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setAspectRatio(1,1)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("\u2022"));
            dots[i].setTextSize(20);
            dots[i].setPadding(0,0,20,0);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
}
