package com.dailylocally.ui.joinCommunity.contactWhatsapp;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
import java.util.List;

public class ContactWhatsAppViewModel extends BaseViewModel<ContactWhatsAppNavigator> {


    public ObservableList<CommunityResponse.Result> communityItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CommunityResponse.Result>> communityItemsLiveData;
    public final ObservableField<String> cmId = new ObservableField<>();

    public final ObservableBoolean register = new ObservableBoolean();
    public final ObservableBoolean joinExpandView = new ObservableBoolean();
    public final ObservableBoolean joinTheCommunity = new ObservableBoolean();
    public final ObservableBoolean completeRegistration = new ObservableBoolean();

    public ContactWhatsAppViewModel(DataManager dataManager) {
        super(dataManager);
        communityItemsLiveData = new MutableLiveData<>();
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public void onWhatsAppClick(){
        if (getNavigator()!=null){
            getNavigator().onWhatsAppClick();
        }
    }

}
