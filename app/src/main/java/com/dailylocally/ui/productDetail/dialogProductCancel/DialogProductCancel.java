package com.dailylocally.ui.productDetail.dialogProductCancel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.DialogProductCancelBinding;
import com.dailylocally.ui.base.BaseBottomSheetFragment;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;


import javax.inject.Inject;


public class DialogProductCancel extends BaseBottomSheetFragment<DialogProductCancelBinding, DialogProductCancelViewModel> implements DialogProductCancelCallBack {

    private static final String TAG = DialogProductCancel.class.getSimpleName();

    @Inject
    DialogProductCancelViewModel mDialogRateKitchenViewModel;

    DialogProductCancelBinding mDialogProductCancelBinding;

    String doid , dayOrderPid;
    ProductCancelListenerCallBack mProductCancelListenerCallBack;


    public static DialogProductCancel newInstance(String fromPage, String toPage) {
        DialogProductCancel fragment = new DialogProductCancel();
        Bundle args = new Bundle();
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public int getBindingVariable() {
        return BR.dialogProductCancelViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_product_cancel;
    }

    @Override
    public DialogProductCancelViewModel getViewModel() {
        return mDialogRateKitchenViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogRateKitchenViewModel.setNavigator(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialogProductCancelBinding = getViewDataBinding();

        if (getArguments()!=null) {
            doid = getArguments().getString("doid", null);
            dayOrderPid = getArguments().getString("dayOrderPid", null);
        }

        Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_DIALOG_PRODUCT_CANCEL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductCancelListenerCallBack) {
            //init the listener
            mProductCancelListenerCallBack = (ProductCancelListenerCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }

    }


    @Override
    public void productCancelClick() {
        mDialogRateKitchenViewModel.cancelProductAPICall(doid, dayOrderPid);
    }

    @Override
    public void goBackClick() {
        dismiss();
    }

    @Override
    public void cancelSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        dismiss();
        mProductCancelListenerCallBack.sendData(true);
    }

    @Override
    public void cancelFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
