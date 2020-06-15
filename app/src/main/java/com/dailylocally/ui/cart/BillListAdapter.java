package com.dailylocally.ui.cart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.R;
import com.dailylocally.databinding.ListItemCartBillBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.utilities.DailylocallyApp;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;


import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<CartResponse.Cartdetail> item_list;
    private BilldetailsInfoListener mBilldetailsInfoListener;

    public BillListAdapter(List<CartResponse.Cartdetail> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemCartBillBinding blogViewBinding = ListItemCartBillBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveProductsViewHolder(blogViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {

        return item_list.size();
    }


    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<CartResponse.Cartdetail> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(BilldetailsInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface BilldetailsInfoListener {

        void infoClick(CartResponse.Cartdetail cartdetail, ImageView imageView, ToolTipRelativeLayout relativeLayout);

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements BillItemViewModel.BilldetailsInfoViewModelListener {

        ListItemCartBillBinding mListItemLiveProductsBinding;
        BillItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCartBillBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CartResponse.Cartdetail blog = item_list.get(position);
            mLiveProductsItemViewModel = new BillItemViewModel(blog,this);
            mListItemLiveProductsBinding.setBillItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();


            if (blog.getColorCode()!=null) {
                int color = Color.parseColor(blog.getColorCode());
                mListItemLiveProductsBinding.name.setTextColor(color);
            }else {
                mListItemLiveProductsBinding.name.setTextColor(DailylocallyApp.getInstance().getResources().getColor(R.color.dark_gray));
            }

/*
            ToolTip toolTip = new ToolTip()
                    .withContentView(LayoutInflater.from(MvvmApp.getInstance()).inflate(R.layout.tool_tip_address, null))
                    // .withText("Now delivering to "+mHomeTabViewModel.getDataManager().getCurrentAddress())
                    .withColor(MvvmApp.getInstance().getResources().getColor(R.color.tracking_back))
                    .withShadow()
                    .withTextColor(Color.BLACK)
                    .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW);
            ToolTipView myToolTipView = mListItemLiveProductsBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, mListItemLiveProductsBinding.info);

            TextView title = myToolTipView.findViewById(R.id.activity_main_redtv);

      //      String aTitle = mHomeTabViewModel.getDataManager().getCurrentAddressArea() == null ? "your location" : mHomeTabViewModel.getDataManager().getCurrentAddressArea();


            String sTitle = "Now showing kitchens around \nClick to change location!";

            title.setText(sTitle);*/

          /*  myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
                @Override
                public void onToolTipViewClicked(ToolTipView toolTipView) {

                    // selectAddress();
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (myToolTipView != null) {

                        myToolTipView.remove();
                        myToolTipView = null;
                        mHomeTabViewModel.getDataManager().appStartedAgain(false);
                    }
                }
            }, 10000);*/

        }

        @Override
        public void onItemClick(CartResponse.Cartdetail cartdetail) {
            mBilldetailsInfoListener.infoClick(cartdetail, mListItemLiveProductsBinding.info,mListItemLiveProductsBinding.activityMainTooltipframelayout);
        }
    }
}
