package com.dailylocally.ui.category.l2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailylocally.ui.category.l2.products.ProductsFragment;

public class PlansPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    L2CategoryResponse response;

    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs, L2CategoryResponse response) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.response = response;
    }

    @Override
    public Fragment getItem(int position) {
/*

        for (int i=0;i<response.getResult().size();i++){
            return ProductsFragment.newInstance( response.getResult().get(i).getScl2Id());
        }
*/

        if (position == 0) {
            return ProductsFragment.newInstance("0");
        }else {
            return ProductsFragment.newInstance(String.valueOf(response.getResult().get(position-1).getScl2Id()));

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}