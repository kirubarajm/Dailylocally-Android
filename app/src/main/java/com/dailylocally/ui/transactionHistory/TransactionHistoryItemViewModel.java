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

        String strDateTime = cartdetail.getTransactionTime();
        String outputDateStr = "";
        try {
            if (strDateTime != null) {
                DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = currentFormat.parse(strDateTime);
                outputDateStr = dateFormat.format(date);

                transactionTime.set(outputDateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface TransactionHistoryViewModelListener {
        void onItemClick(TransactionHistoryResponse.Result cartdetail);
    }

}
