package com.dailylocally.ui.category.l2.products.filter;


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
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.ui.category.l2.products.ProductsResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class FilterViewModel extends BaseViewModel<FilterNavigator> {

    public ObservableList<FilterItems.Result> filterItems = new ObservableArrayList<>();
    public ObservableField<String> filterTitle = new ObservableField<>();
    private MutableLiveData<List<FilterItems.Result>> filterItemsLiveData;

    ProductsRequest productsRequest=new ProductsRequest();

    List<ProductsRequest.Brandlist> brandlists=new ArrayList<>();




public  String scl2id;

    public FilterViewModel(DataManager dataManager) {
        super(dataManager);
        filterItemsLiveData = new MutableLiveData<>();



    }


    public void addtoFilterList(List<FilterItems.Result> results) {
        filterItems.clear();
        filterItems.addAll(results);
    }


    public MutableLiveData<List<FilterItems.Result>> getFilterItemsLiveData() {
        return filterItemsLiveData;
    }


    public void addToFilter(String id) {







    }

    public void removeFromFilter(String id) {





    }

    public void apply() {
        getDataManager().saveIsFilterApplied(true);
        getNavigator().applyFilter();
    }


    public void clearAll() {
        getDataManager().saveIsFilterApplied(false);
        getNavigator().clearFilters();


    }

    public void close() {
        getNavigator().close();

    }

    public void getFilters(String scl2id) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.GET_FILTERS + scl2id, FilterItems.class, new Response.Listener<FilterItems>() {
                @Override
                public void onResponse(FilterItems response) {
                    try {
                        if (response != null) {
                            if (response.getStatus()) {
                                if (response.getResult() != null) {
                                    filterTitle.set(response.getTitle());
                                    filterItemsLiveData.setValue(response.getResult());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
