package com.dailylocally.ui.search;

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
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.account.LogoutRequest;
import com.dailylocally.ui.account.LogoutResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.home.HomePageRequest;
import com.dailylocally.ui.home.HomepageResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();

    private MutableLiveData<List<QuickSearchResponse.Datum>> searchItemsLiveData;
    public ObservableList<QuickSearchResponse.Datum> searchItemViewModels = new ObservableArrayList<>();

    private MutableLiveData<List<SearchProductResponse.Product>> searchProductItemsLiveData;
    public ObservableList<SearchProductResponse.Product> searchProductItemViewModels = new ObservableArrayList<>();


    public SearchViewModel(DataManager dataManager) {
        super(dataManager);
        searchItemsLiveData = new MutableLiveData<>();
        searchProductItemsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<QuickSearchResponse.Datum>> getSearchItemsLiveData() {
        return searchItemsLiveData;
    }

    public ObservableList<QuickSearchResponse.Datum> getSearchItemViewModels() {
        return searchItemViewModels;
    }

    public void addSearchItemsToList(List<QuickSearchResponse.Datum> ordersItems) {
        if (ordersItems != null) {
            searchItemViewModels.clear();
            searchItemViewModels.addAll(ordersItems);
        }
    }

    public MutableLiveData<List<SearchProductResponse.Product>> getSearchProductItemsLiveData() {
        return searchProductItemsLiveData;
    }

    public ObservableList<SearchProductResponse.Product> getSearchProductItemViewModels() {
        return searchProductItemViewModels;
    }

    public void addSearchProductItemsToList(List<SearchProductResponse.Product> ordersItems) {
        if (ordersItems != null) {
            searchProductItemViewModels.clear();
            searchProductItemViewModels.addAll(ordersItems);
        }
    }

    public void quickSearch() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.QUICK_SEARCH,
                    QuickSearchResponse.class, new QuickSearchRequest("milk",1,"13.050675","80.2341"),
                    new Response.Listener<QuickSearchResponse>() {
                        @Override
                        public void onResponse(QuickSearchResponse response) {
                            if (response.getStatus()){
                                searchItemsLiveData.setValue(response.getData());
                            }else {

                            }
                            setIsLoading(false);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void SearchProduct() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SEARCH_PRODUCT,
                    SearchProductResponse.class, new SearchProductRequest("12.9760","80.2212",4,1,1),
                    new Response.Listener<SearchProductResponse>() {
                        @Override
                        public void onResponse(SearchProductResponse response) {
                            if (response.getStatus()){
                                searchProductItemsLiveData.setValue(response.getProduct());
                                if (getNavigator()!=null){
                                    getNavigator().suggestionProductSuccess();
                                }
                            }else {

                            }
                            setIsLoading(false);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

}