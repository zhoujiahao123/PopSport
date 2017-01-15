package com.nexuslink.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nexuslink.R;
import com.nexuslink.ui.fragment.AppointmentFragment;
import com.nexuslink.ui.fragment.CommunityFragment;
import com.nexuslink.ui.fragment.PersonalInfoFragment;
import com.nexuslink.ui.fragment.StepFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.long1.spacetablayout.SpaceTabLayout;

public class MainViewActivity extends AppCompatActivity {

    //===============================================常量
    private static String TAG = "MainViewActivity";
    //===============================================view相关
    private List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.viewPager_mainView)
    ViewPager mViewPager;
    @BindView(R.id.spaceTab_mainView)
    SpaceTabLayout mSpaceTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        ButterKnife.bind(this);
        initView(savedInstanceState);


    }

    private void initView(Bundle savedInstanceState) {
        Fragment stepFragment = new StepFragment();
        Fragment appointFragment = new AppointmentFragment();
        Fragment communityFragment = new CommunityFragment();
        Fragment personalInfoFragment = new PersonalInfoFragment();

        fragments.add(stepFragment);
        fragments.add(appointFragment);
        fragments.add(communityFragment);
        fragments.add(personalInfoFragment);

        mSpaceTab.initialize(mViewPager,getSupportFragmentManager(),fragments,savedInstanceState);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mSpaceTab.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
