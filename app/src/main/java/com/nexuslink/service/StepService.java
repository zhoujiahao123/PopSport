package com.nexuslink.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nexuslink.Steps;
import com.nexuslink.StepsDao;
import com.nexuslink.config.Constants;
import com.nexuslink.util.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.dao.query.QueryBuilder;

import static android.hardware.SensorManager.SENSOR_DELAY_FASTEST;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class StepService extends Service implements SensorEventListener {

    //TAG
    private static String TAG = "StepService";
    //存储间隔
    private static int duration = 3000;
    //当前日期
    private static String CURRENT_DATE = "";
    //传感器
    private SensorManager sensorManager;

    //广播--监听手机状态变化
    private BroadcastReceiver mReceiver;
    //===============================================倒计时
    private TimeCount time;
    //===============================================数据库操作
    private StepsDao stepsDao = DBUtil.getStepsDap();
    //当前步数
    private int CURRENT_STEPS;
    //计步传感器类型 0-counter 1-detector
    private static  int stepSensor = -1;
    //是否记录
    private boolean hasRecord = false;
    //已经走过的步数
    private int hasStepCount = 0;
    //以前走过的步数
    private int previousStepCount = 0;
    //===============================================messenger
    //跨进程通信--使用Messenger方式
    private Messenger messengerFromService = new Messenger(new MessengerHandler());
    private  Messenger messengerFromClient;

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.MSG_FROM_CLIENT:
                    Log.i(TAG, String.valueOf(msg.replyTo==null));
                    messengerFromClient = msg.replyTo;
                    Message message = Message.obtain(null,Constants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putInt("currentSteps",CURRENT_STEPS);
                    message.setData(bundle);
                    try {
                        messengerFromService.send(message);
                    } catch (RemoteException e) {
                        Log.i(TAG,"remoteException");
                        e.printStackTrace();

                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化广播
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取传感器类型
                startStepDetector();
            }
        }).start();
        startTimeCount();
        initTodayData();
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

    /**
     * 初始化当天的步数
     */
    private void initTodayData() {
        CURRENT_DATE = getTodayDate();
        //===============================================在这里进行数据新的一列的存储
        //通过日期匹配，当数据中有今日步数的行，那么将步数值进行读取，如果没有那么久新增一行，并将CURRENT_STEP存储进去
        QueryBuilder qb = stepsDao.queryBuilder();
        qb.where(StepsDao.Properties.CurrentDate.eq(getTodayDate()));
        Steps steps = (Steps) qb.unique();
        if(steps!=null){
            CURRENT_STEPS = steps.getSteps();
        }else{
            //增加一行
            Steps stepsAdd = new Steps();
            stepsAdd.setCurrentDate(CURRENT_DATE);
            stepsAdd.setSteps(0);
            stepsDao.insert(stepsAdd);
        }
    }

    /**
     * 获取传感器实例
     */
    private void startStepDetector() {
        if(sensorManager!=null){
            sensorManager = null;
        }
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if(VERSION_CODES>=19){
            addCountStepListener();
        }else{
            //===============================================另一套方案
        }
    }
    private void addCountStepListener() {
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(countSensor!=null){
            //选择计步传感器
            stepSensor = 0;
            Log.i(TAG,"计步传感器");
            sensorManager.registerListener(StepService.this,countSensor,SENSOR_DELAY_NORMAL);
        }else if(detectorSensor!=null){
            //步数检测器
            stepSensor = 1;
            Log.i(TAG,"步数监测器");
            sensorManager.registerListener(StepService.this,detectorSensor,SENSOR_DELAY_FASTEST);
        }

    }

    /**
     * 传感器回调
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(stepSensor == 0){
            int tempStep = (int) event.values[0];
            if(!hasRecord){
                hasRecord = true;
                hasStepCount = tempStep;
            }else{
                int thisStepCount = tempStep -hasStepCount;
                CURRENT_STEPS+=(thisStepCount-previousStepCount);
                previousStepCount = thisStepCount;
                Log.i(TAG,"tempStepCount:"+tempStep +"hasStepCount:"+hasStepCount+" privousStepCount:"+previousStepCount
                        +" CURRENT_STEP:"+ CURRENT_STEPS);
            }
//            Toast.makeText(BaseApplication.mContext, tempStep, Toast.LENGTH_SHORT).show();
        }else if(stepSensor == 1){
            if(event.values[0] == 1.0){
                CURRENT_STEPS++;
                Log.i(TAG,CURRENT_STEPS+"");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    /**
     * 注册广播
     */
    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //监听日期变化
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(Intent.ACTION_SCREEN_ON.equals(action)){
                    Log.i(TAG,"ScreenOn");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.i(TAG,"ScreenOff");
                    //60s
                    duration = 6000;
                }else if (Intent.ACTION_USER_PRESENT.equals(action)){
                    Log.i(TAG,"screen unlock");
                    save();
                    //30s
                    duration = 3000;
                }else if(Intent.ACTION_SHUTDOWN.equals(action)){
                    Log.i(TAG,"shutdown");
                    save();
                }else if(Intent.ACTION_DATE_CHANGED.equals(action)){
                    save();
                    isNewDay();
                }else if(Intent.ACTION_TIME_CHANGED.equals(action)){
                    save();
                    isNewDay();
                }else if(Intent.ACTION_TIME_TICK.equals(action)){
                    save();
//                    isNewDay();
                }
            }
        };
        registerReceiver(mReceiver,filter);
    }

    /**
     * 0点时初始化数据
     */
    private void isNewDay() {
        String time = "00:00";
        if(time.equals(new SimpleDateFormat("HH:mm").format(new Date()))||(!CURRENT_DATE.equals(getTodayDate()))){
            initTodayData();
        }
    }

    /**
     * 存储到数据中去
     */
    private void save() {
        int tempStep = CURRENT_STEPS;
        QueryBuilder qb = stepsDao.queryBuilder();
        qb.where(StepsDao.Properties.CurrentDate.eq(getTodayDate()));
        Steps steps = (Steps) qb.unique();
        //不为空时，说明还未到12点，我们进行更新就行，为空说明为最后一次存储
        if(steps!=null){
            steps.setSteps(tempStep);
            steps.setCurrentDate(CURRENT_DATE);
            stepsDao.update(steps);
        }else{
            steps = new Steps();
            steps.setSteps(tempStep);
            steps.setCurrentDate(CURRENT_DATE);
            stepsDao.update(steps);
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"返回IBinder");
        return messengerFromService.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消前台进程
        stopForeground(true);
        unregisterReceiver(mReceiver);
    }


    /**
     * 开始计时
     */
    private void startTimeCount() {
        time = new TimeCount(duration,1000);
        time.start();
    }
    private class TimeCount extends CountDownTimer{


        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            Message msg = Message.obtain(null,Constants.MSG_FROM_SERVICE);
            Bundle bundle = new Bundle();
            bundle.putInt("currentSteps",CURRENT_STEPS);
            msg.setData(bundle);
            try {
                if(hasRecord&&messengerFromClient!=null){
                    messengerFromClient.send(msg);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
//            Log.i(TAG,"currentSteps:"+CURRENT_STEPS);
        }

        @Override
        public void onFinish() {
            //如果计时器正常结束，则开始计步
            time.cancel();
            save();
            startTimeCount();
        }
    }




}
