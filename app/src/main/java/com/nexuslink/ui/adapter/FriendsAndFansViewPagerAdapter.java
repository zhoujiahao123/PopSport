package com.nexuslink.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/22.
 */

public class FriendsAndFansViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    String title[] = new String[]{"关注","粉丝"};

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public FriendsAndFansViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments == null?null:fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null?0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
