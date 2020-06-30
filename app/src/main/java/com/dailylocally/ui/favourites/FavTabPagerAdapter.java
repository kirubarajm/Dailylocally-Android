package com.dailylocally.ui.favourites;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailylocally.ui.favourites.products.FavProductFragment;

public class FavTabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FavResponse response;

    public FavTabPagerAdapter(FragmentManager fm, int NumOfTabs, FavResponse response) {
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
            return FavProductFragment.newInstance("0");
        }else {
            return FavProductFragment.newInstance(String.valueOf(response.getResult().get(position-1).getCatid()));
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}