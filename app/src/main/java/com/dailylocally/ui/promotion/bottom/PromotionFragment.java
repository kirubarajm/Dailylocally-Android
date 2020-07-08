package com.dailylocally.ui.promotion.bottom;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentPromotionBinding;
import com.dailylocally.ui.base.BaseBottomSheetFragment;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.WebViewClientImpl;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class PromotionFragment extends BaseBottomSheetFragment<FragmentPromotionBinding, PromotionViewModel> implements PromotionNavigator {


    public static final String TAG = PromotionFragment.class.getSimpleName();
    @Inject
    PromotionViewModel mPromotionViewModel;

    FragmentPromotionBinding mFragmentPromotionBinding;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_KITCHEN_FILTER;


    public static PromotionFragment newInstance() {
        Bundle args = new Bundle();
        PromotionFragment fragment = new PromotionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.filterViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_promotion;
    }

    @Override
    public PromotionViewModel getViewModel() {
        return mPromotionViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPromotionViewModel.setNavigator(this);
   //     setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPromotionBinding = getViewDataBinding();

       /* setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
        getBaseActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

        //  this.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_bottom_sheet);

        //      mPromotionViewModel.url.set("https://eattovo.s3.amazonaws.com/upload/admin/makeit/product/1578500485888-Infinity%20regions-40.jpg");


        if (getArguments() != null) {

            if (getArguments().getInt(AppConstants.PROMOTION_TYPE)==1) {
                mPromotionViewModel.isImage.set(false);

                mFragmentPromotionBinding.imageView.setVisibility(View.GONE);
                mFragmentPromotionBinding.webview.setVisibility(View.VISIBLE);
                WebSettings webSettings = mFragmentPromotionBinding.webview.getSettings();
                webSettings.setJavaScriptEnabled(true);

                WebViewClientImpl webViewClient = new WebViewClientImpl(getBaseActivity());
                mFragmentPromotionBinding.webview.setWebViewClient(webViewClient);
                mFragmentPromotionBinding.webview.loadUrl(getArguments().getString(AppConstants.PROMOTION_URL));


            }else {
                mPromotionViewModel.isImage.set(true);


                mFragmentPromotionBinding.imageView.setVisibility(View.VISIBLE);
                mFragmentPromotionBinding.webview.setVisibility(View.GONE);


                Glide.with(getBaseActivity())
                        .load(getArguments().getString(AppConstants.PROMOTION_URL))
                        //   .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                      //  .transform(new DelayBitmapTransformation(0))
                        // .listener(new LoggingListener<String, Bitmap>())
                        .into(mFragmentPromotionBinding.imageView);
            }
            mPromotionViewModel.saveSeen(getArguments().getInt(AppConstants.PROMOTION_ID));
        }
        mFragmentPromotionBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void closeDialog() {
        dismiss();
    }


}
