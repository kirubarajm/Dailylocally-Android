package com.dailylocally.ui.collection.l2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailylocally.ui.collection.l2.products.CollectionProductFragment;

public class CollectionTabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    CollectionDetailsResponse response;
    String cid;

    public CollectionTabPagerAdapter(FragmentManager fm, int NumOfTabs, CollectionDetailsResponse response,String cid) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.response = response;
        this.cid=cid;
    }

    @Override
    public Fragment getItem(int position) {
/*

        for (int i=0;i<response.getResult().size();i++){
            return ProductsFragment.newInstance( response.getResult().get(i).getScl2Id());
        }
*/

        if (position == 0) {
            return CollectionProductFragment.newInstance("0",cid);
        }else {
            return CollectionProductFragment.newInstance(String.valueOf(response.getResult().get(position-1).getScl1_id()),cid);
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}