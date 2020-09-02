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


public class CommunityActivityViewModel extends BaseViewModel<CommunityActivityNavigator> {


    public ObservableList<CommunityResponse.Result> communityItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CommunityResponse.Result>> communityItemsLiveData;
    public final ObservableField<String> cmId = new ObservableField<>();

    public final ObservableBoolean register = new ObservableBoolean();
    public final ObservableBoolean joinExpandView = new ObservableBoolean();
    public final ObservableBoolean joinTheCommunity = new ObservableBoolean();
    public final ObservableBoolean completeRegistration = new ObservableBoolean();
    String imageUrl = "";


    public CommunityActivityViewModel(DataManager dataManager) {
        super(dataManager);
        communityItemsLiveData = new MutableLiveData<>();
    }

    public void checkUpdate() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(DailylocallyApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {


                //    Toast.makeText(MvvmApp.getInstance(),String.valueOf(MvvmApp.getInstance().getVersionCode()) , Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public void onRegistrationClick(){
        if (getNavigator()!=null){
            getNavigator().registrationClick();
        }
    }

    public void joinClick(){
        if (getNavigator()!=null){
            getNavigator().joinClick();
        }
    }

    public void joinCommunityUploadImageClick(){
        if (getNavigator()!=null){
            getNavigator().uploadJoinImageClick();
        }
    }

    public void registerCommunityUploadImageClick(){
        if (getNavigator()!=null){
            getNavigator().uploadRegisterImageClick();
        }
    }

    public void joinTheCommunityClick(){
        if (getNavigator()!=null){
            getNavigator().joinTheCommunityClick();
        }
    }

    public void completeRegistrationClick(){
        if (getNavigator()!=null){
            getNavigator().completeRegistrationClick();
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

    public void quickSearch(String search) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            SearchCommunityRequest communityRequest = new SearchCommunityRequest(search);
            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_SEARCH, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            Gson sGson = new GsonBuilder().create();
                            CommunityResponse communityResponse1 = sGson.fromJson(response.toString(), CommunityResponse.class);
                            communityItemsLiveData.setValue(communityResponse1.getResult());

                            ///for marker
                            getNavigator().mapLatLngArray(communityResponse1.getResult());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null) {
                        //getNavigator().updateFailure("Failed to update");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getCommunityList() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        String lat = getDataManager().getCurrentLat();
        String lng = getDataManager().getCurrentLng();
        try {
        CommunityRequest communityRequest = new CommunityRequest(userId,lat,lng);
        Gson gson = new GsonBuilder().create();
        String payloadStr = gson.toJson(communityRequest);
                JsonObjectRequest jsonObjectRequest = null;
        setIsLoading(true);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_LIST, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setIsLoading(false);
                try {
                    if (response.getString("status").equals("true")) {
                        Gson sGson = new GsonBuilder().create();
                        CommunityResponse communityResponse1 = sGson.fromJson(response.toString(), CommunityResponse.class);
                        communityItemsLiveData.setValue(communityResponse1.getResult());

                        ///for marker
                        getNavigator().mapLatLngArray(communityResponse1.getResult());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                if (getNavigator() != null) {
                    //getNavigator().updateFailure("Failed to update");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
            }
        };
        DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void joinTheCommunityAPI(String profileImage,String houseFlatNo,String floorNo) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();

        try {
            JoinCommunityRequest communityRequest = new JoinCommunityRequest(userId,cmId.get(),profileImage,houseFlatNo,floorNo);

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
                        }else {
                            if (getNavigator()!=null){
                                getNavigator().whatAppScreenFailure(response.getString("message"));
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
                    if (getNavigator() != null) {
                        getNavigator().whatAppScreenFailure("Error");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void completeRegistrationAPI(String communityName,String lat,String lon,String apartmentName,String profileImage,String noOfApartments,String flatNo,
                                        String floorNo,String communityAddress,String area) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        try {
            CompleteRegistrationRequest communityRequest = new CompleteRegistrationRequest(communityName,lat,lon,apartmentName,profileImage,userId,noOfApartments,flatNo,
                    floorNo,communityAddress,area);
            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COMPLETE_REGISTRATION, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            if (getNavigator()!=null){
                                getNavigator().whatAppScreenSuccess(response.getString("message"));
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
                    if (getNavigator() != null) {
                        //getNavigator().updateFailure("Failed to update");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
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
                                imageUrl ="";
                                imageUrl = response.getData().getLocation();
                                getNavigator().uploaded(imageUrl);
                            }
                        } else if (REQUEST_CODE == AppConstants.IMAGE_UPLOAD_REGISTRATION) {
                            if (getNavigator() != null) {
                                imageUrl ="";
                                imageUrl = response.getData().getLocation();
                                getNavigator().uploaded1(imageUrl);
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
