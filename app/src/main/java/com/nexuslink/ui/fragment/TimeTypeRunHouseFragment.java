package com.nexuslink.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/2/7.
 */

public class TimeTypeRunHouseFragment extends Fragment {

    @BindView(R.id.time_wheel_hour)
    WheelView mWheelHour;
    @BindView(R.id.time_wheel_minute)
    WheelView mWheelMinute;
    //==============================================数据
    private List<String> housrs = new ArrayList<>();
    private List<String> minutes = new ArrayList<>();
    private int hour = 0;
    private int minute = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            housrs.add(i + "");
        }
        for(int i =0;i<60;i++){
            minutes.add(i+"");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_type_fragment, container, false);
        ButterKnife.bind(this, view);
        setUpView();
        return view;
    }

    private void setUpView() {
//        mTimeWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
//        mTimeWheelView.setSkin(WheelView.Skin.Holo);
//        mTimeWheelView.setWheelData(list);

        WheelView.WheelViewStyle style  = new WheelView.WheelViewStyle();
        style.backgroundColor = getResources().getColor(R.color.white_light);
        style.textColor = getResources().getColor(R.color.tab_text_color);
        style.selectedTextColor = getResources().getColor(R.color.tab_text_sel);
        style.holoBorderColor = getResources().getColor(R.color.ufo_green);
        style.selectedTextZoom = 1.5f;

        mWheelHour.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        mWheelHour.setSkin(WheelView.Skin.Holo);
        mWheelHour.setWheelData(housrs);
        mWheelHour.setStyle(style);
        mWheelHour.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                hour = Integer.parseInt((String) o);
            }
        });

        mWheelMinute.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        mWheelMinute.setSkin(WheelView.Skin.Holo);
        mWheelMinute.setWheelData(minutes);
        mWheelMinute.setStyle(style);
        mWheelMinute.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                minute = Integer.parseInt(String.valueOf(o)) ;
            }
        });

    }
    public int getGoal(){
        return hour*60+minute;
    }

}
