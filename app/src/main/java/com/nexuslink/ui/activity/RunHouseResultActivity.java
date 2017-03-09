package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.ui.adapter.RoomResultRecylerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunHouseResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mTabCollaps;
    @BindView(R.id.room_result_recylerview)
    RecyclerView mRecylcerView;
    //===============================================数据
    private List<RoomGoal.GoalsBean> goalsList;
    private RoomResultRecylerAdapter adapter;
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
        type = getIntent().getIntExtra("type",-1);
        adapter = new RoomResultRecylerAdapter(this,goalsList,type);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        mTabCollaps.setTitle("跑房结果");

        //recylcerview
        mRecylcerView.setAdapter(adapter);
        mRecylcerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecylcerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
