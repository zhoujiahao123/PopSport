package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nexuslink.R;
import com.nexuslink.ui.adapter.CreateRunHouseViewPagerAdapter;
import com.nexuslink.ui.fragment.RoadTypeRunHouseFragment;
import com.nexuslink.ui.fragment.TimeTypeRunHouseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRunHouseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnDateSetListener {

    @BindView(R.id.toolbar_create_run_house)
    Toolbar mToolbar;
    @BindView(R.id.bt_time_type)
    TextView timeBt;
    @BindView(R.id.bt_road_type)
    TextView roadBt;
    @BindView(R.id.viewpager_create_run_house)
    ViewPager mViewPager;
    @BindView(R.id.start_date_show)
    TextView mStartDateShow;
    @BindView(R.id.start_date_pick)
    RelativeLayout mStartDatePicker;
    @BindView(R.id.post)
    Button post;
    //===============================================view

    private TimePickerDialog timePickerDialog;
    //===============================================变量
    private SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");
    private List<Fragment> fragments = new ArrayList<>();
    private CreateRunHouseViewPagerAdapter adapter;

    //===============================================常量
    private static final String TAG = "CreateRunHouseActivity";

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


        timePickerDialog = new TimePickerDialog.Builder().setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("开始时间")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.ufo_green))
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.three_class_text))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.one_class_text))
                .setWheelItemTextSize(16)
                .build();
        //设置默认时间
        mStartDateShow.setText(df.format(System.currentTimeMillis()));
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
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        changPage(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String date = df.format(millseconds);
        mStartDateShow.setText(date);
    }



    @OnClick({R.id.bt_time_type, R.id.bt_road_type, R.id.start_date_pick, R.id.post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_time_type:
                changToTime();
                break;
            case R.id.bt_road_type:
                changToRoad();
                break;
            case R.id.start_date_pick:
                timePickerDialog.show(getSupportFragmentManager(),null);
                break;
            case R.id.post:
                break;
        }
    }
}
