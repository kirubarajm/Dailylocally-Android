package com.dailylocally.ui.joinCommunity.viewProfilePic;

import androidx.databinding.ObservableField;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewPhotoViewModel extends BaseViewModel<ViewPhotoNavigator> {


    public final ObservableField<String> whatsAppLink = new ObservableField<>();

    public ViewPhotoViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public void editClick(){
        if (getNavigator()!=null){
            getNavigator().edit();
        }
    }

    public void removeClick(){
        if (getNavigator()!=null){
            getNavigator().remove();
        }
    }

}
