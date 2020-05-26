package com.dailylocally.ui.cart;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class SubscribeItemViewModel {

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> futureDate = new ObservableField<>();
    public final ObservableField<String> veg_type = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> productDes = new ObservableField<>();


    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<String> availability = new ObservableField<>();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean isAvailable = new ObservableBoolean();
    public final ObservableBoolean isVeg = new ObservableBoolean();
    public final ObservableField<String> weight = new ObservableField<>();
    private final ObservableField<Integer> price = new ObservableField<>();
    private final ObservableField<Integer> quantity = new ObservableField<>();
    private final SubscribeItemViewModelListener mListener;
    private final CartResponse.SubscriptionItem dishList;


    public SubscribeItemViewModel(SubscribeItemViewModelListener mListener, CartResponse.SubscriptionItem dishList) {

        this.mListener = mListener;
        this.dishList = dishList;

        product_name.set(dishList.getProductname());
      // product_name.set("Abcdefghijklmnopqrstuvwxyz a b c d e f g h i j k l m n o p q r s t u v w x y z ");

        sprice.set("INR. " + String.valueOf(dishList.getMrp()));
        image.set(dishList.getImage());

    }


    public void edit(){
        mListener.edit(dishList);
    }

    public interface SubscribeItemViewModelListener {
        void reload();

        void edit(CartResponse.SubscriptionItem product);
    }

}
