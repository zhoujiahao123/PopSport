package com.nexuslink.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nexuslink.ui.fragment.ViewImageFragment;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/10.
 */

public class ViewImageShowAdapter extends FragmentPagerAdapter {

    private List<ViewImageFragment> fragments;

    public ViewImageShowAdapter(FragmentManager fm,List<ViewImageFragment> list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
