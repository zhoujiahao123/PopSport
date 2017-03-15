package com.nexuslink.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nexuslink.HasJoinedRooms;
import com.nexuslink.HasJoinedRoomsDao;
import com.nexuslink.broadcast.AlarmReceiver;
import com.nexuslink.model.data.SetUpAlarm;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 猿人 on 2017/3/8.
 */

public class AlarmService extends Service {
    //===============================================数据库操作对象
    private HasJoinedRoomsDao joinedRoomsDao = DBUtil.getHasJoinedRoomsDap();
    private AlarmManager am;
    private Map<Long,PendingIntent> map = new HashMap<>();
    //==============================================辅助变量
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册eventBUg
        EventBus.getDefault().register(this);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        //保证sesrvice的远东anr安全
        //进行数据库的检索
        new Thread(new Runnable() {
            @Override
            public void run() {
              setUpAlarm(null);
            }
        }).start();
        //检索完成开始进行闹钟的设置
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 取消闹钟的接口
     */
    public void cancelRemind(Long id){
        am.cancel(map.get(id));
    }
    /**
     * 设置闹钟接口
     */
    @Subscribe
    public void setUpAlarm(SetUpAlarm alarm){
        List<HasJoinedRooms> joinedRoomses = joinedRoomsDao.queryBuilder()
                .where(HasJoinedRoomsDao.Properties.StartTime.gt(sdf.format(System.currentTimeMillis()))).list();
        if(joinedRoomses != null && joinedRoomses.size() >0){
            for(HasJoinedRooms room : joinedRoomses){
                Intent intent1 = new Intent(AlarmService.this, AlarmReceiver.class);

                intent1.putExtra("room_name",  room.getRoomName());
                intent1.putExtra("rId",room.getRId());
                intent1.putExtra("person_num",room.getPersonNum());
                intent1.putExtra("start_time",room.getStartTime());
                intent1.putExtra("goal",room.getGoal());
                intent1.putExtra("type",room.getType());

                PendingIntent sender = PendingIntent.getBroadcast(AlarmService.this,room.getId().intValue(),intent1,0);
                map.put(room.getId(),sender);
                am.setExact(AlarmManager.RTC_WAKEUP, TimeUtils.DateToMills(room.getStartTime()),sender);
            }
        }
    }
}
