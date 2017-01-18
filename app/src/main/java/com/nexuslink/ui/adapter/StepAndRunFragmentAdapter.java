package com.nexuslink.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by 猿人 on 2017/1/15.
 */

public class StepAndRunFragmentAdapter extends FragmentPagerAdapter {

    //===============================================常量
    private static final String TAG = "FragmentAdapter";
    //===============================================Fragment
    private List<Fragment> fragments;
    private String title[] = {"计步","跑步"};
    public StepAndRunFragmentAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG,"getItem:"+position);
        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i(TAG,"position:"+position);
        return title[position];
    }
}
