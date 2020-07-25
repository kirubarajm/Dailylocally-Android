package com.dailylocally.ui.cart;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.R;
import com.dailylocally.utilities.DailylocallyApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SubscribeItemViewModel {

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> startingDate = new ObservableField<>();
    public final ObservableField<String> veg_type = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> productDes = new ObservableField<>();
    public final ObservableField<String> totalPktSize = new ObservableField<>();


    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<String> availability = new ObservableField<>();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean isAvailable = new ObservableBoolean();
    public final ObservableBoolean isVeg = new ObservableBoolean();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableBoolean mon = new ObservableBoolean();
    public final ObservableBoolean tue = new ObservableBoolean();
    public final ObservableBoolean wed = new ObservableBoolean();
    public final ObservableBoolean thu = new ObservableBoolean();
    public final ObservableBoolean fri = new ObservableBoolean();
    public final ObservableBoolean sat = new ObservableBoolean();
    public final ObservableBoolean sun = new ObservableBoolean();
    private final ObservableField<Integer> price = new ObservableField<>();
    private final ObservableField<Integer> quantity = new ObservableField<>();
    private final SubscribeItemViewModelListener mListener;
    private final CartResponse.SubscriptionItem dishList;


    public SubscribeItemViewModel(SubscribeItemViewModelListener mListener, CartResponse.SubscriptionItem dishList) {

        this.mListener = mListener;
        this.dishList = dishList;
        product_name.set(dishList.getProductname());
        // product_name.set("Abcdefghijklmnopqrstuvwxyz a b c d e f g h i j k l m n o p q r s t u v w x y z ");

        sprice.set(DailylocallyApp.getInstance().getString(R.string.rupees_symbol) + " " + String.valueOf(dishList.getAmount()));
        image.set(dishList.getImage());
        //   weight.set(dishList.getWeight() +" "+dishList.getUnit()+" | "+dishList.getNoOfDeliveries() + " days");
        weight.set(dishList.getWeight() + " " + dishList.getUnit() + " | " + dishList.getPacketInfo() + " " + dishList.getPkts());
        totalPktSize.set(dishList.getPacketTotalInfo() + " " + dishList.getPkts());

        startingDate.set("Starting on "+ parseDateChange(dishList.getStartingDate()));

        isAvailable.set(dishList.getAvailablity());

        mon.set(dishList.getMon() == 1);
        tue.set(dishList.getTue() == 1);
        wed.set(dishList.getWed() == 1);
        thu.set(dishList.getThur() == 1);
        fri.set(dishList.getFri() == 1);
        sat.set(dishList.getSat() == 1);
        sun.set(dishList.getSun() == 1);

    }

    public String parseDateChange(String time) {
        String  inputPattern= "yyyy-MM-dd";
        String  outputPattern= "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void edit() {
        mListener.edit(dishList);
    }

    public void deleteSubcription() {
        mListener.deleteSubcription(dishList);
    }

 public void subsItemClick() {
        mListener.subsItemClick(dishList);
    }

    public interface SubscribeItemViewModelListener {
        void reload();

        void edit(CartResponse.SubscriptionItem product);
        void deleteSubcription(CartResponse.SubscriptionItem product);
        void subsItemClick(CartResponse.SubscriptionItem product);
    }

}
