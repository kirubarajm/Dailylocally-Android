package com.dailylocally.ui.community.event;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

import java.util.List;

import im.getsocial.sdk.communities.GetSocialActivity;

public class EventViewModel extends BaseViewModel<EventNavigator> {
    public final ObservableField<String> title = new ObservableField<>();

    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    public MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;

    public EventViewModel(DataManager dataManager) {
        super(dataManager);
        socialActivitiesListLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<List<GetSocialActivity>> getsocialActivitiesListLiveData() {
        return socialActivitiesListLiveData;
    }


    public void addCommunityPostToList(List<GetSocialActivity> items) {
        getSocialActivities.clear();
        getSocialActivities.addAll(items);

    }
 public void back(){
        getNavigator().back();
 }

}
