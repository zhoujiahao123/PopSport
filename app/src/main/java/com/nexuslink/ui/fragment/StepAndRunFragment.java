package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nexuslink.R;
import com.nexuslink.ui.activity.WeatherActivity;
import com.nexuslink.ui.adapter.StepAndRunFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/1/15.
 */

public class StepAndRunFragment extends Fragment implements View.OnClickListener {

    //===============================================常量
    private final String TAG = "StepAndRunFragment";
    //===============================================辅助变量
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    //===============================================view
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    //===============================================Fragments的相关设置
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        appCompatActivity = (AppCompatActivity) activity;
        fragments.clear();
        Fragment stepFragment = new StepFragment();
        Fragment runFragment = new RunFragment();
        fragments.add(stepFragment);
        fragments.add(runFragment);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_and_run_fragment,container,false);
        initView(view);
        initViewPagerAndTab();
        setHasOptionsMenu(true);
        return view;
    }

    private void initViewPagerAndTab() {
        StepAndRunFragmentAdapter adapter = new StepAndRunFragmentAdapter(getFragmentManager(),fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Log.i(TAG,fragments.size()+"");
    }

    private void initView(View v) {
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar_step_and_run);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager_step_and_run);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabLayout_step_and_run);
        appCompatActivity.setSupportActionBar(mToolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.weather);
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.step_fragment_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.step_menu_setting){
            Toast.makeText(activity, "菜单设置", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), WeatherActivity.class);
        startActivity(intent);
    }
}
