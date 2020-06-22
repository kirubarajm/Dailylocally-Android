package com.dailylocally.ui.category.l2.products.sort;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;


public class SortItemViewModel {


    public final ObservableField<String> sortTitle = new ObservableField<>();

    public final ObservableBoolean isClicked = new ObservableBoolean();


    public final SortItemViewModelListener mListener;
    private final SortItems.Result sorts;

    String  id;

    public SortItemViewModel(SortItemViewModelListener mListener, SortItems.Result sorts) {


        this.mListener = mListener;
        this.sorts = sorts;

        id = sorts.getSortid();



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

        sortTitle.set(sorts.getSortname());
    }


    public void onItemClick() {

        /*if (isClicked.get()) {
            isClicked.set(false);
            mListener.removeFilter(id);

        } else {
            isClicked.set(true);
            mListener.addfilter(id);
        }*/
    }


    interface SortItemViewModelListener {

        void onItemClick(Integer id);

        void addfilter(Integer id);

        void removeFilter(Integer id);



    }


}
