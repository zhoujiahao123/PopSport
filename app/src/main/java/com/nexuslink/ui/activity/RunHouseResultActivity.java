package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.ui.adapter.RoomResultRecyclerAdapter;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RunHouseResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mTabCollaps;
    @BindView(R.id.circleImageView2)
    CircleImageView mNo1Image;
    @BindView(R.id.No1_name)
    TextView mName;
    @BindView(R.id.No1_steps)
    TextView mSteps;
    @BindView(R.id.No1_miles)
    TextView Miles;
    @BindView(R.id.No1_fans)
    TextView mFans;
    @BindView(R.id.No1_goal)
    TextView mGoal;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.other_persons)
    EasyRecyclerView mRecycler;


    //===============================================数据
    private List<RoomGoal.GoalsBean> goalsList;
    private RoomResultRecyclerAdapter adapter;
    RoomGoal.GoalsBean No1 = null;
    //===============================================变量
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_house_result);
        ButterKnife.bind(this);
        initDatas();
        initViews();
    }

    private void initDatas() {
        //获取数据
        goalsList = getIntent().getParcelableArrayListExtra("resultBeans");
        //抽取No1
        No1 = goalsList.get(0);

        //删除No1
        goalsList.remove(0);
        type = getIntent().getIntExtra("type", -1);
        if (type == 0){
            mType.setText("minute");
        }else if(type == 1) {
            mType.setText("m");
        }
        mGoal.setText(No1.getGoal()+"");

        adapter = new RoomResultRecyclerAdapter(this, type);
        adapter.addAll(goalsList);
        initNo1Data();

    }

    private void initNo1Data() {
        if (No1 != null) {
            //取得个人信息
            ApiUtil.getInstance(Constants.BASE_URL).getUserInfo(No1.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<UserInfo>() {
                        @Override
                        public void call(UserInfo userInfo) {
                            if (userInfo.getCode() == Constants.SUCCESS) {
                                Glide.with(RunHouseResultActivity.this).load(Constants.PHOTO_BASE_URL+userInfo.getUser().getUImg()).crossFade().into(mNo1Image);
                                mSteps.setText(userInfo.getUser().getUHistoryStep()+"");
                                Miles.setText(userInfo.getUser().getUHistoryMileage()+"");
                                mFans.setText(userInfo.getUser().getUFansNum()+"");
                            }
                        }
                    });
        }
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        mTabCollaps.setTitle("跑房结果");

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.circleImageView2)
    public void onViewClicked() {

    }
}
