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

    private MutableLiveData<List<SearchProductResponse.Result>> searchProductItemsLiveData;
    public ObservableList<SearchProductResponse.Result> searchProductItemViewModels = new ObservableArrayList<>();
    String strLat="",strLng="",userId="";

    public SearchViewModel(DataManager dataManager) {
        super(dataManager);
        searchItemsLiveData = new MutableLiveData<>();
        searchProductItemsLiveData = new MutableLiveData<>();
        strLat = getDataManager().getCurrentLat();
        strLng = getDataManager().getCurrentLng();
        userId = getDataManager().getCurrentUserId();
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

    public MutableLiveData<List<SearchProductResponse.Result>> getSearchProductItemsLiveData() {
        return searchProductItemsLiveData;
    }

    public ObservableList<SearchProductResponse.Result> getSearchProductItemViewModels() {
        return searchProductItemViewModels;
    }

    public void addSearchProductItemsToList(List<SearchProductResponse.Result> ordersItems) {
        if (ordersItems != null) {
            searchProductItemViewModels.clear();
            searchProductItemViewModels.addAll(ordersItems);
        }
    }

    public void quickSearch(String searchWord) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.QUICK_SEARCH,
                    QuickSearchResponse.class, new QuickSearchRequest(searchWord,userId,strLat,strLng),
                    new Response.Listener<QuickSearchResponse>() {
                        @Override
                        public void onResponse(QuickSearchResponse response) {
                            if (response.getStatus()) {
                                if (response.getData() != null && response.getData().size() > 0) {
                                    searchItemsLiveData.setValue(response.getData());
                                    if (getNavigator() != null) {
                                        getNavigator().quickSearchSuccess();
                                    }
                                } else {
                                    searchItemsLiveData.setValue(response.getData());
                                    if (getNavigator() != null) {
                                        getNavigator().searchNotFound();
                                    }
                                }
                            }else {
                                searchItemsLiveData.setValue(response.getData());
                                if (getNavigator() != null) {
                                    getNavigator().searchNotFound();
                                }
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

    public void SearchProduct(String type,String id) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SEARCH_PRODUCT,
                    SearchProductResponse.class, new SearchProductRequest(strLat,strLng,type,id,userId,"product"),
                    new Response.Listener<SearchProductResponse>() {
                        @Override
                        public void onResponse(SearchProductResponse response) {
                            if (response.getStatus()){
                                if (response.getResult()!=null && response.getResult().size()>0) {
                                    searchProductItemsLiveData.setValue(response.getResult());
                                }
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