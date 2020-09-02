package com.dailylocally.ui.category.viewall;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.ui.category.viewall.products.CatProductFragment;

public class CatProductPlansPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    L1CategoryResponse response;
    String catid;
    public CatProductPlansPagerAdapter(FragmentManager fm, int NumOfTabs, L1CategoryResponse response, String catid) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.response = response;
        this.catid = catid;
    }

    @Override
    public Fragment getItem(int position) {
/*

        for (int i=0;i<response.getResult().size();i++){
            return ProductsFragment.newInstance( response.getResult().get(i).getScl2Id());
        }
*/

        if (position == 0) {
            return CatProductFragment.newInstance(catid,"0");
        }else {
            if (response.getResult().size()>0) {
                return CatProductFragment.newInstance(String.valueOf(response.getResult().get(position - 1).getCatid()), String.valueOf(response.getResult().get(position - 1).getScl1Id()));
            }else {
                return CatProductFragment.newInstance(catid,"0");

            }
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}