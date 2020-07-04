package com.dailylocally.ui.pronotion.bottom;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.Module;

@Module
public class PromotionViewModel extends BaseViewModel<PromotionNavigator> {


    public final ObservableField<String> url = new ObservableField<>();


    public ObservableBoolean isImage = new ObservableBoolean();


    public PromotionViewModel(DataManager dataManager) {
        super(dataManager);
        // url.set("https://www.whynotdeals.com//wp-content/uploads//2016/07/what-to-eat-delivery-singapore-promotion-free-delivery-ends-31-dec-2016_why-not-deals-2-e1469516136292.jpg");
        //   url.set("https://eattovo.s3.amazonaws.com/upload/admin/makeit/product/1578500485888-Infinity%20regions-40.jpg");


    }


    void saveSeen(int promotionid) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentdate = df.format(c);

        getDataManager().savePromotionShowedDate(currentdate);

        String dd=getDataManager().getPromotionShowedDate();

       getDataManager().savePromotionDisplayedCount(getDataManager().getPromotionDisplayedCount() + 1);

        getDataManager().savePromotionSeen(true);
        getDataManager().savePromotionId(promotionid);


        getDataManager().savePromotionCustomerId(getDataManager().getCurrentUserId());


    }


    public void closeDialog() {
        getNavigator().closeDialog();
    }

}
