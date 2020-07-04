package com.dailylocally.ui.productDetail.dialogProductCancel;

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


import javax.inject.Inject;


public class DialogProductCancel extends BaseBottomSheetFragment<DialogProductCancelBinding, DialogProductCancelViewModel> implements DialogProductCancelCallBack {

    private static final String TAG = DialogProductCancel.class.getSimpleName();

    @Inject
    DialogProductCancelViewModel mDialogRateKitchenViewModel;

    DialogProductCancelBinding mDialogProductCancelBinding;

    String doid , vpid;


    public static DialogProductCancel newInstance() {
        DialogProductCancel fragment = new DialogProductCancel();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
            vpid = getArguments().getString("vpid", null);
        }
    }

    @Override
    public void productCancelClick() {
        mDialogRateKitchenViewModel.cancelProductAPICall(doid,vpid);
    }

    @Override
    public void goBackClick() {
        dismiss();
    }

    @Override
    public void cancelSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void cancelFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
