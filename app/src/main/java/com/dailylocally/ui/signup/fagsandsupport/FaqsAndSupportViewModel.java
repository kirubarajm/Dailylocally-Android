package com.dailylocally.ui.signup.fagsandsupport;



import androidx.databinding.ObservableBoolean;
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
import com.dailylocally.utilities.SupportResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class FaqsAndSupportViewModel extends BaseViewModel<FaqsAndSupportNavigator> {
    public ObservableBoolean contact = new ObservableBoolean();

    public ObservableField<String> support=new ObservableField<>();

    public final ObservableBoolean supportNumber = new ObservableBoolean();


    public FaqsAndSupportViewModel(DataManager dataManager) {
        super(dataManager);
        contact.set(false);
        support.set("0");
        supportNumber.set(false);
    }


    public void faq() {
        getNavigator().feedBackClick();
    }

    public void supportClick() {
           getNavigator().supportClick();

    }

    public void goBack() {
        getNavigator().goBack();

    }

    public void callCustomer() {
        getNavigator().callCusstomerCare();

    }

}
