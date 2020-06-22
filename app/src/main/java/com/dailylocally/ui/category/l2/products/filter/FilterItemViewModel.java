package com.dailylocally.ui.category.l2.products.filter;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FilterItemViewModel {


    public final ObservableField<String> filterTitle = new ObservableField<>();

    public final ObservableBoolean isClicked = new ObservableBoolean();


    public final FilterItemViewModelListener mListener;
    private final FilterItems.Result filterItems;

    String  id;

    public FilterItemViewModel(FilterItemViewModelListener mListener, FilterItems.Result filterItems) {


        this.mListener = mListener;
        this.filterItems = filterItems;

        id = filterItems.getBrand();

        FilterRequestPojo filterRequestPojo;

        /*if (mListener.getFilters() != null) {
            Gson sGson = new GsonBuilder().create();
            filterRequestPojo = sGson.fromJson(mListener.getFilters(), FilterRequestPojo.class);

            if (type == 1) {

                if (filterRequestPojo.getSortid().equals(id)){
                    isClicked.set(true);
                }

            } else if (type == 2) {

                if (filterRequestPojo.getRegionlist() != null) {
                    for (int i = 0; i < filterRequestPojo.getRegionlist().size(); i++) {


                        if (filterRequestPojo.getRegionlist().get(i).getRegion().equals(id)) {

                            isClicked.set(true);
                        }

                    }
                }

            } else if (type == 3) {

                if (filterRequestPojo.getCusinelist() != null) {
                    for (int i = 0; i < filterRequestPojo.getCusinelist().size(); i++) {

                        if (filterRequestPojo.getCusinelist().get(i).getCusine().equals(id)) {

                            isClicked.set(true);
                        }

                    }
                }
            }

        }*/

        filterTitle.set(filterItems.getBrandname());
    }


    public void onItemClick() {

        if (isClicked.get()) {
            isClicked.set(false);
            mListener.removeFilter(id);

        } else {
            isClicked.set(true);
           mListener.addfilter(id);
        }
    }


    interface FilterItemViewModelListener {



        void addfilter(String id);

        void removeFilter(String id);



    }


}
