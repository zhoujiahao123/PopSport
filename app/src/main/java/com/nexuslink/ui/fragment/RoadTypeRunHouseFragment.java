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

public class RoadTypeRunHouseFragment extends Fragment {

    //===============================================view
    @BindView(R.id.road_wheel_1)
    WheelView mWheel1;
    @BindView(R.id.road_wheel_2)
    WheelView mWheel2;
    //===============================================数据
    private List<String> wheel1 = new ArrayList<>();
    private List<String> wheel2 = new ArrayList<>();
    private int data1 = 0;
    private int data2 = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for(int i =0;i<30;i++){
            wheel1.add(i+"");
        }
        for(int i =0;i<=99;i++){
            wheel2.add(i+"");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.road_type_fragment, container, false);
        ButterKnife.bind(this, view);
        setUpView();
        return view;
    }

    private void setUpView() {
        WheelView.WheelViewStyle style  = new WheelView.WheelViewStyle();
        style.backgroundColor = getResources().getColor(R.color.white_light);
        style.textColor = getResources().getColor(R.color.tab_text_color);
        style.selectedTextColor = getResources().getColor(R.color.tab_text_sel);
        style.holoBorderColor = getResources().getColor(R.color.ufo_green);
        style.selectedTextZoom = 2.1f;

        mWheel1.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        mWheel1.setSkin(WheelView.Skin.Holo);
        mWheel1.setWheelData(wheel1);
        mWheel1.setStyle(style);
        mWheel1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                data1 = Integer.parseInt(String.valueOf(o));
            }
        });

        mWheel2.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        mWheel2.setSkin(WheelView.Skin.Holo);
        mWheel2.setWheelData(wheel2);
        mWheel2.setStyle(style);
        mWheel2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                data2 = Integer.parseInt(String.valueOf(o));
            }
        });
    }
    public int getGoal(){
        return data1*1000+data2*100;
    }
}
