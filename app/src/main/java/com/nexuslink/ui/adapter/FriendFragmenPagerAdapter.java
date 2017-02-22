package com.nexuslink.ui.adapter;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.nexuslink.config.Constants;
import com.nexuslink.ui.fragment.AllUserFragment;
import com.nexuslink.ui.fragment.MyFriendFragment;

/**
 * Created by ASUS-NB on 2017/1/16.
 */

public class FriendFragmenPagerAdapter extends FragmentPagerAdapter {
    private static final String[] mTitle = {"结果","好友"};
    public FriendFragmenPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(position==0){
            Log.e(Constants.TAG,"position=0");
            return AllUserFragment.getInstance();
        }
        Log.e(Constants.TAG,"position=1");
        return MyFriendFragment.getInstance();
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
