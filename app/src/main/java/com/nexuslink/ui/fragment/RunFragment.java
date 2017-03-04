package com.nexuslink.ui.fragment;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.nexuslink.R;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.ui.activity.RunViewFresh;
import com.nexuslink.util.DBUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/1/15.
 */

public class RunFragment extends Fragment {

    //===============================================view
    @BindView(R.id.current_miles_tv)
    TextView mCurrentMeilsTv;
    @BindView(R.id.history_best_miles)
    TextView mHistoryBestMiles;
    @BindView(R.id.history_average_miles)
    TextView mHistoryAverageMiles;
    @BindView(R.id.charter)
    BarChart mChart;
    //===============================================辅助变量
    /**
     * 格式控制
     */
    private SimpleDateFormat monthsdf = new SimpleDateFormat("MM-dd");
    private SimpleDateFormat yeaersdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df = new DecimalFormat("#0.0");
    /**
     * 数据库操作
     */
    private RunDao runDao = DBUtil.getRunDao();
    /**
     * 日期辅助
     */

    /**
     * 数据
     */
    List<Run> runList;
    //===============================================常量
    private static final String TAG = "RunFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_fragment, container, false);
        ButterKnife.bind(this, view);
        initViews(null);
        //注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }
    @Subscribe
    public void initViews(RunViewFresh runViewFresh) {
        setFragmentHead();
        setChart();
    }

    /**
     * 设置表格
     */

    private void setChart() {
        //设置表格
        //日期回滚七天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        runList = null;
        //x轴数据
        List<String> xList = new ArrayList<>();
        //y轴数据
        List<BarEntry> yBarEnties = new ArrayList<>();

        //加载七天的数据
        for(int i =0;i<7;i++){
            runList = runDao.queryBuilder().where(RunDao.Properties.Date.eq(yeaersdf.format(calendar.getTime()))).list();
            //如果runlist不为空，代表有改天的数据，那么就将它进行循环相加
            if(runList != null && runList.size() > 0){
                float oneDayTotalMlies = 0.0f;
                for(int j = 0;j<runList.size();j++){
                    oneDayTotalMlies+=Float.valueOf(runList.get(j).getUMileage());
                }
                //相加完成后就进行y轴数据复制
                yBarEnties.add(new BarEntry(oneDayTotalMlies,i));
            }else{
                //如果没有就手动添加一个为0的值
                yBarEnties.add(new BarEntry(0.0f,i));
            }
            //最后为x轴赋值
            xList.add(monthsdf.format(calendar.getTime()));
            //日期加1
            calendar.add(Calendar.DATE,1);
        }

        //对表格进行初始化
        BarDataSet barDataSet = new BarDataSet(yBarEnties,"运动量");
        barDataSet.setColor(getResources().getColor(R.color.yellow_500));
        BarData barData = new BarData(xList,barDataSet);
        mChart.setData(barData);
        mChart.setDescription("运动周数据");
        //设置空数据
        mChart.setNoDataTextDescription("您暂时还没有数据可以显示哦，快去运动吧");
        //===============================================属性设置
        //设置Y方向上动画animateY(int time);
        mChart.animateY(3000);
        // 设置是否可以触摸
        mChart.setTouchEnabled(false);
        //设置背景颜色
        mChart.setBackgroundColor(getResources().getColor(R.color.white));
        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
        mChart.setDrawGridBackground(false);
        // 集拉杆阴影
        mChart.setDrawBarShadow(false);
        // 是否显示表格颜色
        mChart.setDrawGridBackground(false);
        // 设置边框颜色
        mChart.setBorderColor(getResources().getColor(R.color.one_class_text_color));
        // 说明颜色
        mChart.setDescriptionColor(getResources().getColor(R.color.one_class_text_color));

    }

    /**
     * 设置顶部界面
     */

    private void setFragmentHead() {
        //设置今日已走
        //从数据库中取到跑步的公里数
        float currentMiles = 0.0f;
        //匹配当前日期
         runList = runDao.queryBuilder().where(RunDao.Properties.Date.eq(yeaersdf.format(System.currentTimeMillis()))).list();
        if(runList != null && runList.size() > 0){
            for(int i = 0;i<runList.size();i++){
                currentMiles+= Float.valueOf(runList.get(i).getUMileage());
            }
        }
        mCurrentMeilsTv.setText(df.format(currentMiles)+"");
        //设置平均
        float avergeMiles = 0.0f;
        float bestMiels  = 0.0f;
        runList = null;
        runList = runDao.queryBuilder().where(RunDao.Properties.Date.le(yeaersdf.format(System.currentTimeMillis()))).list();
        if(runList != null && runList.size() > 0){
            for(int i =0;i<runList.size();i++){
                float miles = Float.valueOf(runList.get(i).getUMileage());
                avergeMiles+= miles;
                if(bestMiels < miles){
                    bestMiels = miles;
                }
            }
            avergeMiles/=runList.size();
        }
        mHistoryAverageMiles.setText(df.format(avergeMiles)+"");
        //设置最佳
        mHistoryBestMiles.setText(df.format(bestMiels)+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
