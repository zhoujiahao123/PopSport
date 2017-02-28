package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.HistoryViewPagerAdapter;
import com.nexuslink.ui.fragment.RunHistroyFragment;
import com.nexuslink.ui.fragment.StepHistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepAndRunHistoryActivity extends AppCompatActivity {

    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_history)
    ViewPager mViewPagerHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_and_run_history);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //设置toolbar和viewpager联动
        mTabLayout.setupWithViewPager(mViewPagerHistory);
        //viewpager初始化
        Fragment stepHistory = new StepHistoryFragment();
        Fragment runHistory = new RunHistroyFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(stepHistory);
        list.add(runHistory);

        HistoryViewPagerAdapter adapter = new HistoryViewPagerAdapter(getSupportFragmentManager(),list);
        mViewPagerHistory.setAdapter(adapter);
    }

    @OnClick(R.id.back_icon)
    public void onClick() {
        onBackPressed();
    }
}
