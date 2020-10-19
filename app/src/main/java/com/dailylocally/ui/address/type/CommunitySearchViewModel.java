package com.dailylocally.ui.address.type;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.joinCommunity.CommunityRequest;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
import com.dailylocally.ui.joinCommunity.SearchCommunityRequest;
import com.dailylocally.ui.update.UpdateRequest;
import com.dailylocally.ui.update.UpdateResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommunitySearchViewModel extends BaseViewModel<CommunitySearchNavigator> {

    public final ObservableBoolean newUser = new ObservableBoolean();
    public final ObservableField<String> version = new ObservableField<>();
    public ObservableList<CommunityResponse.Result> communityItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CommunityResponse.Result>> communityItemsLiveData;
    public CommunitySearchViewModel(DataManager dataManager) {
        super(dataManager);
        communityItemsLiveData = new MutableLiveData<>();

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
            SearchCommunityRequest communityRequest = new SearchCommunityRequest(search,getDataManager().getCurrentLat(),getDataManager().getCurrentLng());
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

                        }else {
                            List<CommunityResponse.Result> results=new ArrayList<>();
                            communityItemsLiveData.setValue(results);
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
    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }


 public void addManualy(){
        if (getNavigator()!=null){
            getNavigator().addManualy();
        }
    }


}
