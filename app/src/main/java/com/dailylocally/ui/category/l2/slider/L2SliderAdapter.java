package com.dailylocally.ui.category.l2.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.dailylocally.data.DataManager;
import com.dailylocally.databinding.ListItemSliderBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.category.l2.L2CategoryResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

public class L2SliderAdapter extends SliderViewAdapter<L2SliderAdapter.SliderViewHolder> {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public Integer type;
    private List<L2CategoryResponse.GetSubCatImage> item_list;
    private FiltersAdapterListener mFiltersAdapterListener;

    private DataManager dataManager;


    public L2SliderAdapter(List<L2CategoryResponse.GetSubCatImage> item_list) {
        this.item_list = item_list;
    }

    public L2SliderAdapter(List<L2CategoryResponse.GetSubCatImage> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        ListItemSliderBinding blogViewBinding1 = ListItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new SliderViewHolder(blogViewBinding1);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.onBind(position);
    }



    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<L2CategoryResponse.GetSubCatImage> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(FiltersAdapterListener listener) {
        this.mFiltersAdapterListener = listener;
    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    public interface FiltersAdapterListener {


        void itemClick();


    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder implements SliderItemViewModel.SliderItemViewModelListener {
        ListItemSliderBinding mListItemLiveProductsBinding;
        SliderItemViewModel mFilterItemViewModel;

        public SliderViewHolder(ListItemSliderBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        public SliderViewHolder(View itemView) {
            super(itemView);
        }

        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final L2CategoryResponse.GetSubCatImage blog = item_list.get(position);
            mFilterItemViewModel = new SliderItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSliderItemViewModel(mFilterItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void sliderItemClick(L2CategoryResponse.GetSubCatImage getSubCatImage) {

        }
    }

}


