package com.nexuslink.service;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.nexuslink.broadcast.AlarmReceiver;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.AlarmInfo;
import com.nexuslink.util.SharedPrefsUtil;
import com.wuxiaolong.androidutils.library.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 猿人 on 2017/2/13.
 */

public class NotifyService extends Service {
    //日期相关
    private long systemTime;
    private long selectTime;
    //常量
    private static final String TAG = "NotifyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册EventBus
        EventBus.getDefault().register(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提取是否设置了到时间的通知,默认没有设置
        boolean isSettingNotify = SharedPreferencesUtil.getBoolean(this,Constants.NOTIFY_SETTING,false);
        if(isSettingNotify){
            startRemind();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRemind(){
        //得到日历实例，主要是为了下面的获取时间
        Calendar  mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        //获取当前毫秒值
        systemTime = System.currentTimeMillis();
        //是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //提取设置时间
        String time  = SharedPrefsUtil.getValue(this,Constants.NOTIFY_PF_NAME,Constants.NOTIFY_PF_TIME,Constants.DEFAULT_TIME);
        //判断用户是否设置了时间
        if(!time.equals(Constants.DEFAULT_TIME)){
            //解析时间
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);
            //设置在几点提醒
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            //设置在几分提醒
            mCalendar.set(Calendar.MINUTE, minute);
            //默认秒为0
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.MILLISECOND, 0);
            //获取上面设置的时间的毫秒值
            selectTime = mCalendar.getTimeInMillis();
            // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
            if(systemTime > selectTime) {
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            //创建广播接受者
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pd = PendingIntent.getBroadcast(this,0,intent,0);
            //得到AlarmManager实例
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            //设置提醒,时间就是上述设置的时间
            am.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),pd);
        }

    }


    /**
     * 提供外部接口，取消通知
     */
    @Subscribe
    public void cancleRemind(AlarmInfo info){
        Intent intent = new Intent(NotifyService.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(NotifyService.this, 0,
                intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        SharedPrefsUtil.putValue(this,Constants.NOTIFY_PF_NAME,Constants.NOTIFY_SETTING,false);
        Toast.makeText(this, "关闭了提醒", Toast.LENGTH_SHORT).show();
        stopSelf();
    }
}
