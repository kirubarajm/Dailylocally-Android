package com.dailylocally.ui.address.addAddress;

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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import com.android.volley.Request;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityAddressNewBinding;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.fandsupport.FeedbackSupportActivity;
import com.dailylocally.ui.joinCommunity.CommunityAdapter;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class AddressNewActivity extends BaseActivity<ActivityAddressNewBinding, AddressNewViewModel> implements
        AddressNewNavigator, HasSupportFragmentInjector, LocationListener, CommunityAdapter.TransactionHistoryInfoListener {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int ADDRESS_SEARCH_CODE = 15545;
    public ActivityAddressNewBinding mActivityAddressNewBinding;
    @Inject
    public AddressNewViewModel mAddAddressViewModel;
    protected LocationManager locationManager;
    @Inject
    CommunityAdapter mCommunityAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    Location mLocation;
    Dialog locationDialog;
    FusedLocationProviderClient fusedLocationClient;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADDRESS;
    String address = null;
    String aid = null;
    Bundle bundle = null;
    ProgressDialog dialog;
    String lat = "", lon = "", googleAddress = "", pinCode = "", area = "", aId = "", edit = "";
    String strCommunityLat = "", strCommunityLng = "";

    String apartment;
    String towerBlock;
    String houseFlatNo;
    String housePlatNo;
    String floor;
    String landmark;
    String apartmentOrIndividual = "";
    Marker marker;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(AddressNewActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    //ProgressDialog dialog;
    // private BottomSheetBehavior mBottomSheetBehavior;

    public static Intent newIntent(Context context, String ToPage, String fromPage) {
        Intent intent = new Intent(context, AddressNewActivity.class);
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

    public static ViewPropertyAnimator slideInToBottom(Context ctx, View view, int index) {
        final int screenHeight = ctx.getResources().getDisplayMetrics().heightPixels;
        int[] coords = new int[2];
        view.getLocationOnScreen(coords);
        view.setTranslationY(screenHeight - coords[1]);
        return view.animate().translationY(0).setDuration(600).setInterpolator(new OvershootInterpolator(1f));


    }

    public void slideInFromBottom(Context ctx, View view, int index) {
        /*final int screenHeight = ctx.getResources().getDisplayMetrics().heightPixels;
        int[] coords = new int[2];
        view.getLocationOnScreen(coords);
        view.setTranslationY(screenHeight - coords[1]);
        return view.animate().translationY(0).setDuration(600).setInterpolator(new OvershootInterpolator(1f));*/

        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);


    }

    public void slideOutAddressView() {

       /* TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, mActivityAddressNewBinding.ad.getHeight());
        translateAnimation.setDuration(600);
        mActivityAddressNewBinding.ad.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //mAddAddressViewModel.openGoogleAddress();
                // slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.ad, mActivityAddressNewBinding.tab.getHeight());


                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.gAddress);
                mAddAddressViewModel.openGoogleAddress();

            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

    }

    public void slidetoBottomView() {

        //      TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);
        //     mAddAddressViewModel.openAddress();


        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, mActivityAddressNewBinding.searchComm.getHeight());
        translateAnimation.setDuration(1000);
        mActivityAddressNewBinding.searchComm.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //  slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.aa, mActivityAddressNewBinding.tab.getHeight());

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //   mAddAddressViewModel.openAddress();
                mAddAddressViewModel.isGoogleAddress.set(false);
                mAddAddressViewModel.isJoinCommunity.set(false);
                mAddAddressViewModel.isSaveAddress.set(false);
                mAddAddressViewModel.isCommunitySearch.set(false);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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


       /* Transition transition = new Slide(Gravity.TOP);
        transition.setDuration(5000);
        transition.addTarget(mActivityAddressNewBinding.container);

        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.container, transition);
        mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);*/




       /* mActivityAddressNewBinding.container.animate()
                .translationY( mActivityAddressNewBinding.container.getHeight())
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                       // mActivityAddressNewBinding.container.setVisibility(View.GONE);

                        mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);
                        mAddAddressViewModel.isApartment.set(true);
                        mAddAddressViewModel.residenceClicked.set(true);
                        mAddAddressViewModel.isApartment.set(true);
                        mActivityAddressNewBinding.edtApartmentName.requestFocus();
                    }
                });*/

        if (!mAddAddressViewModel.residenceClicked.get()) {
            /*Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);
            mActivityAddressNewBinding.container.startAnimation(slideUp);*/


           /* mActivityAddressNewBinding.container.animate()
                    .alpha(1f)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);
                        }
                    });*/
            //mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            //  final int activityHeight = findViewById(android.R.id.content).getHeight();
            //  mActivityAddressNewBinding.fields.animate().translationY(activityHeight -  mActivityAddressNewBinding.fields.getY()).setDuration(5000);


            slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());


            mActivityAddressNewBinding.cardApartment.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mActivityAddressNewBinding.cardIndividual.setCardBackgroundColor(getResources().getColor(R.color.white));

            mActivityAddressNewBinding.txApartment.setTextColor(getResources().getColor(R.color.white));
            mActivityAddressNewBinding.txIndividual.setTextColor(getResources().getColor(R.color.medium_black));

           /* TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.linearAddressType);
            mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);*/
            mAddAddressViewModel.isApartment.set(true);
            mAddAddressViewModel.residenceClicked.set(true);
            mAddAddressViewModel.isApartment.set(true);
            mActivityAddressNewBinding.edtApartmentName.requestFocus();
            mAddAddressViewModel.dummy.set(false);


        } else {

            mActivityAddressNewBinding.cardApartment.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mActivityAddressNewBinding.cardIndividual.setCardBackgroundColor(getResources().getColor(R.color.white));

            mActivityAddressNewBinding.txApartment.setTextColor(getResources().getColor(R.color.white));
            mActivityAddressNewBinding.txIndividual.setTextColor(getResources().getColor(R.color.medium_black));

            TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);
            mActivityAddressNewBinding.container.setVisibility(View.VISIBLE);
            mAddAddressViewModel.isApartment.set(true);
            mAddAddressViewModel.residenceClicked.set(true);
            mAddAddressViewModel.isApartment.set(true);
            mActivityAddressNewBinding.edtApartmentName.requestFocus();
            mAddAddressViewModel.dummy.set(false);
        }
    }

    @Override
    public void individualClick() {


        if (!mAddAddressViewModel.residenceClicked.get()) {
            slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());


            mActivityAddressNewBinding.txApartment.setTextColor(getResources().getColor(R.color.medium_black));
            mActivityAddressNewBinding.txIndividual.setTextColor(getResources().getColor(R.color.white));

            mActivityAddressNewBinding.cardApartment.setCardBackgroundColor(getResources().getColor(R.color.white));
            mActivityAddressNewBinding.cardIndividual.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

            //  TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.linearAddressType);
            mAddAddressViewModel.residenceClicked.set(true);
            mAddAddressViewModel.isApartment.set(false);
            mAddAddressViewModel.dummy.set(true);

        } else {
            mActivityAddressNewBinding.txApartment.setTextColor(getResources().getColor(R.color.medium_black));
            mActivityAddressNewBinding.txIndividual.setTextColor(getResources().getColor(R.color.white));

            mActivityAddressNewBinding.cardApartment.setCardBackgroundColor(getResources().getColor(R.color.white));
            mActivityAddressNewBinding.cardIndividual.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

            TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.linearAddressType);
            mAddAddressViewModel.isApartment.set(false);
            mAddAddressViewModel.dummy.set(true);
            // mActivityAddressNewBinding.edtHousePlotNo.requestFocus();
        }
    }

    @Override
    public void confirmClick() {
        if (validation()) {

            apartment = mActivityAddressNewBinding.edtApartmentName.getText().toString();
            towerBlock = mActivityAddressNewBinding.edtTowerBlock.getText().toString();
            houseFlatNo = mActivityAddressNewBinding.edtFlatHouseNo.getText().toString();

            housePlatNo = mActivityAddressNewBinding.edtHousePlotNo.getText().toString();
            floor = mActivityAddressNewBinding.edtFloor.getText().toString();

            address = mActivityAddressNewBinding.edtAddress.getText().toString();
            landmark = mActivityAddressNewBinding.edtLandmark.getText().toString();

            if (mAddAddressViewModel.isApartment.get()) {
                apartmentOrIndividual = "1";
            } else {
                apartmentOrIndividual = "2";
            }


            mAddAddressViewModel.landmark.set(landmark);
            if (apartmentOrIndividual.equals("1")) {
                mAddAddressViewModel.addressType.set("Apartment or Gated Society");

                String completeAddress = "No." + houseFlatNo + ", " + towerBlock + ", " + apartment + "," + address;
                mAddAddressViewModel.address.set(completeAddress);

            } else {
                mAddAddressViewModel.addressType.set("Individual House");

                String completeAddress = "No." + housePlatNo + ", Floor-" + floor + ", " + address;
                mAddAddressViewModel.address.set(completeAddress);

            }


            TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            mAddAddressViewModel.isSaveAddress.set(true);
            mAddAddressViewModel.isAddress.set(false);

         /*   TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, mActivityAddressNewBinding.aa.getHeight());
            translateAnimation.setDuration(1000);
            mActivityAddressNewBinding.aa.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //  slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.aa, mActivityAddressNewBinding.tab.getHeight());

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //   mAddAddressViewModel.openAddress();
                    mAddAddressViewModel.isGoogleAddress.set(false);
                    mAddAddressViewModel.isJoinCommunity.set(false);
                    mAddAddressViewModel.isCommunitySearch.set(false);
                    mAddAddressViewModel.isAddress.set(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });*/


         /*   Intent intent = SaveAddressActivity.newIntent(AddressNewActivity.this);
            intent.putExtra("apartmentOrIndividual", apartmentOrIndividual);
            intent.putExtra("apartment", apartment);
            intent.putExtra("towerBlock", towerBlock);
            intent.putExtra("houseFlatNo", houseFlatNo);
            intent.putExtra("housePlatNo", housePlatNo);
            intent.putExtra("floor", floor);
            intent.putExtra("address", address);
            intent.putExtra("landmark", landmark);
            intent.putExtra("googleAddress", googleAddress);
            intent.putExtra("pinCode", pinCode);
            intent.putExtra("area", area);
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            intent.putExtra("edit", edit);
            intent.putExtra("aid", mAddAddressViewModel.aId.get());
            startActivity(intent);*/
        }
    }

    @Override
    public void getAddressSuccess(UserAddressResponse.Result result) {
        try {
            if (result != null) {
                if (result.getAddressType() == 1) {
                    mAddAddressViewModel.isApartment.set(true);
                    mActivityAddressNewBinding.edtApartmentName.setText(result.getApartmentName());
                    mActivityAddressNewBinding.edtTowerBlock.setText(result.getBlockName());
                    mActivityAddressNewBinding.edtFlatHouseNo.setText(result.getFlatHouseNo());
                } else {
                    mAddAddressViewModel.isApartment.set(false);
                    mActivityAddressNewBinding.edtHousePlotNo.setText(result.getPlotHouseNo());
                    mActivityAddressNewBinding.edtFloor.setText(result.getFloor());
                }
                mActivityAddressNewBinding.edtAddress.setText(result.getCompleteAddress());
                mActivityAddressNewBinding.edtLandmark.setText(result.getLandmark());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAddressFailure() {

    }


    @Override
    public void goBack() {


        if (mAddAddressViewModel.isGoogleAddress.get()) {

            finish();

        } else if (mAddAddressViewModel.isAddress.get()) {
            //    map.clear();
          /*  slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());

            mAddAddressViewModel.openGoogleAddress();*/
            //   slideOutAddressView();

            //  mAddAddressViewModel.openGoogleAddress();

            /*TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, mActivityAddressNewBinding.searchComm.getHeight());
            translateAnimation.setDuration(600);
            mActivityAddressNewBinding.fields.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.gAddress, mActivityAddressNewBinding.tab.getHeight());
                    mAddAddressViewModel.openGoogleAddress();
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });*/
            if (mAddAddressViewModel.isCommunitySearch.get()) {

              /*  TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.aa);
                mAddAddressViewModel.isCommunitySearchClicked.set(false);
                mAddAddressViewModel.isCommunitySearch.set(false);
                mActivityAddressNewBinding.searchComm.setVisibility(View.GONE);*/
                Fade fade = new Fade(Fade.OUT);
                fade.setDuration(600);
                Fade fadeIN = new Fade(Fade.IN);
                fadeIN.setDuration(1000);
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields, fadeIN);
                mAddAddressViewModel.isCommunitySearch.set(false);
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields, fade);
                mActivityAddressNewBinding.searchComm.setVisibility(View.GONE);


            } else {
                map.clear();
                //Slide slide = new Slide(Gravity.BOTTOM);
                //  slide.setDuration(600);
                mAddAddressViewModel.isAddressEdit.set(false);
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
                mAddAddressViewModel.isGoogleAddress.set(true);
                //   mAddAddressViewModel.  isAddress.set(false);

            }


        } /*else if (mAddAddressViewModel.isCommunitySearch.get()) {


            Slide slide = new Slide();
            slide.setDuration(600);

            // TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);

            mAddAddressViewModel.isAddress.set(true);

            slidetoBottomView();
            *//*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    slidetoBottomView();
                }
            },600);*//*


            //   slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());


        }*/ else if (mAddAddressViewModel.isSaveAddress.get()) {

            TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            mAddAddressViewModel.isAddress.set(true);
            mAddAddressViewModel.isSaveAddress.set(false);


           /* TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, mActivityAddressNewBinding.ad.getHeight());
            translateAnimation.setDuration(600);
            mActivityAddressNewBinding.ad.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //mAddAddressViewModel.openGoogleAddress();
                    // slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.ad, mActivityAddressNewBinding.tab.getHeight());
                    TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.gAddress);
                    mAddAddressViewModel.openAddress();

                }

                @Override
                public void onAnimationEnd(Animation animation) {


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });*/


        } else if (mAddAddressViewModel.isJoinCommunity.get()) {
            //   slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());


            // TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            //   mAddAddressViewModel.isAddress.set(true);
            //   mAddAddressViewModel.isCommunitySearch.set(true);
            //   mAddAddressViewModel.isJoinCommunity.set(false);

            //     mActivityAddressNewBinding.searchComm.setVisibility(View.VISIBLE);
            //  TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            //   mAddAddressViewModel.isJoinCommunity.set(false);


               /* Fade fade = new Fade(Fade.OUT);
                fade.setDuration(600);
                Fade fadeIN = new Fade(Fade.IN);
                fadeIN.setDuration(1000);
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields);
                mActivityAddressNewBinding.searchComm.setVisibility(View.VISIBLE);
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent, fade);
                mAddAddressViewModel.isJoinCommunity.set(false);*/
            Fade fade = new Fade(Fade.OUT);
            fade.setDuration(600);
            Slide slide = new Slide(Gravity.TOP);

            TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            mAddAddressViewModel.isAddress.set(true);
            // TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
            mAddAddressViewModel.isJoinCommunity.set(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    map.clear();
                    createMarkers(mAddAddressViewModel.googleLat.get(), mAddAddressViewModel.googleLon.get(), "", "", 0);
                }
            }, 500);


        } else {
            finish();
        }


    }

    @Override
    public void addApartmentClick() {

        /*Intent inIntent = CommunitySearchActivity.newIntent(AddressNewActivity.this);
        inIntent.putExtra("newuser", true);
        inIntent.putExtra("lat", lat);
        inIntent.putExtra("lng", lon);
        startActivityForResult(inIntent, AppConstants.SELECT_COMMUNITY_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/


        //  Scene  aScene = Scene.getSceneForLayout(mActivityAddressNewBinding.searchComm );
        //anotherScene =Scene.getSceneForLayout(sceneRoot, R.layout.another_scene, this);


// Obtain the scene root element
        /*ViewGroup sceneRoot = (ViewGroup) mActivityAddressNewBinding.searchComm;

// Obtain the view hierarchy to add as a child of
// the scene root when this scene is entered
        ViewGroup  viewHierarchy = (ViewGroup) mActivityAddressNewBinding.searchComm;

// Create a scene
        Scene mScene = new Scene(sceneRoot, viewHierarchy);
        Transition fadeTransition = new Fade(Fade.IN);
        fadeTransition.setDuration(600);
        TransitionManager.go(mScene, fadeTransition);*/


        //   mActivityAddressNewBinding.fullPage.endViewTransition(mActivityAddressNewBinding.searchComm);
        //  mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


       /* ViewGroup rootView = (ViewGroup) mActivityAddressNewBinding.fullPage;
        Fade  mFade = new Fade(Fade.IN);
        mFade.setDuration(600);


// Start recording changes to the view hierarchy
        TransitionManager.beginDelayedTransition(rootView, mFade);*/

        //  TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);


        /*Slide slide = new Slide();
        slide.setDuration(600);
        *//*slide.onAppear((ViewGroup) mActivityAddressNewBinding.fullPage,
                new TransitionValues(mActivityAddressNewBinding.searchComm), View.VISIBLE,
                new TransitionValues(mActivityAddressNewBinding.address), View.GONE);*//*
        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage, slide);

        mAddAddressViewModel.isCommunitySearch.set(true);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fullPage);
                mAddAddressViewModel.openCommunitySearch();
            }
        }, 600);


        //slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.searchComm, mActivityAddressNewBinding.bottomSheet.getHeight());

        mActivityAddressNewBinding.searchCommunity.requestFocus();*/


        //  TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields);

        mActivityAddressNewBinding.searchComm.setVisibility(View.VISIBLE);

        Fade fade = new Fade(Fade.OUT);
        fade.setDuration(1000);
        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields, fade);
        mAddAddressViewModel.isCommunitySearch.set(true);

        //   TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.aa);
        //    mAddAddressViewModel.isCommunitySearchClicked.set(true);
        //   mAddAddressViewModel.openCommunitySearch();
      /*  mAddAddressViewModel.isGoogleAddress.set(false);
        mAddAddressViewModel.isSaveAddress.set(false);
        mAddAddressViewModel.isJoinCommunity.set(false);*/

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields);
                mAddAddressViewModel.isCommunitySearch.set(true);
            }
        }, 600);*/


        //slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.searchComm, mActivityAddressNewBinding.bottomSheet.getHeight());

        mActivityAddressNewBinding.searchCommunity.requestFocus();


    }

    @Override
    public void myLocationn() {
        turnOnGps();
    }

    @Override
    public void searchCommunity() {
        /*Intent inIntent = CommunitySearchActivity.newIntent(AddressNewActivity.this);
        inIntent.putExtra("newuser", true);
        inIntent.putExtra("lat", lat);
        inIntent.putExtra("lng", lon);
        startActivityForResult(inIntent, AppConstants.SELECT_COMMUNITY_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

        addApartmentClick();


    }

    private void subscribeToLiveData() {
        mAddAddressViewModel.getCommunityListItemsLiveData().observe(this,
                catregoryItemViewModel -> mAddAddressViewModel.addCommunityListItemsToList(catregoryItemViewModel));
    }

    @Override
    public void googleAddressConfirm() {

        hideKeyboard();


        if (mAddAddressViewModel.isClickableLocality.get()) {

            if (mActivityAddressNewBinding.txtSubLocality.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter your area", Toast.LENGTH_SHORT).show();
                return;
            }

            mAddAddressViewModel.googleArea.set(mActivityAddressNewBinding.txtSubLocality.getText().toString());
        } else {
            if (mActivityAddressNewBinding.txtSubLocality2.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter your area", Toast.LENGTH_SHORT).show();
                return;
            }

            mAddAddressViewModel.googleArea.set(mActivityAddressNewBinding.txtSubLocality2.getText().toString());
        }

        mAddAddressViewModel.checkAddress(mAddAddressViewModel.googleAddress.get(), mAddAddressViewModel.googleArea.get(), mAddAddressViewModel.googleLat.get(), mAddAddressViewModel.googleLon.get(), mAddAddressViewModel.googlePinCode.get());

    }

    @Override
    public void joinCommunityClick() {
        String houseFlatNo = mActivityAddressNewBinding.edtHouse.getText().toString();
        String floorNo = mActivityAddressNewBinding.edtFloorNo.getText().toString();

        if (validJoin()) {
            mAddAddressViewModel.joinTheCommunityAPI(houseFlatNo, floorNo, false);
        }

    }

    @Override
    public void communityClick() {


        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.allContent);
        mAddAddressViewModel.isAddress.set(false);
        mAddAddressViewModel.isJoinCommunity.set(true);

        //   TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.gga);


    }

    @Override
    public void knowMore() {

        Intent inIntent = CommunityOnBoardingActivity.newIntent(AddressNewActivity.this, AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_COMMUNITY_ONBOARDING);
        inIntent.putExtra("next", true);
        startActivity(inIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void showAlert(String title, String message, String locationAddress, String area, String lat,
                          String lng, String pinCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message).setTitle(title);

        //Setting message manually and performing action on button click
        builder.setCancelable(true)
                .setPositiveButton("Get in touch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // confirmLocationClick(locationAddress,area,lat,lng,pinCode);
                        Intent intent = FeedbackSupportActivity.newIntent(AddressNewActivity.this,
                                AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        // Intent intent = MainActivity.newIntent(CommunityActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                        //  startActivity(intent);
                        //  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();

    }

    @Override
    public void confirmLocationClick(String locationAddress, String area, String lat, String lng, String pinCode) {
        createMarkers(mAddAddressViewModel.googleLat.get(), mAddAddressViewModel.googleLon.get(), "", "", 0);

        //mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());

        mAddAddressViewModel.openAddress();

        mAddAddressViewModel.getCommunityList();
        mActivityAddressNewBinding.searchCommunity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    mAddAddressViewModel.quickSearch(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {

                    mAddAddressViewModel.quickSearch(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public boolean validJoin() {
        if (mActivityAddressNewBinding.edtHouse.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter House/Flat no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mActivityAddressNewBinding.edtFloorNo.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Floor no", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message).setTitle(title);

        //Setting message manually and performing action on button click
        builder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        String houseFlatNo = mActivityAddressNewBinding.edtHouse.getText().toString();
                        String floorNo = mActivityAddressNewBinding.edtFloorNo.getText().toString();
                        if (validJoin()) {
                            mAddAddressViewModel.joinTheCommunityAPI(houseFlatNo, floorNo, true);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        //  Intent intent = MainActivity.newIntent(CommunityActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                        // startActivity(intent);
                        //  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();

    }

    @Override
    public void addresSaved(String message, Boolean status, String email, String gender, int method) {

        if (method == Request.Method.POST) {
            new Analytics().eventAccountCreated(AddressNewActivity.this, email, gender, null, "Non DLE");
        }


        if (status) {
            Intent intent = MainActivity.newIntent(AddressNewActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_ADDRESS_ACTV, AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_HOME);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void communityJoined(String message, String aid, String apartmentName) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        String gender = "MALE";
        if (mAddAddressViewModel.getDataManager().getGender() == 2) {
            gender = "FEMALE";
        }


        if (aid != null) {
            if (!aid.equals("0")) {
                new Analytics().eventAccountCreated(AddressNewActivity.this, mAddAddressViewModel.getDataManager().getCurrentUserEmail(), gender, apartmentName, "DLE");
            }
        }else {
            new Analytics().eventAccountCreated(AddressNewActivity.this, mAddAddressViewModel.getDataManager().getCurrentUserEmail(), gender, apartmentName, "DLE");

        }

        Intent intent = MainActivity.newIntent(AddressNewActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_ADDRESS_ACTV, AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void showToast(String message, Boolean status) {

        if (status) {
            Intent intent = MainActivity.newIntent(AddressNewActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_ADDRESS_ACTV, AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_HOME);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAddressFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addManually() {
        mActivityAddressNewBinding.edtApartmentName.setText(mActivityAddressNewBinding.searchCommunity.getQuery().toString());
        //  slideInFromBottom(AddressNewActivity.this, mActivityAddressNewBinding.fields, mActivityAddressNewBinding.tab.getHeight());
        //  mAddAddressViewModel.openAddress();
        Fade fade = new Fade(Fade.OUT);
        fade.setDuration(600);
        Fade fadeIN = new Fade(Fade.IN);
        fadeIN.setDuration(1000);
        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields, fadeIN);
        mAddAddressViewModel.isCommunitySearch.set(false);
        TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.fields, fade);
        mActivityAddressNewBinding.searchComm.setVisibility(View.GONE);

        mAddAddressViewModel.clickableApartment.set(false);
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
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
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
                if (mAddAddressViewModel.isGoogleAddress.get())
                    if (center.latitude != 0.0) {

                        getAddressFromLocation(center.latitude, center.longitude);
                        mAddAddressViewModel.googleLat.set(String.valueOf(center.latitude));
                        mAddAddressViewModel.googleLon.set(String.valueOf(center.longitude));

                    }
                //getAddressFromLocation(12.99447060,80.25593567);
            }
        });

    }

    private void getAddressFromLocation(double latitude, double longitude) {

        if (mAddAddressViewModel.isGoogleAddress.get()) {
            new AsyncTaskAddress().execute(latitude, longitude);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_SEARCH_CODE) {
            if (resultCode == RESULT_OK) {
                //   Place place = PlaceAutocomplete.getPlace(this, data);
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place.getLatLng() != null) {
                    getAddressFromLocation(place.getLatLng().latitude, place.getLatLng().longitude);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16);
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
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
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
    public void saveAddressClick() {


        mAddAddressViewModel.saveAddress(apartmentOrIndividual, mAddAddressViewModel.googleAddress.get(), address, houseFlatNo, housePlatNo, area, pinCode,
                mAddAddressViewModel.googleLat.get(), mAddAddressViewModel.googleLon.get(), landmark, floor, towerBlock, apartment, aId);


    }


    @Override
    public void editAddressClick() {
        goBack();
    }

    @Override
    public void dataloaded() {

    }

    @Override
    public void goHome() {
        Intent intent = MainActivity.newIntent(AddressNewActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_SPLASH_ACTV, AppConstants.SCREEN_NAME_ADDRESS, AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressNewBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        mCommunityAdapter.setListener(this);
        //analytics = new Analytics(this, pageName);
        mAddAddressViewModel.isClickableLocality.set(true);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mAddAddressViewModel.clickableApartment.set(true);

       /* mBottomSheetBehavior = BottomSheetBehavior.from(mActivityAddressNewBinding.bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setHideable(false);*/


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //  mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAddressNewBinding.recyclerCommunity.setLayoutManager(mLayoutManager);
        mActivityAddressNewBinding.recyclerCommunity.setAdapter(mCommunityAdapter);

        mAddAddressViewModel.openGoogleAddress();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");

       /* View googleLogo = mActivityAddressNewBinding.frame.findViewWithTag("GoogleWatermark");
        RelativeLayout.LayoutParams glLayoutParams = (RelativeLayout.LayoutParams) googleLogo.getLayoutParams();
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        glLayoutParams.setMargins(0, 16, 10, 0);
        googleLogo.setLayoutParams(glLayoutParams);*/


        View googleLogo = mActivityAddressNewBinding.frame.findViewWithTag("GoogleWatermark");
        RelativeLayout.LayoutParams glLayoutParams = (RelativeLayout.LayoutParams) googleLogo.getLayoutParams();
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        googleLogo.setLayoutParams(glLayoutParams);


        subscribeToLiveData();


        bundle = getIntent().getExtras();
        if (bundle != null) {

            mAddAddressViewModel.newUser.set(bundle.getBoolean("newuser", false));


            /*lat = bundle.getString("lat");
            lon = bundle.getString("lon");
            googleAddress = bundle.getString("locationAddress");
            pinCode = bundle.getString("pinCode");
            area = bundle.getString("area");
            edit = bundle.getString("edit");
            aId = bundle.getString("aid");*/

            //  mAddAddressViewModel.fetchUserDetails();
        }









       /* TransitionManager.beginDelayedTransition(mActivityAddressNewBinding.container);
        mAddAddressViewModel.residenceClicked.set(false);
        mAddAddressViewModel.isApartment.set(false);*/


        /*dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");*/

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {


                map = googleMap;
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                AddressNewActivity.this, R.raw.style_silver));


                if (mAddAddressViewModel.isGoogleAddress.get()) {
                    turnOnGps();
                    initCameraIdle();
                }
                /*try {
                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    //  markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    Bitmap bitmap = getBitmapFromVectorDrawable(AddressNewActivity.this, R.drawable.ic_map_marker);
                    BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                    markerOptions.icon(descriptor);



                    map.clear();
                    // map.getUiSettings().setZoomControlsEnabled(true);
                    //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    if (bundle != null) {
                        map.addMarker(markerOptions);
                        Projection projection = googleMap.getProjection();
                        LatLng markerPosition = markerOptions.getPosition();
                        Point markerPoint = projection.toScreenLocation(markerPosition);
                        Point targetPoint = new Point(markerPoint.x, markerPoint.y - mActivityAddressNewBinding.frame.getHeight());
                        LatLng targetPosition = projection.fromScreenLocation(targetPoint);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(targetPosition), 1000, null);


                        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

                        // initCameraIdle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });
        // dialog.show();

        mAddAddressViewModel.isApartment.set(true);
        mActivityAddressNewBinding.edtApartmentName.setFocusable(true);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_ADDRESS);
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
                //turnOnGps();

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
        goBack();
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

    public boolean validation() {

        if (mAddAddressViewModel.isApartment.get()) {
            if (mActivityAddressNewBinding.edtApartmentName.getText().toString().trim().isEmpty()) {
                //  mActivityAddressNewBinding.edtApartmentName.setError("");
                Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (mActivityAddressNewBinding.edtTowerBlock.getText().toString().trim().isEmpty()) {
                //   mActivityAddressNewBinding.edtTowerBlock.setError("");
                Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

                return false;
            } else if (mActivityAddressNewBinding.edtFlatHouseNo.getText().toString().trim().isEmpty()) {
                //  mActivityAddressNewBinding.edtFlatHouseNo.setError("");
                Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        if (!mAddAddressViewModel.isApartment.get()) {
            if (mActivityAddressNewBinding.edtHousePlotNo.getText().toString().trim().isEmpty()) {
                // mActivityAddressNewBinding.edtHousePlotNo.setError("");
                Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

                return false;
            } else if (mActivityAddressNewBinding.edtFloor.getText().toString().trim().isEmpty()) {
                //  mActivityAddressNewBinding.edtFloor.setError("");
                Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        if (mActivityAddressNewBinding.edtAddress.getText().toString().trim().isEmpty()) {
            // mActivityAddressNewBinding.edtAddress.setError("");
            Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

            return false;
        } else if (mActivityAddressNewBinding.edtLandmark.getText().toString().trim().isEmpty()) {
            //  mActivityAddressNewBinding.edtLandmark.setError("");
            Toast.makeText(this, "Please enter the mandatory fields", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(CommunityResponse.Result community) {

        mAddAddressViewModel.cmId.set(String.valueOf(community.getComid()));
        mAddAddressViewModel.comLat.set(community.getLat());
        mAddAddressViewModel.comLon.set(community.get_long());

        communityClick();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                createMarkers(community.getLat(), community.get_long(), community.getCommunityname(), community.getNoOfApartments() + " Residency", 0);

            }
        }, 600);


    }

    public void createMarkers(String latitude, String longitude, String title, String snippet, int iconResID) {
        try {
            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.snippet(snippet);


            Bitmap bitmap = getBitmapFromVectorDrawable(AddressNewActivity.this, R.drawable.ic_group_310);
            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            markerOptions.icon(descriptor);
            //    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
            map.clear();
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            marker = map.addMarker(markerOptions);
            //   builder.include(markerOptions.getPosition());
            if (!title.isEmpty())
                marker.showInfoWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

            hideKeyboard();

            try {

                if (fetchedAddress != null) {


               /* String address = fetchedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = fetchedAddress.getLocality();
                String state = fetchedAddress.getAdminArea();
                String country = fetchedAddress.getCountryName();
                String postalCode = fetchedAddress.getPostalCode();
                String knownName = fetchedAddress.getFeatureName();
                Toast.makeText(AddAddressActivity.this, address+"\n"+city+"\n"+state+"\n"+country+"\n"+postalCode+"\n"+knownName, Toast.LENGTH_LONG).show();*/


                    address = fetchedAddress.getAddressLine(0);
                    //    address = fetchedAddress.getSubLocality()+","+fetchedAddress.getLocality()+","+fetchedAddress.getAdminArea()+","+fetchedAddress.getCountryName()+","+fetchedAddress.getPostalCode();


                    mAddAddressViewModel.locationAddress.set(address);

                    ColorStateList colorStateList;
                    if (fetchedAddress.getSubLocality() != null) {

                        mAddAddressViewModel.isClickableLocality.set(true);

                        mAddAddressViewModel.area.set(fetchedAddress.getSubLocality());
                        //mActivityCommunityBinding.txtSubLocality.setError("Unable to identify location");
                        //mActivityCommunityBinding.txtEdit.setEnabled(false);
                        // mActivityAddressNewBinding.txtSubLocality.setEnabled(false);
                        mActivityAddressNewBinding.txtSubLocality.setHint("");
                        colorStateList = ColorStateList.valueOf(Color.parseColor("#000000"));
                        ViewCompat.setBackgroundTintList(mActivityAddressNewBinding.txtSubLocality, colorStateList);
                        mActivityAddressNewBinding.txtMessage.setVisibility(View.GONE);
                        mActivityAddressNewBinding.imgMarker.setBackgroundResource(R.drawable.ic_group_2);


                    } else {
                        mAddAddressViewModel.isClickableLocality.set(false);
                        mAddAddressViewModel.area.set("");
                        //mActivityCommunityBinding.txtEdit.setEnabled(true);

                        //mActivityCommunityBinding.txtSubLocality.setError("Unable to identify location");
                        mActivityAddressNewBinding.txtSubLocality2.setHint(null);
                        mActivityAddressNewBinding.txtSubLocality2.setHint("Unable to identify location");
                        mActivityAddressNewBinding.txtSubLocality2.setHintTextColor(Color.parseColor("#FF0001"));
                        mActivityAddressNewBinding.txtMessage.setVisibility(View.VISIBLE);
                        printToast("Unable to find your area please mark your location on map..");

                        colorStateList = ColorStateList.valueOf(Color.parseColor("#FF0001"));
                        ViewCompat.setBackgroundTintList(mActivityAddressNewBinding.txtSubLocality2, colorStateList);

                        mActivityAddressNewBinding.imgMarker.setBackgroundResource(R.drawable.ic_group_3);

                        mActivityAddressNewBinding.txtSubLocality2.setEnabled(true);
                        mActivityAddressNewBinding.txtSubLocality2.requestFocus();
                    }


                    //    mAddAddressViewModel.house.set(fetchedAddress.getFeatureName());


                    mAddAddressViewModel.saveAddress(address, fetchedAddress.getSubLocality(), String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode());

                    strCommunityLat = String.valueOf(fetchedAddress.getLatitude());
                    strCommunityLng = String.valueOf(fetchedAddress.getLongitude());


                    mAddAddressViewModel.googleLat.set(String.valueOf(fetchedAddress.getLatitude()));
                    mAddAddressViewModel.googleLon.set(String.valueOf(fetchedAddress.getLongitude()));


                    pinCode = fetchedAddress.getPostalCode();

                    StringBuilder strAddress = new StringBuilder();
                    for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");

                    }

                } else {
                /*if (address == null)
                    printToast("Unable to find your address please mark your location on map..");*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}