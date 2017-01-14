package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.app.BaseFragment;
import com.nexuslink.ui.view.PersonInfoView;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class PersonInfoFragment extends BaseFragment implements PersonInfoView{


    public static PersonInfoFragment getInstance(){
        PersonInfoFragment fragment = new PersonInfoFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showFriends(String nickName, String headPath) {

    }

    @Override
    public void showRunningRoom() {

    }

    @Override
    public void showSetting() {

    }

    @Override
    public void showTask() {

    }

    @Override
    public void showAchieve() {

    }

    @Override
    public void showRank() {

    }
}
