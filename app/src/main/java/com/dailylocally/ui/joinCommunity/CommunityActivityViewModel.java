package com.dailylocally.ui.joinCommunity;


import android.graphics.Bitmap;
import android.util.Base64;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.api.remote.VolleyMultiPartRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.cart.OrderCreateResponse;
import com.dailylocally.ui.update.UpdateRequest;
import com.dailylocally.ui.update.UpdateResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class CommunityActivityViewModel extends BaseViewModel<CommunityActivityNavigator> {


    public ObservableList<CommunityResponse.Result> communityItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CommunityResponse.Result>> communityItemsLiveData;
    public final ObservableField<String> cmId = new ObservableField<>();

    public final ObservableBoolean joinTheCommunity = new ObservableBoolean();



    public final ObservableBoolean flagRemovePicJoin = new ObservableBoolean();
    public final ObservableBoolean flagRemovePicReg = new ObservableBoolean();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public CommunityActivityViewModel(DataManager dataManager) {
        super(dataManager);
        communityItemsLiveData = new MutableLiveData<>();
    }


    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }



    public void joinCommunityUploadImageClick(){
        if (getNavigator()!=null){
            getNavigator().uploadJoinImageClick();
        }
    }

    public void closeClick(){
        if (getNavigator()!=null){
            getNavigator().close();
        }
    }

 public void knowMore(){
        if (getNavigator()!=null){
            getNavigator().knowMore();
        }
    }


    public void joinTheCommunityClick(){
        if (getNavigator()!=null){
            getNavigator().joinTheCommunityClick();
        }
    }



    public MutableLiveData<List<CommunityResponse.Result>> getCommunityListItemsLiveData() {
        return communityItemsLiveData;
    }

    public ObservableList<CommunityResponse.Result> getCommunityListItemViewModels() {
        return communityItemViewModels;
    }

    public void addCommunityListItemsToList(List<CommunityResponse.Result> ordersItems) {
        if (ordersItems != null) {
            communityItemViewModels.clear();
            communityItemViewModels.addAll(ordersItems);
        }
    }


    public void joinTheCommunityAPI(String profileImage,String houseFlatNo,String floorNo,boolean changeAddress) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();

        try {
            JoinCommunityRequest communityRequest = new JoinCommunityRequest(userId,cmId.get(),imageUrl.get(),houseFlatNo,floorNo,getDataManager().getCurrentLat(),getDataManager().getCurrentLng(),changeAddress);

            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_JOIN_THE_COMMUNITY, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            if (getNavigator()!=null){
                                getNavigator().communityJoined(response.getString("message"));
                            }
                            Gson gson = new Gson();
                            JoinCommunityResponse joinCommunityResponse = gson.fromJson(response.toString(), JoinCommunityResponse.class);

                            String completeAddress = joinCommunityResponse.getResult().get(0).getCompleteAddress();
                            String lat = String.valueOf(joinCommunityResponse.getResult().get(0).getLat());
                            String lon = String.valueOf(joinCommunityResponse.getResult().get(0).getLon());
                            String aid = String.valueOf(joinCommunityResponse.getResult().get(0).getAid());
                            String city = String.valueOf(joinCommunityResponse.getResult().get(0).getCity());
                            String addressType = String.valueOf(joinCommunityResponse.getResult().get(0).getAddressType());
                            String flatHouseNo = String.valueOf(joinCommunityResponse.getResult().get(0).getFlatHouseNo());
                            String blockName = String.valueOf(joinCommunityResponse.getResult().get(0).getBlockName());
                            String apartmentName = String.valueOf(joinCommunityResponse.getResult().get(0).getApartmentName());
                            String plotHouseNo = String.valueOf(joinCommunityResponse.getResult().get(0).getPlotHouseNo());
                            String floor = String.valueOf(joinCommunityResponse.getResult().get(0).getFloor());


                            getDataManager().updateCurrentAddress("", completeAddress, lat, lon, city, aid);
                        getDataManager().setCurrentLat(lat);
                        getDataManager().setCurrentLng(lon);
                        getDataManager().setCurrentAddress(completeAddress);
                        getDataManager().setCurrentAddressArea(city);
                        getDataManager().setCurrentAddressTitle(city);
                        getDataManager().setAddressId(aid);

                        if (addressType.equals("1")){
                            String cAddress="No."+flatHouseNo+", "+blockName+", "+apartmentName+", "+completeAddress;
                            getDataManager().setCurrentAddress(cAddress);

                        }else {
                            String cAddress="No."+plotHouseNo+", Floor-"+floor+", "+completeAddress;
                            getDataManager().setCurrentAddress(cAddress);
                        }
                        }else {
                            if (response.getString("show_alert").equals("true")) {
                                if (response.getString("order_available").equals("true")) {
                                    if (getNavigator() != null)
                                        getNavigator().showAlert(response.getString("alert_title"), response.getString("alert_message"), "", "", "", "", "");
                                }else {
                                    if (getNavigator() != null)
                                        getNavigator().showAlert(response.getString("alert_title"), response.getString("alert_message"));
                                }
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadImage(Bitmap bitmap, int REQUEST_CODE) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        if (REQUEST_CODE == AppConstants.IMAGE_UPLOAD_JOIN) {
            if (getNavigator() != null) {
                getNavigator().uploading();
            }
        } else if (REQUEST_CODE == AppConstants.IMAGE_UPLOAD_REGISTRATION) {
            if (getNavigator() != null) {
                getNavigator().uploading1();
            }
        }
        final String image = getStringImage(bitmap);
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, AppConstants.URL_UPLOAD_DOCUMENT_PICKUP,
                DocumentUploadResponse.class, new Response.Listener<DocumentUploadResponse>() {
            @Override
            public void onResponse(DocumentUploadResponse response) {
                if (response != null) {
                    if (response.getSuccess()) {
                        if (REQUEST_CODE == AppConstants.IMAGE_UPLOAD_JOIN) {
                            if (getNavigator() != null) {
                                //imageUrl ="";
                                imageUrl.set(response.getData().getLocation());
                                getNavigator().uploaded(imageUrl.get());
                            }
                        } else if (REQUEST_CODE == AppConstants.IMAGE_UPLOAD_REGISTRATION) {
                            if (getNavigator() != null) {
                                //imageUrl ="";
                                imageUrl.set(response.getData().getLocation());
                                getNavigator().uploaded1(imageUrl.get());
                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "tag");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("accept-version", AppConstants.API_VERSION_ONE);
                headers.put("Authorization", "Bearer " + getDataManager().getApiToken());
                return headers;
            }

            @Override
            protected Map<String, VolleyMultiPartRequest.DataPart> getByteData() {
                Map<String, VolleyMultiPartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("lic", new VolleyMultiPartRequest.DataPart("lic", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        DailylocallyApp.getInstance().addToRequestQueue(volleyMultipartRequest);
    }


}
