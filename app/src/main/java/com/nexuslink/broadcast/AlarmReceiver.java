package com.nexuslink.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.nexuslink.HasJoinedRooms;
import com.nexuslink.R;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.runhousemodel.RunHoseDetailModelImpl;
import com.nexuslink.model.runhousemodel.RunHouseDetailModel;
import com.nexuslink.ui.activity.RunActivity;

/**
 * Created by 猿人 on 2017/2/13.
 */

public class AlarmReceiver extends BroadcastReceiver {
    //===============================================常量
    private static final String TAG = "AlarmReceiver";
    public static final String COME_FROM_RUNHOUSE = "come_from_run_house";
    public static final boolean COME_FROM_RUNHOUSE_VALUE = true;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //收到来自Alarm的消息
        Log.i(TAG, "收到信息");
        final HasJoinedRooms room = getInfoFromService(intent);
        if (room == null) {
            return;
        }

        //设置震动
        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000, 1000}, 0);
        //采用系统弹窗的方式
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                0, 0, PixelFormat.TRANSPARENT);
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //设置view的相关属性
        final View view = LayoutInflater.from(context).inflate(R.layout.remind_dialog, null);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        int length_head = tv.getText().toString().length();
        String str = room.getRoomName();
        int length_between = str.length() + length_head;
        SpannableString spannableString = new SpannableString(tv.getText().toString() + str + "马上开始咯");
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), length_head, length_between , SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
        view.findViewById(R.id.confirm_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startRun = new Intent(context, RunActivity.class);
                startRun.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startRun.putExtra(COME_FROM_RUNHOUSE, COME_FROM_RUNHOUSE_VALUE);
                startRun.putExtra("type", room.getType());
                startRun.putExtra("goal", room.getGoal());
                startRun.putExtra("rId", room.getRId());
                context.startActivity(startRun);
                wm.removeView(view);
                vibrator.cancel();
            }
        });
        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(view);
                vibrator.cancel();

                RunHouseDetailModel runHouseModeImp = new RunHoseDetailModelImpl();
                runHouseModeImp.quitRoom(room.getRId(), new CallBackListener() {
                    @Override
                    public void onFinish(Object o) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });
        wm.addView(view, lp);
    }

    private HasJoinedRooms getInfoFromService(Intent intent) {
        String roomName = intent.getStringExtra("room_name");
        int rid = intent.getIntExtra("rId", -1);
        int personNum = intent.getIntExtra("person_num", -1);
        String startTime = intent.getStringExtra("start_time");
        int goal = intent.getIntExtra("goal", -1);
        int type = intent.getIntExtra("type", -1);
        return new HasJoinedRooms(null, rid, roomName, personNum, startTime, goal, type);
    }
}
