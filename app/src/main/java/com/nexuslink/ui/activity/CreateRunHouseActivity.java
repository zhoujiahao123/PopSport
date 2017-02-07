package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.CreateRunHouseViewPagerAdapter;
import com.nexuslink.ui.fragment.RoadTypeRunHouseFragment;
import com.nexuslink.ui.fragment.TimeTypeRunHouseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRunHouseActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //===============================================view

    @BindView(R.id.viewpager_create_run_house)
    ViewPager mViewPager;
    @BindView(R.id.post)
    Button mPostBt;
    @BindView(R.id.toolbar_create_run_house)
    Toolbar mToolbar;
    private TextView timeBt, roadBt;
    //===============================================数据
    private List<Fragment> fragments = new ArrayList<>();
    private CreateRunHouseViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_run_house);
        ButterKnife.bind(this);
        setUpView();
    }

    private void setUpView() {
        Fragment timeTypeFragment = new TimeTypeRunHouseFragment();
        Fragment roadTypeFragment = new RoadTypeRunHouseFragment();
        fragments.add(timeTypeFragment);
        fragments.add(roadTypeFragment);
        adapter = new CreateRunHouseViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);

        timeBt = (TextView) findViewById(R.id.bt_time_type);
        roadBt = (TextView) findViewById(R.id.bt_road_type);
        timeBt.setOnClickListener(this);
        roadBt.setOnClickListener(this);
        //设置默认页
        changToTime();
        //设置toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @OnClick(R.id.post)
    public void onClick() {
    }


    private void changPage(int i) {
        if (i == 0) {
            changToTime();
        } else {
            changToRoad();
        }
    }

    private void changToTime() {
        timeBt.setSelected(true);
        roadBt.setSelected(false);
        timeBt.setClickable(false);
        roadBt.setClickable(true);
        timeBt.setTextColor(getResources().getColor(R.color.ghost_white));
        roadBt.setTextColor(getResources().getColor(R.color.tab_text_color));
        mViewPager.setCurrentItem(0);
    }

    private void changToRoad() {
        roadBt.setSelected(true);
        timeBt.setSelected(false);
        roadBt.setClickable(false);
        timeBt.setClickable(true);
        roadBt.setTextColor(getResources().getColor(R.color.ghost_white));
        timeBt.setTextColor(getResources().getColor(R.color.tab_text_color));
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_time_type:
                changToTime();
                break;
            case R.id.bt_road_type:
                changToRoad();
                break;
        }
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        changPage(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
