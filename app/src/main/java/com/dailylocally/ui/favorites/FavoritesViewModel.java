package com.dailylocally.ui.favorites;


import androidx.databinding.ObservableField;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;


public class FavoritesViewModel extends BaseViewModel<FavoritesNavigator> {

    public final ObservableField<String> version = new ObservableField<>();

    public FavoritesViewModel(DataManager dataManager) {
        super(dataManager);
        getDataManager().appStartedAgain(true);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

}
