package com.nexuslink.ui.fragment;

import android.graphics.Color;
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
import com.nexuslink.util.DBUtil;

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
    private SimpleDateFormat monthDf = new SimpleDateFormat("MM-dd");
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df1 = new DecimalFormat("#0.0");
    /**
     * 数据库操作
     */
    private RunDao runDao = DBUtil.getRunDao();
    /**
     * 日期辅助
     */
    private Calendar calendar  = Calendar.getInstance();
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
        initViews();
        return view;
    }

    private void initViews() {
        setFragmentHead();
        setChart();
    }

    /**
     * 设置表格
     */
    private void setChart() {
        //设置表格
        //日期回滚七天
        calendar.roll(Calendar.DATE, -6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        runList = null;
        //大于8天前，小于昨天
        runList = runDao.loadAll();
        //x轴数据
        List<String> xList = new ArrayList<>();
        //y轴数据
        List<BarEntry> yBarEnties = new ArrayList<>();

        if (runList != null && runList.size() > 0) {
            for (int i = 0; i < 7; i++) {
                String str = df.format(calendar.getTime());
                if (str.equals(runList.get(i).getDate().split(" ")[0])) {
                    //满足条件就进行复制
                    yBarEnties.add(new BarEntry(Float.parseFloat(runList.get(i).getUMileage()),i));
                } else {
                    //不满足条件就进行插入
                    runList.add(i, null);
                }
                calendar.roll(Calendar.DATE, 1);
                xList.add(monthDf.format(calendar.getTime()));
            }
        }
        //对表格进行初始化
        BarDataSet barDataSet = new BarDataSet(yBarEnties,"运动量");
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
        // 网格背景颜色
        mChart.setGridBackgroundColor(Color.parseColor("#00000000"));
        // 是否显示表格颜色
        mChart.setDrawGridBackground(false);
        // 设置边框颜色
        mChart.setBorderColor(Color.parseColor("#00000000"));
        // 说明颜色
        mChart.setDescriptionColor(Color.parseColor("#00000000"));
        // 图例
        mChart.getLegend().setEnabled(false);
    }

    /**
     * 设置顶部界面
     */
    private void setFragmentHead() {
        //设置今日已走
        //从数据库中取到跑步的公里数
        float currentMiles = 0.0f;
         runList = runDao.queryBuilder().where(RunDao.Properties.Date.ge(df.format(System.currentTimeMillis()))).list();
        if(runList != null && runList.size() > 0){
            for(int i = 0;i<runList.size();i++){
                currentMiles+= Float.valueOf(runList.get(i).getUMileage());
            }
        }
        mCurrentMeilsTv.setText(df1.format(currentMiles)+"");
        //设置平均
        float avergeMiles = 0.0f;
        float bestMiels  = 0.0f;
        runList = null;
        runList = runDao.queryBuilder().where(RunDao.Properties.Date.lt(df.format(System.currentTimeMillis()))).list();
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
        mHistoryAverageMiles.setText(df1.format(avergeMiles)+"");
        //设置最佳
        mHistoryBestMiles.setText(df1.format(bestMiels)+"");
    }


}
