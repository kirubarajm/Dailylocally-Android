package com.dailylocally.ui.home;


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

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean fullEmpty = new ObservableBoolean();
    public final ObservableBoolean kitchenListLoading = new ObservableBoolean();
    public final ObservableField<String> emptyImageUrl = new ObservableField<>();
    public final ObservableField<String> emptyContent = new ObservableField<>();
    public final ObservableField<String> headerContent = new ObservableField<>();
    public final ObservableField<String> emptySubContent = new ObservableField<>();
    public final ObservableField<String> headerSubContent = new ObservableField<>();
    public final ObservableField<String> categoryTitle = new ObservableField<>();
    public final ObservableField<String> collectionTitle = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();


    public ObservableList<HomepageResponse.Result> categoryList = new ObservableArrayList<>();
    private MutableLiveData<List<HomepageResponse.Result>> categoryListLiveData;


    public HomeViewModel(DataManager dataManager) {
        super(dataManager);
        categoryListLiveData = new MutableLiveData<>();
        fetchCategoryList();
    }

    public MutableLiveData<List<HomepageResponse.Result>> getCategoryListLiveData() {
        return categoryListLiveData;
    }


    public void addCategoryToList(List<HomepageResponse.Result> items) {
        categoryList.clear();
        categoryList.addAll(items);

    }


    public String updateAddressTitle() {
        addressTitle.set(getDataManager().getCurrentAddressTitle());
        return getDataManager().getCurrentAddressTitle();

    }

    public boolean isAddressAdded() {

        return getDataManager().getCurrentLat() != null;

    }

    public void fetchCategoryList() {

        /*if (getDataManager().getCurrentLat() == null) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
            kitchenListLoading.set(true);
            HomePageRequest homePageRequest = new HomePageRequest();
           *//* homePageRequest.setUserid(getDataManager().getCurrentUserId());
            homePageRequest.setLat(getDataManager().getCurrentLat());
            homePageRequest.setLon(getDataManager().getCurrentLng());*//*

            homePageRequest.setUserid("1");
            homePageRequest.setLat("12.979937");
            homePageRequest.setLon( "80.218418");


            GsonRequest gsontoJsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CATEGORY_LIST, HomepageResponse.class, homePageRequest, new Response.Listener<HomepageResponse>() {

                @Override
                public void onResponse(HomepageResponse response) {


                    if (response != null) {

                        getDataManager().saveServiceableStatus(false, response.getUnserviceableTitle(), response.getUnserviceableSubtitle());
                        serviceable.set(response.getServiceablestatus());
                        unserviceableTitle.set(response.getUnserviceableTitle());
                        unserviceableSubTitle.set(response.getUnserviceableSubtitle());
                        emptyImageUrl.set(response.getEmptyUrl());
                        emptyContent.set(response.getEmptyContent());
                        emptySubContent.set(response.getEmptySubconent());
                        headerContent.set(response.getHeaderContent());
                        headerSubContent.set(response.getHeaderSubconent());
                        categoryTitle.set(response.getCategoryTitle());


                        if (getNavigator() != null)
                            getNavigator().changeHeaderText(response.getHeaderContent());

                        if (response.getResult() != null && response.getResult().size() > 0) {
                            fullEmpty.set(false);
                            categoryListLiveData.setValue(response.getResult());
                            if (getNavigator() != null)
                                getNavigator().kitchenLoaded();

                        } else {
                            fullEmpty.set(true);
                            if (getNavigator() != null)
                                getNavigator().kitchenLoaded();
                        }


                    } else {
                        fullEmpty.set(true);
                        if (getNavigator() != null)
                            getNavigator().kitchenLoaded();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getNavigator() != null)
                        getNavigator().kitchenLoaded();
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsontoJsonRequest);

        }*/


        


    }
}