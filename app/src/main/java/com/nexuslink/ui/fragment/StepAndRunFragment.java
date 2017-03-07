package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nexuslink.R;
import com.nexuslink.ui.activity.RunActivity;
import com.nexuslink.ui.activity.RunHouseResultActivity;
import com.nexuslink.ui.activity.StepAndRunHistoryActivity;
import com.nexuslink.ui.activity.WeatherActivity;
import com.nexuslink.ui.adapter.StepAndRunFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 猿人 on 2017/1/15.
 */

public class StepAndRunFragment extends Fragment {

    //===============================================常量
    private final String TAG = "StepAndRunFragment";
    @BindView(R.id.test)
    Button test;
    //===============================================辅助变量
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    //===============================================view
    private ImageView weatherImage, histroyImage, startRunImage;
    private ViewPager mViewPager;

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
        View view = inflater.inflate(R.layout.step_and_run_fragment, container, false);
        initView(view);
        initViewPagerAndTab();
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initViewPagerAndTab() {
        StepAndRunFragmentAdapter adapter = new StepAndRunFragmentAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        Log.i(TAG, fragments.size() + "");
    }


    private void initView(View v) {

        mViewPager = (ViewPager) v.findViewById(R.id.viewPager_step_and_run);

        //天气界面跳转
        weatherImage = (ImageView) v.findViewById(R.id.weather_image);
        weatherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });
        //历史界面跳转
        histroyImage = (ImageView) v.findViewById(R.id.history_image);
        histroyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, StepAndRunHistoryActivity.class);
                startActivity(intent);
            }
        });
        startRunImage = (ImageView) v.findViewById(R.id.start_run_image);
        startRunImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RunActivity.class);
                startActivity(intent);
            }
        });

    }


    @OnClick(R.id.test)
    public void onClick() {
        Intent intent = new Intent(getContext(), RunHouseResultActivity.class);
        startActivity(intent);
    }
}
