package com.dailylocally.ui.transactionHistory;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.coupons.CouponsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.util.List;


public class TransactionHistoryViewModel extends BaseViewModel<TransactionHistoryNavigator> {

    public final ObservableField<String> version = new ObservableField<>();

    public ObservableList<TransactionHistoryResponse.Result> transactionHistoryItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<TransactionHistoryResponse.Result>> transactionHistoryItemsLiveData;

    public TransactionHistoryViewModel(DataManager dataManager) {
        super(dataManager);
        transactionHistoryItemsLiveData = new MutableLiveData<>();
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public MutableLiveData<List<TransactionHistoryResponse.Result>> getTransactionHistoryItemsLiveData() {
        return transactionHistoryItemsLiveData;
    }

    public ObservableList<TransactionHistoryResponse.Result> getTransactionHistoryItemViewModels() {
        return transactionHistoryItemViewModels;
    }

    public void addTransactionHistoryItemsToList(List<TransactionHistoryResponse.Result> ordersItems) {
        if (ordersItems != null) {
            transactionHistoryItemViewModels.clear();
            transactionHistoryItemViewModels.addAll(ordersItems);
        }
    }

    public void getTransactionHistoryList(){
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_TRANSACTION_LIST, TransactionHistoryResponse.class,
                new CouponsRequest("1"),
                new Response.Listener<TransactionHistoryResponse>() {
                    @Override
                    public void onResponse(TransactionHistoryResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus()){
                                    transactionHistoryItemsLiveData.setValue(response.getResult());
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

}
