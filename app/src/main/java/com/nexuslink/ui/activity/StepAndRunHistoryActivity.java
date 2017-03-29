package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.HistoryViewPagerAdapter;
import com.nexuslink.ui.fragment.RunHistroyFragment;
import com.nexuslink.ui.fragment.StepHistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAndRunHistoryActivity extends AppCompatActivity {


    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_history)
    ViewPager mViewPagerHistory;
    @BindView(R.id.toolbar2)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_and_run_history);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置toolbar和viewpager联动
        mTabLayout.setupWithViewPager(mViewPagerHistory);
        //viewpager初始化
        Fragment stepHistory = new StepHistoryFragment();
        Fragment runHistory = new RunHistroyFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(stepHistory);
        list.add(runHistory);

        HistoryViewPagerAdapter adapter = new HistoryViewPagerAdapter(getSupportFragmentManager(), list);
        mViewPagerHistory.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
