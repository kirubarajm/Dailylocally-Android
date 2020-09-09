package com.dailylocally.ui.video;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class VideoViewModel extends BaseViewModel<VideoNavigator> {

    public VideoViewModel(DataManager dataManager) {
        super(dataManager);
    }

 public void back(){
        getNavigator().back();
 }

}
