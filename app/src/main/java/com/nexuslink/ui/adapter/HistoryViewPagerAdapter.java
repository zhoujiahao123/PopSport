package com.nexuslink.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/28.
 */

public class HistoryViewPagerAdapter extends FragmentPagerAdapter {
    String title[] = {"step","run"};
    private List<Fragment> fragments;
    public HistoryViewPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
