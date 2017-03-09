package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.model.data.RoomGoal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunHouseResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mTabCollaps;

    private List<RoomGoal.GoalsBean> goalsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_house_result);
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        mTabCollaps.setTitle("跑房结果");
    }
}
