package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrules.charter.CharterLine;
import com.nexuslink.R;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.config.Constants;
import com.nexuslink.service.StepService;
import com.nexuslink.ui.view.ViewColor;
import com.nexuslink.util.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class StepFragment extends Fragment {
    //===============================================常量
    private static final String TAG = "StepFragment";
    @BindView(R.id.current_steps_tv)
    TextView mCurrentStepsTv;
    @BindView(R.id.history_average_steps)
    TextView mHistoryAverageSteps;
    @BindView(R.id.history_best_steps)
    TextView mHistoryBestSteps;
    @BindView(R.id.charter_steps)
    CharterLine charterSteps;
    //===============================================辅助变量
    private boolean isBind = false;
    private Activity activity;
    private int historyBestStep = 0;
    private int historyAverageStep = 0;
    private float[] values = new float[7];
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    //===============================================Messenger
    private Messenger messengerService;
    private Messenger messengerClient = new Messenger(new MessengerHandler());

    /**
     * Toolbar左侧点击事件
     *
     * @param
     */


    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.MSG_FROM_SERVICE:
                    // currentStepsTv.setText(msg.getData().getInt("currentSteps")+"");
                    mCurrentStepsTv.setText(msg.getData().getInt("currentSteps")+"");
                    break;
                default:
                    break;
            }
        }
    }

    //===============================================数据库操作
    private StepsDao stepsDao = DBUtil.getStepsDao();
    //===============================================serviceConn
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
            Message msg = Message.obtain(null, Constants.MSG_FROM_CLIENT);
            msg.replyTo = messengerClient;
            try {
                messengerService.send(msg);
                Log.i(TAG, "向service发送请求，进行初始化view");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "断开连接");
            isBind = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        setupService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment, container, false);
        ViewColor.setColor(activity,activity.getResources().getColor(R.color.pink_dark_top));
        ButterKnife.bind(this, view);
        initDate();
        return view;
    }

    private void initDate() {
        QueryBuilder qb = stepsDao.queryBuilder();
        qb.where(StepsDao.Properties.Date.eq(getTodayDate()));
        Steps steps = (Steps) qb.unique();
        if (steps != null) {
            mCurrentStepsTv.setText(steps.getUStep()+"");
        } else {
            mCurrentStepsTv.setText(0+"");
        }
        //计算所需数据
        caclulate();
        //历史平均显示
        setHistoryAverageTv();
        //设置历史最佳显示
        setHistoryBeset();
        //初始化柱状图
        initCharter();
    }

    /**
     * 初始化列表
     */
    private void initCharter() {


        //日期回滚七天
        calendar.roll(Calendar.DATE,-8);
        Date date = new Date();
        //找到数据 区间在一个星期之内
        List<Steps> list = stepsDao.queryBuilder()
                .where(StepsDao.Properties.Date.lt(sdf.format(date)),StepsDao.Properties.Date.ge(sdf.format(calendar.getTime())))
                .list();
        int index = 0;
        if(list != null && list.size() > 0){
            for(int i=0;i<7;i++){
                String str = sdf.format(calendar.getTime());
                if(str.equals(list.get(i).getDate())){
                    //满足条件就进行复制
                    values[index] =(float) list.get(i).getUStep();
                    index++;
                }else{
                    //不满足条件就进行插入
                    Steps steps = new Steps(null,0,str);
                    stepsDao.insert(steps);
                    list.add(i,steps);
                }
                calendar.set(Calendar.DATE,i-8+1);
            }
        }else{
            //如果还没有数据，那么就进行0赋值
            for(int i =0;i<7;i++){
                values[i] = 0.0f;
            }
        }

        //对表格进行初始化
        charterSteps.setValues(values);
        charterSteps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        charterSteps.show();
    }

    private void caclulate() {
        //加载昨天及昨天以前的数据
        List<Steps> list = stepsDao.queryBuilder().where(StepsDao.Properties.Date.lt(sdf.format(calendar.getTime()))).list();
        if (list != null &&list.size() > 0){
            for(int i =0;i<list.size();i++){
                if(list.get(i).getUStep() > historyBestStep){
                    historyBestStep = list.get(i).getUStep();
                }
                historyAverageStep += list.get(i).getUStep();
            }
            historyAverageStep/=list.size();
        }
    }

    private void setHistoryBeset() {
        mHistoryBestSteps.setText(historyBestStep+"");
    }

    private void setHistoryAverageTv() {
        mHistoryAverageSteps.setText(historyAverageStep+"");
    }

    /**
     * 取得今日日期
     *
     * @return
     */
    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void setupService() {
        if (!isBind) {
            Intent intent = new Intent(activity, StepService.class);
            activity.startService(intent);
            isBind = getContext().getApplicationContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);
            Log.i(TAG, "isBind:" + isBind);
            Log.i(TAG, "绑定服务成功");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            getContext().getApplicationContext().unbindService(conn);
        }
    }
}
