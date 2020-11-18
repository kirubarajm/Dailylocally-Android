package com.dailylocally.ui.joinCommunity;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.fandsupport.FeedbackSupportActivity;
import com.dailylocally.ui.favourites.FavActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.dailylocally.utilities.AppConstants.STORAGE_PERMISSION_REQUEST_CODE;


public class CommunityActivity extends BaseActivity<ActivityCommunityBinding, CommunityActivityViewModel>
        implements CommunityActivityNavigator, OnMapReadyCallback {

    @Inject
    CommunityActivityViewModel mOnBoardingActivityViewModel;
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
    boolean flagFirstTime;
    Bitmap imageBitmap, imageBitmap1;
    ProgressDialog progress, progress1;
    String imageUrl = "", imageUrl1 = "", strCommunityLat = "", strCommunityLng = "", area = "";
    Boolean showSingle = true;
    int count = 0;
    List<CommunityResponse.Result> markersArrays;
    Boolean markerAdded = false;
    int scrollCount = 0;
    private ActivityCommunityBinding mActivityOnboardingBinding;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private PrefManager prefManager;
    //private GuideView.Builder builder;
    private GoogleMap mMap;
    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, CommunityActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
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
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {

        super.onBackPressed();

    }


    @Override
    public void joinClick() {

        mOnBoardingActivityViewModel.joinTheCommunity.set(true);
        mActivityOnboardingBinding.edtHouse.setText("");
        mActivityOnboardingBinding.edtFloorNo.setText("");
    }

    @Override
    public void uploadJoinImageClick() {

        if (checkIfAlreadyhavePermission()) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setAspectRatio(1, 1)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(this);
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void close() {
        imageBitmap = null;
        imageUrl = "";
        mActivityOnboardingBinding.imgJoin.setImageBitmap(null);
        mActivityOnboardingBinding.imgJoin.setBackgroundResource(R.drawable.ic_group_482);
        mOnBoardingActivityViewModel.imageUrl.set("");
        mOnBoardingActivityViewModel.flagRemovePicReg.set(false);
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setAspectRatio(1, 1)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .start(this);

                }
        }

    }

    @Override
    public void joinTheCommunityClick() {
        String houseFlatNo = mActivityOnboardingBinding.edtHouse.getText().toString();
        String floorNo = mActivityOnboardingBinding.edtFloorNo.getText().toString();
        if (validJoin()) {
            mOnBoardingActivityViewModel.joinTheCommunityAPI(imageUrl, houseFlatNo, floorNo, false);
        }
    }

    public boolean validJoin() {
        if (mActivityOnboardingBinding.edtHouse.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter House/Flat no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mActivityOnboardingBinding.edtFloorNo.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Floor no", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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

        MapFragment mapFragment1 = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment1.getMapAsync(this);


        changeStatusBarColor();


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

        try {

            mOnBoardingActivityViewModel.joinTheCommunity.set(true);

            mOnBoardingActivityViewModel.cmId.set(getIntent().getExtras().getString("comid"));


            mOnBoardingActivityViewModel.lat.set(getIntent().getExtras().getString("cuslat", ""));
            mOnBoardingActivityViewModel.lng.set(getIntent().getExtras().getString("cuslng", ""));


            if (mMap != null) {
                createMarkers(getIntent().getExtras().getString("lat"), getIntent().getExtras().getString("lng"), getIntent().getExtras().getString("name", ""), getIntent().getExtras().getString("residency", ""), 0);

                markerAdded = true;

            }

            Intent intent = getIntent();
            new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                    AppConstants.SCREEN_NAME_JOIN_COMMUNITY);

        } catch (Exception e) {
            e.printStackTrace();
        }


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


                if (!markerAdded) {
                    //    LatLng currentLocation = new LatLng(Double.parseDouble(getIntent().getExtras().getString("lat")), Double.parseDouble(getIntent().getExtras().getString("lng")));
                    createMarkers(getIntent().getExtras().getString("lat"), getIntent().getExtras().getString("lng"), getIntent().getExtras().getString("name", ""), getIntent().getExtras().getString("residency", ""), 0);
                    //mMap.addMarker(new
                    //MarkerOptions().position(currentLocation).title(result.getCommunityname()));
                    // mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    //   mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    //   mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    markerAdded = true;
                }


            } catch (Resources.NotFoundException e) {
                //Log.e(TAG, "Can't find style. Error: ", e);
            }

        } catch (Exception e) {
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
        //  mOnBoardingActivityViewModel.getCommunityList();
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
        mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
    }

    @Override
    public void uploaded1(String imageUrl1) {
        progress1.dismiss();
        this.imageUrl1 = imageUrl1;
        mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
    }


    @Override
    public void communityJoined(String message) {

        Intent intent = MainActivity.newIntent(CommunityActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV,AppConstants.SCREEN_NAME_COMMUNITY,AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }


    public void createMarkers(String latitude, String longitude, String title, String snippet, int iconResID) {
        try {
            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.snippet(snippet);


            Bitmap bitmap = getBitmapFromVectorDrawable(CommunityActivity.this, R.drawable.ic_group_310);
            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            markerOptions.icon(descriptor);
            //    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
            mMap.clear();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            Marker marker = mMap.addMarker(markerOptions);
            //   builder.include(markerOptions.getPosition());
            marker.showInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Log.e("area", area);


                mOnBoardingActivityViewModel.joinTheCommunity.set(false);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //mActivitySignupBinding.acceptTandC.setChecked(false);
                Log.e("area", "area");
            }
        } else if (requestCode == AppConstants.IMAGE_UPLOAD_JOIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    Uri selectedImage = data.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
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

                    BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    paint.setAntiAlias(true);
                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                    mActivityOnboardingBinding.imgJoin.setBackgroundResource(0);
                    mActivityOnboardingBinding.imgJoin.setImageBitmap(circleBitmap);


                    //mActivityOnboardingBinding.flagCameraOrUpload.set(true);
                    mOnBoardingActivityViewModel.uploadImage(imageBitmap, AppConstants.IMAGE_UPLOAD_JOIN);
                    mOnBoardingActivityViewModel.flagRemovePicJoin.set(true);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("area", "area");
            }
        } else if (requestCode == AppConstants.IMAGE_UPLOAD_REGISTRATION && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                assert extras != null;
                Uri selectedImage = data.getData();
                try {
                    imageBitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                assert imageBitmap1 != null;
                imageBitmap1.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                //mActivityOnboardingBinding.imgRegistration.setImageBitmap(null);
                //mActivityOnboardingBinding.imgRegistration.setImageBitmap(imageBitmap1);


                Bitmap bitmap = imageBitmap1;
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

                mOnBoardingActivityViewModel.uploadImage(imageBitmap1, AppConstants.IMAGE_UPLOAD_REGISTRATION);
                mOnBoardingActivityViewModel.flagRemovePicReg.set(true);
            } else {
            }
        } else {

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (data != null) {
                Bundle extras = data.getExtras();
                assert extras != null;
                Uri selectedImage = result.getUri();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                assert imageBitmap != null;
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


                Bitmap bitmap = imageBitmap;
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

                mOnBoardingActivityViewModel.uploadImage(imageBitmap, AppConstants.IMAGE_UPLOAD_JOIN);
            }

        }

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
                        Intent intent = FeedbackSupportActivity.newIntent(CommunityActivity.this,AppConstants.SCREEN_MY_ACCOUNT,AppConstants.SCREEN_FEEDBACK_SUPPORT);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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
                        String houseFlatNo = mActivityOnboardingBinding.edtHouse.getText().toString();
                        String floorNo = mActivityOnboardingBinding.edtFloorNo.getText().toString();
                        if (validJoin()) {
                            mOnBoardingActivityViewModel.joinTheCommunityAPI(imageUrl, houseFlatNo, floorNo, true);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

    @Override
    public void knowMore() {

        Intent inIntent = CommunityOnBoardingActivity.newIntent(CommunityActivity.this);
        inIntent.putExtra("next", false);
        startActivity(inIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }


}
