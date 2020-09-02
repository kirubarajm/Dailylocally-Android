package com.dailylocally.ui.joinCommunity.contactWhatsapp;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ContactWhatsAppViewModel extends BaseViewModel<ContactWhatsAppNavigator> {


    public final ObservableField<String> whatsAppLink = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> phoneno = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subtitle1 = new ObservableField<>();
    public final ObservableField<String> subtitle2 = new ObservableField<>();

    public ContactWhatsAppViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public void onWhatsAppClick(){
        if (getNavigator()!=null){
            getNavigator().onWhatsAppClick();
        }
    }

    public void callClick(){
        if (getNavigator()!=null){
            getNavigator().callClick();
        }
    }

    public void fetchData() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_WHATSAPP_SCREEN, new JSONObject(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            Gson sGson = new GsonBuilder().create();
                            ContactWhatsAppResponse communityResponse1 = sGson.fromJson(response.toString(), ContactWhatsAppResponse.class);
                            whatsAppLink.set(communityResponse1.getResult().get(0).getWhatsUpLink());
                            imageUrl.set(communityResponse1.getResult().get(0).getImageUrl());
                            phoneno.set(communityResponse1.getResult().get(0).getPhoneno());
                            //title.set(communityResponse1.getResult().get(0).getTitle());
                            subtitle1.set(communityResponse1.getResult().get(0).getSubtitle1());
                            subtitle2.set(communityResponse1.getResult().get(0).getSubtitle2());

                            if (getNavigator()!=null){
                                getNavigator().changeHeader(communityResponse1.getResult().get(0).getTitle() + " "+getDataManager().getCurrentUserName());
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


}
