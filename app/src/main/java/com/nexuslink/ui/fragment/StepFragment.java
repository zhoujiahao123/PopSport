package com.nexuslink.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.service.StepService;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class StepFragment extends Fragment {
    //===============================================常量
    private static final String TAG = "StepFragment";
    //===============================================辅助变量
    private boolean isBind = false;
    //===============================================view
    private MagicProgressCircle stepCircle;
    private TextView currentStepsTv,expectStepsTv;
    //===============================================Messenger
    private Messenger messengerService;
    private Messenger messengerClient;
    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.MSG_FROMSERVICE:
                    currentStepsTv.setText(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    }
    //===============================================serviceConn
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
            Message msg = Message.obtain(null,Constants.MSG_FROMCLIENT);
            msg.replyTo = messengerClient;
            try {
                messengerService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment,container,false);
        //初始化view
        initView(view);
        //初始化view的数据，这里是计步显示
        initData();
        setupService();
        return view;
    }

    private void setupService() {
        Intent intent = new Intent(getActivity(), StepService.class);
        isBind = getActivity().bindService(intent,conn, Context.BIND_AUTO_CREATE);
        getActivity().startActivity(intent);
    }

    private void initData() {
        //从数据库中取得数据

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
            getActivity().unbindService(conn);
        }
    }
}
