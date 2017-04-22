package com.nexuslink.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.ui.BaseFragment;
import com.nexuslink.ui.adapter.AchievementRecyclerAdapter;
import com.nexuslink.util.DBUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class AchievementFragment extends BaseFragment {
    /**
     * view
     */
    @BindView(R.id.month_steps)
    TextView monthStepsTv;
    @BindView(R.id.month_miels)
    TextView monthMielsTv;
    @BindView(R.id.achieve_recyclerview)
    EasyRecyclerView mRecyclerView;

    private DecimalFormat df = new DecimalFormat("#.0");


    @Override
    public int getLayout() {
        return R.layout.achievement_fragment;
    }

    @Override
    public void initView() {
        setStepsAndMiles();
        setAchievement();
    }


    /**
     * 数据库操作
     */
    private StepsDao stepsDao = DBUtil.getStepsDao();
    private RunDao runDao = DBUtil.getRunDao();
    private UserDao userDao = DBUtil.getUserDao();

    /**
     * 设置成就
     */
    private void setAchievement() {
        AchievementRecyclerAdapter adapter = new AchievementRecyclerAdapter(getContext());
        //初始化数据
        User user = userDao.queryBuilder().unique();
        String achieveStr = user.getUAchievements();
        String achieve[] = achieveStr.split(",");
        Boolean[] achieveBool = new Boolean[achieve.length];
        for (int i = 0; i < achieve.length; i++) {
            achieveBool[i] = Boolean.parseBoolean(achieve[i]);
        }
        adapter.addAll(achieveBool);
        //设置为水平滑动
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置步数和公里数
     */
    private void setStepsAndMiles() {
        //变量声明
        int monthSteps = 0;
        float monthMiles = 0;
        //从数据库中读取本月信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date(System.currentTimeMillis()));
        String date[] = dateStr.split("-");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        //设置为本月的第一天
        calendar1.set(Integer.valueOf(date[0]), Integer.valueOf(date[1]) - 1, 1);
        //设置为下月的第一天
        //如果本月为十二月，那么就设置下一年的第一天
        if (Integer.valueOf(date[1]) == 12) {
            //下一年
            calendar2.set(Integer.valueOf(date[0]) + 1, 0, 1);
        } else {
            calendar2.set(Integer.valueOf(date[0]), Integer.parseInt(date[1]), 1);
        }
        //加载步数
        String startDate = sdf.format(calendar1.getTimeInMillis());
        String endDate = sdf.format(calendar2.getTimeInMillis());
        List<Steps> stepsList = stepsDao.queryBuilder().where(StepsDao.Properties.Date.ge(startDate), StepsDao.Properties.Date.lt(endDate)).list();
        if (stepsList != null && !stepsList.isEmpty()) {
            for (Steps steps : stepsList) {
                monthSteps += steps.getUStep();
            }
        }
        monthStepsTv.setText(monthSteps + "");
        //加载跑步公里数
        List<Run> runList = runDao.queryBuilder().where(RunDao.Properties.Date.ge(startDate), RunDao.Properties.Date.lt(endDate)).list();
        if (runList != null && !runList.isEmpty()) {
            for (Run run : runList) {
                monthMiles += Float.valueOf(run.getUMileage());
            }
        }
        monthMielsTv.setText(df.format(monthMiles));
    }


}
