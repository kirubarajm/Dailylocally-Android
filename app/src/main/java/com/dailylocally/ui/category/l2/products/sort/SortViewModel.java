package com.dailylocally.ui.category.l2.products.sort;


import androidx.databinding.ObservableArrayList;
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

import dagger.Module;

@Module
public class SortViewModel extends BaseViewModel<SortNavigator> {

    public ObservableList<SortItems.Result> sortItems = new ObservableArrayList<>();
    private MutableLiveData<List<SortItems.Result>> sortItemsLiveData;


    public SortViewModel(DataManager dataManager) {
        super(dataManager);
        sortItemsLiveData = new MutableLiveData<>();
    }


    public void addtoFilterList(List<SortItems.Result> results) {
        sortItems.clear();
        sortItems.addAll(results);
    }


    public MutableLiveData<List<SortItems.Result>> getFilterItemsLiveData() {
        return sortItemsLiveData;
    }


    public void addToFilter(Integer id) {
    }

    public void removeFromFilter(Integer id) {
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


    public void getSorts() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.GET_SORT, SortItems.class, new Response.Listener<SortItems>() {
                @Override
                public void onResponse(SortItems response) {
                    try {
                        if (response != null) {
                            if (response.getStatus()) {
                                if (response.getResult() != null) {
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
