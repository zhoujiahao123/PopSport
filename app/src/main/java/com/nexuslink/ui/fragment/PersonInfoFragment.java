package com.nexuslink.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.example.zhouwei.library.CustomPopWindow;
import com.nexuslink.R;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.EventEntry;
import com.nexuslink.model.data.Info;
import com.nexuslink.model.friendinfomodel.OnStartFriendInfoListener;
import com.nexuslink.ui.activity.AchievementActivity;
import com.nexuslink.ui.activity.AlterActivity;
import com.nexuslink.ui.activity.AlterPhotoActivity;
import com.nexuslink.ui.activity.FriendActivity;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.ui.activity.RankActivity;
import com.nexuslink.ui.activity.TaskActivity;
import com.nexuslink.ui.view.PersonInfoView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;
import com.nexuslink.util.RatingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class PersonInfoFragment extends Fragment implements PersonInfoView, OnStartFriendInfoListener {


    @BindView(R.id.group_myfriend)
    LinearLayout groupMyfriend;
    @BindView(R.id.group_runningromm)
    LinearLayout groupRunningromm;
    @BindView(R.id.group_setting)
    LinearLayout groupSetting;
    @BindView(R.id.group_mytask)
    LinearLayout groupMytask;
    @BindView(R.id.group_achieve)
    LinearLayout groupAchieve;
    @BindView(R.id.group_rank)
    LinearLayout groupRank;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_achieve)
    TextView tvAchieve;
    @BindView(R.id.change_bg)
    ImageView changeBg;
    @BindView(R.id.head_relative)
    RelativeLayout headRelative;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL + BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique().getUImg(), imageHead);
//                LoaderFactory.getGlideLoader().clearCacheMemory(BaseApplication.getContext());
//                LoaderFactory.getGlideLoader().loadNet(imageHead,Constants.PHOTO_BASE_URL+BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique().getUImg(),null);
                XLog.e("这里OK的");
            }
        }
    };

    public static PersonInfoFragment getInstance() {
        PersonInfoFragment fragment = new PersonInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personinfo, container, false);
        ButterKnife.bind(this, view);
        XLog.e("OnCreateView");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }).start();
        XLog.e("OnCreate");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
        int exp = user.getUExp();
        tvGrade.setText(RatingUtil.getRating(exp));
        tvName.setText(user.getUName());
        XLog.e(user.getUAchievements());
        String str[] = user.getUAchievements().split(",");
        String achieve = "初出茅庐";
        for (int i = 7; i > 0; i--) {
            if (str[i].equals("true")) {
                achieve = str[i];
                break;
            }
        }
        tvAchieve.setText(achieve);
        XLog.e("onResume");
        changeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int width = windowManager.getDefaultDisplay().getWidth();
                View popView = LayoutInflater.from(getContext()).inflate(R.layout.item_pop, null);
                final CustomPopWindow window = new CustomPopWindow.PopupWindowBuilder(getContext())
                        .setView(popView)
                        .setFocusable(true)
                        .setOutsideTouchable(true)
                        .create()
                        .showAtLocation(popView, Gravity.RIGHT|Gravity.TOP,0,150);
                RelativeLayout rl = (RelativeLayout) popView.findViewById(R.id.pop_window);
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), AlterPhotoActivity.class));
                        window.dissmiss();
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change_bg(EventEntry eventEntry) {
        String photoPath = eventEntry.photos.get(0).getPath();
        eventEntry.photos.clear();
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        Drawable drawable = new BitmapDrawable(bitmap);
        headRelative.setBackground(drawable);
        XLog.e("切换背景的操作执行了");
    }

    @Override
    public void onStart() {
        super.onStart();


        XLog.e("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        XLog.e("onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        XLog.e("onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        XLog.e("onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        XLog.e("onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        XLog.e("onDetach");
    }

    @Override
    public void showFriends() {
        Intent friendIntent = new Intent(getContext(), FriendActivity.class);
        startActivity(friendIntent);
    }

    @Override
    public void showRunningRoom() {

    }

    @Override
    public void showSetting() {
        Intent intent = new Intent(getContext(), AlterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTask() {
        startActivity(new Intent(getContext(), TaskActivity.class));
    }

    @Override
    public void showAchieve() {
        startActivity(new Intent(getContext(), AchievementActivity.class));
    }

    @Override
    public void showRank() {
        startActivity(new Intent(getContext(), RankActivity.class));
    }

    @OnClick({R.id.group_myfriend, R.id.group_runningromm, R.id.group_setting, R.id.group_mytask, R.id.group_achieve, R.id.group_rank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_myfriend:
                showFriends();
                break;
            case R.id.group_runningromm:
                showRunningRoom();
                break;
            case R.id.group_setting:
                showSetting();
                break;
            case R.id.group_mytask:
                showTask();
                break;
            case R.id.group_achieve:
                showAchieve();
                break;
            case R.id.group_rank:
                showRank();
                break;
        }
    }

    @Override
    public void startFriendInfo(Context context, int uId, String uImg, String uName) {
        Intent intent = new Intent(context, FriendInfoActivity.class);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra("uId", uId);
            intent.putExtra("uImg", uImg);
            intent.putExtra("uName", uName);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePhoto(Info info) {
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL + BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique().getUImg(), imageHead);
    }
}
