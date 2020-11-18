package com.dailylocally.ui.search;

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
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.util.List;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> resultReturned = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public ObservableList<QuickSearchResponse.Result.ProductsList> searchItemViewModels = new ObservableArrayList<>();
    public ObservableList<QuickSearchResponse.Result.SubcategoryList> searchSubCategoryItemViewModels = new ObservableArrayList<>();
    public ObservableList<SearchProductResponse.Result> searchProductItemViewModels = new ObservableArrayList<>();
    String strLat = "", strLng = "", userId = "";
    private MutableLiveData<List<QuickSearchResponse.Result.ProductsList>> searchItemsLiveData;
    private MutableLiveData<List<QuickSearchResponse.Result.SubcategoryList>> searchSubCategoryItemsLiveData;
    private MutableLiveData<List<SearchProductResponse.Result>> searchProductItemsLiveData;

    public SearchViewModel(DataManager dataManager) {
        super(dataManager);
        searchItemsLiveData = new MutableLiveData<>();
        searchSubCategoryItemsLiveData = new MutableLiveData<>();

        searchProductItemsLiveData = new MutableLiveData<>();
        strLat = getDataManager().getCurrentLat();
        strLng = getDataManager().getCurrentLng();
        userId = getDataManager().getCurrentUserId();
    }

    public MutableLiveData<List<QuickSearchResponse.Result.ProductsList>> getSearchItemsLiveData() {
        return searchItemsLiveData;
    }

    public ObservableList<QuickSearchResponse.Result.ProductsList> getSearchItemViewModels() {
        return searchItemViewModels;
    }

    public void addSearchItemsToList(List<QuickSearchResponse.Result.ProductsList> ordersItems) {
        if (ordersItems != null) {
            searchItemViewModels.clear();
            searchItemViewModels.addAll(ordersItems);
        }
    }

    public MutableLiveData<List<QuickSearchResponse.Result.SubcategoryList>> getSearchSubCategoryItemsLiveData() {
        return searchSubCategoryItemsLiveData;
    }

    public ObservableList<QuickSearchResponse.Result.SubcategoryList> getSearchSubCategoryItemViewModels() {
        return searchSubCategoryItemViewModels;
    }

    public void addSearchSubCategoryItemsToList(List<QuickSearchResponse.Result.SubcategoryList> ordersItems) {
        if (ordersItems != null) {
            searchSubCategoryItemViewModels.clear();
            searchSubCategoryItemViewModels.addAll(ordersItems);
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
                    QuickSearchResponse.class, new QuickSearchRequest(searchWord, userId, strLat, strLng),
                    new Response.Listener<QuickSearchResponse>() {
                        @Override
                        public void onResponse(QuickSearchResponse response) {
                            if (response.getStatus()) {


                                boolean dataAvailable = false;
                                int size = 0;
                                int size1 = 0;
                                int size2 = 0;


                                if (response.getResult() != null && response.getResult().getProductsList() != null) {
                                    size1 = response.getResult().getProductsList().size();
                                    searchItemsLiveData.setValue(response.getResult().getProductsList());

                                    if (response.getResult() != null && response.getResult().getProductsList().size() > 0) {
                                        dataAvailable = true;
                                    }

                                }

                                if (response.getResult() != null && response.getResult().getSubcategoryList() != null) {
                                    size2 =  response.getResult().getSubcategoryList().size();
                                    searchSubCategoryItemsLiveData.setValue(response.getResult().getSubcategoryList());
                                    if (response.getResult() != null && response.getResult().getSubcategoryList().size() > 0) {
                                        dataAvailable = true;
                                    }
                                }
                                size = size1 + size2;
                                resultReturned.set(String.valueOf(size));

                                if (dataAvailable) {
                                    if (getNavigator() != null) {
                                        getNavigator().quickSearchSuccess();
                                    }
                                } else {
                                    if (getNavigator() != null) {
                                        getNavigator().searchNotFound();
                                    }
                                }
                            }
                            setIsLoading(false);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null) {
                        getNavigator().searchNotFound();
                    }
                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void SearchProduct(String type, String id) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SEARCH_PRODUCT,
                    SearchProductResponse.class, new SearchProductRequest(strLat, strLng, type, id, userId, "product"),
                    new Response.Listener<SearchProductResponse>() {
                        @Override
                        public void onResponse(SearchProductResponse response) {
                            if (response.getStatus()) {
                                if (response.getResult() != null && response.getResult().size() > 0) {
                                    searchProductItemsLiveData.setValue(response.getResult());
                                }
                                if (getNavigator() != null) {
                                    getNavigator().suggestionProductSuccess();
                                }
                            } else {

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