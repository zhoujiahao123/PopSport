package com.nexuslink.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class PersonInfoViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    public PersonInfoViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setFragments(List<Fragment> fragmentList){
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
