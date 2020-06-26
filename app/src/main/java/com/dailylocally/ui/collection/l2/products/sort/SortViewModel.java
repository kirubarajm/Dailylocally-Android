package com.dailylocally.ui.collection.l2.products.sort;


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
import com.dailylocally.ui.collection.l2.products.CollectionProductsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class SortViewModel extends BaseViewModel<SortNavigator> {

    public ObservableList<SortItems.Result> sortItems = new ObservableArrayList<>();
    public ObservableField<String> sortTitle = new ObservableField<>();
    public String scl2id;
        private MutableLiveData<List<SortItems.Result>> sortItemsLiveData;

    public SortViewModel(DataManager dataManager) {
        super(dataManager);
        sortItemsLiveData = new MutableLiveData<>();


    }


    public void addtoFilterList(List<SortItems.Result> results) {
        sortItems.clear();
        sortItems.addAll(results);
    }


    public MutableLiveData<List<SortItems.Result>> getSortItemsLiveData() {
        return sortItemsLiveData;
    }


    public void addToFilter(String id) {
        List<CollectionProductsRequest.Brandlist> brandlists = new ArrayList<>();
        Gson sGson = new GsonBuilder().create();
        CollectionProductsRequest collectionProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), CollectionProductsRequest.class);

        if (collectionProductsRequest != null) {

            if (collectionProductsRequest.getBrandlist()!=null){

                if (collectionProductsRequest.getBrandlist().size()>0){



                }

            }


        }


        Gson gson = new Gson();
        String request = gson.toJson(collectionProductsRequest);


    }

    public void removeFromFilter(String id) {


    }

    public void apply() {
        getDataManager().saveIsFilterApplied(true);
        getNavigator().applyFilter();
    }


    public void clearAll() {
        Gson sGson = new GsonBuilder().create();
        CollectionProductsRequest collectionProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), CollectionProductsRequest.class);

        if (collectionProductsRequest !=null){
            collectionProductsRequest.setSortid(0);
            Gson gson = new Gson();
            String request = gson.toJson(collectionProductsRequest);
            getDataManager().saveFiletrSort(request);
        }

        getNavigator().clearFilters();


    }

    public void close() {
        getNavigator().close();

    }

    public void getSortItemList(String scl2id) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            Gson sGson = new GsonBuilder().create();
            CollectionProductsRequest fCollectionProductsRequest = sGson.fromJson(getDataManager().getFilterSort(), CollectionProductsRequest.class);
            if (fCollectionProductsRequest !=null){
                if (!fCollectionProductsRequest.getScl2Id().equals(scl2id)){
                    getDataManager().saveFiletrSort(null);
                }
            }

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.GET_SORT, SortItems.class, new Response.Listener<SortItems>() {
                @Override
                public void onResponse(SortItems response) {
                    try {
                        if (response != null) {
                            if (response.getStatus()) {
                                if (response.getResult() != null) {
                                    sortTitle.set(response.getTitle());
                                    sortItemsLiveData.setValue(response.getResult());
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
