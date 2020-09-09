package com.dailylocally.ui.transactionHistory;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.ui.cart.CartResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionHistoryItemViewModel {

    public final ObservableField<String> transactionId = new ObservableField<>();
    public final ObservableField<String> paymentId = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> transactionTime = new ObservableField<>();
    public final ObservableField<String> productCount = new ObservableField<>();
    public final ObservableBoolean onlineOrder = new ObservableBoolean();
    public final TransactionHistoryViewModelListener mListener;
    private final TransactionHistoryResponse.Result cartdetail;

    public TransactionHistoryItemViewModel(TransactionHistoryResponse.Result cartdetail, TransactionHistoryViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        transactionId.set(String.valueOf(cartdetail.getOrderid()));

        price.set(String.valueOf(cartdetail.getPrice()));

        if (cartdetail.getOnlineOrder()){
            paymentId.set( "Payment ID : "+String.valueOf(cartdetail.getTsid()));

        }else {
            paymentId.set("Cash on delivery");
        }




        if (cartdetail.getItems().equals("1")) {
            productCount.set("(" + cartdetail.getItems() + " item)");
        }else {
            productCount.set("(" + cartdetail.getItems() + " items)");
        }

        String strDateTime;
        if (cartdetail.getOnlineOrder()){
            strDateTime = cartdetail.getTransactionTime();

        }else {
            strDateTime = cartdetail.getCreatedAt();
        }

        transactionTime.set(getFormatedDate(strDateTime));


    }


    public String getFormatedDate(String cDate){

        String outputDateStr = "";
        try {
            if (cDate != null) {
                DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = currentFormat.parse(cDate);
                outputDateStr = dateFormat.format(date);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }



    public void viewClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface TransactionHistoryViewModelListener {
        void onItemClick(TransactionHistoryResponse.Result cartdetail);
    }

}
