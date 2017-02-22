package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.model.taskmodel.OnChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/2/18.
 */
public class TaskActivity extends SwipeBackActivity implements OnChangedListener {
    @BindView(R.id.task_detail_daily_step)
    TextView taskDetailDailyStep;
    @BindView(R.id.task_times_daily_step)
    TextView taskTimesDailyStep;
    @BindView(R.id.task_detail_daily_run)
    TextView taskDetailDailyRun;
    @BindView(R.id.task_times_daily)
    TextView taskTimesDaily;
    @BindView(R.id.task_detail_addition_run)
    TextView taskDetailAdditionRun;
    @BindView(R.id.task_times_addition)
    TextView taskTimesAddition;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_finish_task_step)
    Button btnFinishTaskStep;
    @BindView(R.id.btn_finish_task_run)
    Button btnFinishTaskRun;
    @BindView(R.id.btn_finish_task)
    Button btnFinishTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.turn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        StepsDao stepsDao = BaseApplication.getDaosession().getStepsDao();
        Steps stepNum = stepsDao.queryBuilder().list().get(0);
        int step = stepNum.getUStep();
        onStepChanged(step);
    }

    @OnClick({R.id.btn_finish_task_step, R.id.btn_finish_task_run, R.id.btn_finish_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish_task_step:
                break;
            case R.id.btn_finish_task_run:
                break;
            case R.id.btn_finish_task:
                break;
        }
    }

    @Override
    public void onStepChanged(int stepNum) {
        if (stepNum >= 4000) {
            btnFinishTaskStep.setVisibility(View.VISIBLE);
            taskDetailDailyStep.setText("任务详情: "+"已完成");
        }else {
            taskDetailDailyStep.setText("任务详情: " + stepNum + "/4000步");
        }
    }

    @Override
    public void onRunChanged(int mileNum) {
        taskDetailDailyRun.setText("任务详情: " + mileNum + "/2000米");
    }

}
