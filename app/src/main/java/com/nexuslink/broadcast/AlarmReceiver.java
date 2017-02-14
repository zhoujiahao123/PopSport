package com.nexuslink.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.service.NotifyService;
import com.nexuslink.ui.activity.MainViewActivity;
import com.nexuslink.util.ToastUtil;

/**
 * Created by 猿人 on 2017/2/13.
 */

public class AlarmReceiver extends BroadcastReceiver {
    //===============================================常量
    private static final String TAG = "AlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //收到来自Alarm的消息
        ToastUtil.showToast(BaseApplication.getContext(),"收到通知啦");
        Log.i(TAG,"收到信息");
        //唤醒通知栏
        PendingIntent toMainPd = PendingIntent.getActivity(context,0,new Intent(context,MainViewActivity.class),0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentText("您今天的运动时间到了，快进来健身吧")
                .setLargeIcon(BitmapFactory.decodeResource(BaseApplication.mContext.getResources(),R.drawable.poplog))
                .setSmallIcon(R.drawable.poplog)
                .setContentIntent(toMainPd)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
        //再次打开Service
        context.startService(new Intent(context, NotifyService.class));
    }
}
