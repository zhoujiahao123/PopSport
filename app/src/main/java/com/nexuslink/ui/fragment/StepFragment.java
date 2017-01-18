package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.nexuslink.R;
import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.config.Constants;
import com.nexuslink.service.StepService;
import com.nexuslink.util.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class StepFragment extends Fragment implements View.OnClickListener {
    //===============================================常量
    private static final String TAG = "StepFragment";
    //===============================================辅助变量
    private boolean isBind = false;
    private Activity activity;
    //===============================================view
    private MagicProgressCircle stepCircle;
    private TextView currentStepsTv,expectStepsTv;
    private Toolbar mStepToolbar;
    //===============================================Messenger
    private Messenger messengerService;
    private Messenger messengerClient = new Messenger(new MessengerHandler());

    /**
     * Toolbar左侧点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(activity, "天气查询", Toast.LENGTH_SHORT).show();
    }

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.MSG_FROM_SERVICE:
                    currentStepsTv.setText(msg.getData().getInt("currentSteps")+"");
                    break;
                default:
                    break;
            }
        }
    }
    //===============================================数据库操作
    private StepsDao stepsDao = DBUtil.getStepsDap();
    //===============================================serviceConn
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
            Message msg = Message.obtain(null,Constants.MSG_FROM_CLIENT);
            msg.replyTo = messengerClient;
            try {
                messengerService.send(msg);
                Log.i(TAG,"向service发送请求，进行初始化view");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"断开连接");
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
        View view = inflater.inflate(R.layout.step_fragment,container,false);
        //初始化view
        initView(view);
        initDate();
        return view;
    }

    private void initDate() {
        QueryBuilder qb = stepsDao.queryBuilder();
        qb.where(StepsDao.Properties.Date.eq(getTodayDate()));
        Steps steps = (Steps) qb.unique();
        if(steps!=null){
            currentStepsTv.setText(steps.getUStep()+"");
        }else{
            currentStepsTv.setText(0+"");
        }
    }

    /**
     * 取得今日日期
     * @return
     */
    private String getTodayDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    private void setupService() {
        if(!isBind){
            Intent intent = new Intent(activity, StepService.class);
            activity.startService(intent);
            isBind = getContext().getApplicationContext().bindService(intent,conn, Context.BIND_AUTO_CREATE);
            Log.i(TAG,"isBind:"+isBind);
            Log.i(TAG,"绑定服务成功");
        }
    }


    private void initView(View v) {
        stepCircle = (MagicProgressCircle) v.findViewById(R.id.stepCircle);
        currentStepsTv = (TextView) v.findViewById(R.id.tv_currentSteps);
        expectStepsTv = (TextView) v.findViewById(R.id.tv_expectSteps);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isBind){
            activity.unbindService(conn);
        }
    }
}
