package com.dailylocally.ui.coupons;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.search.QuickSearchResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import java.util.List;


public class CouponsViewModel extends BaseViewModel<CouponsNavigator> {

    public final ObservableField<String> version = new ObservableField<>();
    public final ObservableBoolean cartCoupon = new ObservableBoolean();
    public ObservableList<CouponsResponse.Result> couponsItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CouponsResponse.Result>> couponsItemsLiveData;

    public CouponsViewModel(DataManager dataManager) {
        super(dataManager);
        getDataManager().appStartedAgain(true);
        couponsItemsLiveData = new MutableLiveData<>();
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public MutableLiveData<List<CouponsResponse.Result>> getCouponsItemsLiveData() {
        return couponsItemsLiveData;
    }

    public ObservableList<CouponsResponse.Result> getCouponsItemViewModels() {
        return couponsItemViewModels;
    }

    public void addCouponsItemsToList(List<CouponsResponse.Result> ordersItems) {
        if (ordersItems != null) {
            couponsItemViewModels.clear();
            couponsItemViewModels.addAll(ordersItems);
        }
    }

    public void getCouponsList(){
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_COUPONS, CouponsResponse.class,
                new CouponsRequest("1"),
                new Response.Listener<CouponsResponse>() {
                    @Override
                    public void onResponse(CouponsResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus()){
                                    couponsItemsLiveData.setValue(response.getResult());
                                }else {

                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        setIsLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(true);
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public void validateCouponClick(){
        if (getNavigator()!=null){
            getNavigator().validateCouponClick();
        }
    }

    public void validateCoupon(){
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_VALIDATE_COUPONS, CouponsResponse.class,
                new CouponsRequest("1",userId),
                new Response.Listener<CouponsResponse>() {
                    @Override
                    public void onResponse(CouponsResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus()){
                                    if (getNavigator()!=null){
                                        getNavigator().validateCouponSuccess("Success");
                                    }
                                }else {
                                    if (getNavigator()!=null){
                                        getNavigator().validateCouponFailure("Failed");
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        setIsLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(true);
                if (getNavigator()!=null){
                    getNavigator().validateCouponFailure("Failed");
                }
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
