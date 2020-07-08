package com.dailylocally.ui.category.l2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailylocally.ui.category.l2.products.ProductsFragment;

public class PlansPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    L2CategoryResponse response;
    String scl1id;
    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs, L2CategoryResponse response,String scl1id) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.response = response;
        this.scl1id = scl1id;
    }

    @Override
    public Fragment getItem(int position) {
/*

        for (int i=0;i<response.getResult().size();i++){
            return ProductsFragment.newInstance( response.getResult().get(i).getScl2Id());
        }
*/

        if (position == 0) {
            return ProductsFragment.newInstance("0",scl1id);
        }else {
            if (response.getResult().size()>0) {
                return ProductsFragment.newInstance(String.valueOf(response.getResult().get(position - 1).getScl2Id()), String.valueOf(response.getResult().get(position - 1).getScl1Id()));
            }else {
                return ProductsFragment.newInstance("0",scl1id);

            }
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}