package com.dailylocally.ui.productDetail.dialogProductCancel;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.coupons.CouponValidateResponse;
import com.dailylocally.ui.coupons.CouponsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

public class DialogProductCancelViewModel extends BaseViewModel<DialogProductCancelCallBack> {

    Response.ErrorListener errorListener;

    public DialogProductCancelViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void cancelClick(){
        if(getNavigator()!=null){
            getNavigator().productCancelClick();
        }
    }

    public void goBackClick(){
        if(getNavigator()!=null){
            getNavigator().goBackClick();
        }
    }

    public void cancelProductAPICall(String doid,String vpid){
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PRODUCT_CANCEL, ProductCancelResponse.class,
                new ProductCancelRequest(doid,vpid),
                new Response.Listener<ProductCancelResponse>() {
                    @Override
                    public void onResponse(ProductCancelResponse response) {
                        try {
                            if (getNavigator()!=null){
                                getNavigator().cancelSuccess("");
                            }

                            if (getNavigator()!=null){
                                getNavigator().cancelFailed("");
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
                    getNavigator().cancelFailed("Failed");
                }
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }

}
