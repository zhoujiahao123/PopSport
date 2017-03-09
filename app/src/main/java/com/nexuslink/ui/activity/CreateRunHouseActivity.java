package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nexuslink.HasJoinedRooms;
import com.nexuslink.HasJoinedRoomsDao;
import com.nexuslink.R;
import com.nexuslink.presenter.runhousepresenter.CreateRunHousePresenter;
import com.nexuslink.presenter.runhousepresenter.CreateRunHousePresenterImpl;
import com.nexuslink.ui.adapter.CreateRunHouseViewPagerAdapter;
import com.nexuslink.ui.fragment.RoadTypeRunHouseFragment;
import com.nexuslink.ui.fragment.TimeTypeRunHouseFragment;
import com.nexuslink.ui.view.CreateRunHouseView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRunHouseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnDateSetListener, CreateRunHouseView {

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
    @BindView(R.id.progress_community)
    LoadingView progress;
    @BindView(R.id.room_name_input)
    EditText roomNameInput;
    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.activity_create_run_house)
    LinearLayout activityCreateRunHouse;
    //===============================================view

    private TimePickerDialog timePickerDialog;


    //===============================================变量
    private SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");
    private List<Fragment> fragments = new ArrayList<>();
    private CreateRunHouseViewPagerAdapter adapter;
    private long time;
    /**
     * 格式控制
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * presenter
     */
    private CreateRunHousePresenter presenter;
    //===============================================常量
    /***
     * 三分钟的毫秒数
     */
    private static final long THREE_MINUTES = /*1000 * 60 * 3*/ 0;
    private static final String TAG = "CreateRunHouseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_run_house);
        ButterKnife.bind(this);
        presenter = new CreateRunHousePresenterImpl(this);
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
        backIcon.setOnClickListener(new View.OnClickListener() {
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
                .setMinMillseconds(System.currentTimeMillis() + THREE_MINUTES)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.ufo_green))
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.three_class_text))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.one_class_text))
                .setWheelItemTextSize(16)
                .build();
        //设置默认时间
        mStartDateShow.setText(df.format(System.currentTimeMillis() + THREE_MINUTES));
        time = System.currentTimeMillis()+THREE_MINUTES;
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
        time = millseconds;
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
                timePickerDialog.show(getSupportFragmentManager(), null);
                break;
            case R.id.post:
                presenter.createRunHouse();
                break;
        }
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast(this, "创建成功");
        EventBus.getDefault().post("刷新跑房");
    }

    @Override
    public void showError() {
        ToastUtil.showToast(this, "创建失败");
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public int getType() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public int getGoal() {
        int goal;
        if (mViewPager.getCurrentItem() == 0) {
            TimeTypeRunHouseFragment fragment = (TimeTypeRunHouseFragment) fragments.get(0);
            goal = fragment.getGoal();
        } else {
            RoadTypeRunHouseFragment fragment = (RoadTypeRunHouseFragment) fragments.get(1);
            goal = fragment.getGoal();
        }
        return goal;
    }

    @Override
    public String getRoomName() {
        return roomNameInput.getText().toString();
    }

    @Override
    public String getStartTime() {
        return sdf.format(time);
    }

    @Override
    public void insertOneRoom() {
        final HasJoinedRoomsDao joinedRoomsDao = DBUtil.getHasJoinedRoomsDap();
        HasJoinedRooms room = new HasJoinedRooms(null,roomNameInput.getText().toString(),1,sdf.format(time),getGoal(),getType());
        joinedRoomsDao.insert(room);
        onBackPressed();
    }
}
