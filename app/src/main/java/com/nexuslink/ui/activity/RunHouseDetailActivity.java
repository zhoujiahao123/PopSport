package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.presenter.runhousepresenter.RunHouseDetailPresenter;
import com.nexuslink.presenter.runhousepresenter.RunHouseDetailPresenterImpl;
import com.nexuslink.ui.adapter.RunHouseDetailAdapter;
import com.nexuslink.ui.view.RunHouseDetailView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RunHouseDetailActivity extends AppCompatActivity implements RunHouseDetailView {


    //===============================================view
    @BindView(R.id.toolbar_run_house_detail)
    Toolbar mToolbar;
    @BindView(R.id.tv_run_house_expect)
    TextView mRunHouseExpectTv;
    @BindView(R.id.tv_run_house_start_time)
    TextView mRunHouseStartTimeTv;
    @BindView(R.id.bt_join_in)
    Button mJoinInBt;
    @BindView(R.id.run_house_name)
    TextView runHouseName;
    @BindView(R.id.run_house_personnum)
    TextView mPersonNum;
    @BindView(R.id.recycler_run_house_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_community)
    LoadingView progress;
    @BindView(R.id.activity_run_house_detail)
    LinearLayout activityRunHouseDetail;
    //===============================================变量
    private LoadRoomsResult.RoomBean roomBean;
    private RunHouseDetailAdapter adapter;
    /**
     * presenter
     */
    private RunHouseDetailPresenter presenter;
    /**
     * 格式控制
     */
    private SimpleDateFormat dfDate = new SimpleDateFormat("MM月dd日");
    private SimpleDateFormat dfDay = new SimpleDateFormat("HH:mm");
    //===============================================常量
    private static final String TAG = "RunHouseDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_house_detail);
        ButterKnife.bind(this);
        presenter = new RunHouseDetailPresenterImpl(this);
        initDatas();
        initViews();
    }


    private void initDatas() {
        roomBean = getIntent().getParcelableExtra("roominfo");
    }

    private void initViews() {
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
        //reyclerview设置
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //进行界面的设置
        if (roomBean != null) {
            //toolbar上信息设置
            runHouseName.setText(roomBean.getRoomName());
            mPersonNum.setText(roomBean.getUsers().size()+"人");
            //跑房目标框设置

            String str = roomBean.getRoomType() == 1 ? "米" : "分钟";
            mRunHouseExpectTv.setText(roomBean.getRoomGoal() + str);
            //由于还在测试，这部分等后台完成在继续
//             mRunHouseStartTimeTv.setText(dfDate.format(roomBean.getStartDate())+" "+dfDay.format(roomBean.getStartTime()));
            //users设置
            adapter = new RunHouseDetailAdapter(this, roomBean.getUsers());
            mRecyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.bt_join_in)
    public void onClick() {
        presenter.joinRoom(roomBean.getRoomId());
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
    public void showSuccess() {
        ToastUtil.showToast(this,"加入成功");
    }

    @Override
    public void showError() {
        ToastUtil.showToast(this,"加入失败");
    }

    @Override
    public void setDatas(List<LoadRoomsResult.RoomBean.UsersBean> users) {
        adapter.setDatas(users);
    }
}
