package com.nexuslink.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.friendinfomodel.OnStartFriendInfoListener;
import com.nexuslink.ui.activity.AchievementActivity;
import com.nexuslink.ui.activity.AlterActivity;
import com.nexuslink.ui.activity.FriendActivity;
import com.nexuslink.ui.activity.FriendInfoActivity;
import com.nexuslink.ui.activity.RankActivity;
import com.nexuslink.ui.activity.TaskActivity;
import com.nexuslink.ui.view.PersonInfoView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class PersonInfoFragment extends Fragment implements PersonInfoView ,OnStartFriendInfoListener{


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
        XLog.e("OnCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL+user.getUImg(),imageHead);
        tvName.setText(user.getUName());
        XLog.e("onResume");
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
    public void startFriendInfo(Context context,int uId, String uImg, String uName) {
        Intent intent = new Intent(context, FriendInfoActivity.class);
        if(intent.resolveActivity(context.getPackageManager())!=null){
            intent.putExtra("uId",uId);
            intent.putExtra("uImg",uImg);
            intent.putExtra("uName",uName);
            startActivity(intent);
        }
    }
}
