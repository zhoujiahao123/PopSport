package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexuslink.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RunHouseDetailActivity extends AppCompatActivity {


    //===============================================view
    @BindView(R.id.toolbar_run_house_detail)
    Toolbar mToolbar;
    @BindView(R.id.tv_run_house_expect)
    TextView mRunHouseExpectTv;
    @BindView(R.id.tv_run_house_start_time)
    TextView mRunHouseStartTimeTv;
    @BindView(R.id.recycler_run_house_detail)
    RelativeLayout mRunHouseDetailRecycler;
    @BindView(R.id.bt_join_in)
    Button mJoinInBt;
    //===============================================变量

    //===============================================常量
    private static final String TAG = "RunHouseDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_house_detail);
        ButterKnife.bind(this);

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
    }

    @OnClick(R.id.bt_join_in)
    public void onClick() {
    }
}
