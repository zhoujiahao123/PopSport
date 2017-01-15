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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.nexuslink.R;
import com.nexuslink.StepsDao;
import com.nexuslink.config.Constants;
import com.nexuslink.service.StepService;
import com.nexuslink.util.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class StepFragment extends Fragment {
    //===============================================常量
    private static final String TAG = "StepFragment";
    //===============================================辅助变量
    private boolean isBind = false;
    private Activity activity;
    //===============================================view
    private MagicProgressCircle stepCircle;
    private TextView currentStepsTv,expectStepsTv;
    //===============================================Messenger
    private Messenger messengerService;
    private Messenger messengerClient = new Messenger(new MessengerHandler());
    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.MSG_FROMSERVICE:
                    currentStepsTv.setText(msg.getData().getInt("currentSteps"));
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
            isBind = true;
            messengerService = new Messenger(service);
            Message msg = Message.obtain(null,Constants.MSG_FROMCLIENT);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        setupService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment,container,false);
        //初始化view
        initView(view);
        //初始化view的数据，这里是计步显示
//        initData();
        return view;
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

//    private void initData() {
//        //从数据库中取得数据
//        QueryBuilder qb = stepsDao.queryBuilder();
//        qb.where(StepsDao.Properties.CurrentDate.eq(getTodayDate()));
//        Steps steps = (Steps) qb.unique();
//        if(steps!=null){
//            currentStepsTv.setText(steps.getSteps()+"");
//        }else{
//            currentStepsTv.setText(0+"");
//        }
//    }
    private String getTodayDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
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
