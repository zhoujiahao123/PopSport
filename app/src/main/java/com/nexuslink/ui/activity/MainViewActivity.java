package com.nexuslink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.nexuslink.R;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.service.AlarmService;
import com.nexuslink.ui.fragment.AppointmentFragment;
import com.nexuslink.ui.fragment.CommunityFragment;
import com.nexuslink.ui.fragment.PersonInfoFragment;
import com.nexuslink.ui.fragment.StepAndRunFragment;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewActivity extends AppCompatActivity {

    //===============================================常量
    private static String TAG = "MainViewActivity";
    @BindView(R.id.tav_view)
    TabView tavView;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    //===============================================view相关
    private List<TabViewChild> tabViewChildList = new ArrayList<>();

    //===============================================屏幕
    private AlterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        ButterKnife.bind(this);
        initView();
        //每次用户打开应用程序就进行一些数据的上传
        //检查用户联网否
        if(NetworkHelper.isNetworkAvailable(this)){
            //进行数据上传
           /* UpLoadDatasUtils.upLoadSteps();
            UpLoadDatasUtils.upLoadRunDatas();*/
        }
        //开始跑房的闹钟提示
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        Fragment stepAndRunFragment = new StepAndRunFragment();
        Fragment appointFragment = new AppointmentFragment();
        Fragment communityFragment = new CommunityFragment();
        Fragment personalInfoFragment = PersonInfoFragment.getInstance();

        TabViewChild stepAndRun = new TabViewChild(R.drawable.step_press, R.drawable.step_normal, "运动", stepAndRunFragment);
        TabViewChild appoint = new TabViewChild(R.drawable.appoint_press, R.drawable.appoint_normal, "跑房", appointFragment);
        TabViewChild community = new TabViewChild(R.drawable.community_press, R.drawable.compress_normal, "社区", communityFragment);
        TabViewChild personinfo = new TabViewChild(R.drawable.person_press, R.drawable.person_normal, "我的", personalInfoFragment);

        tabViewChildList.add(stepAndRun);
        tabViewChildList.add(appoint);
        tabViewChildList.add(community);
        tabViewChildList.add(personinfo);

        tavView.setTabViewChild(tabViewChildList, getSupportFragmentManager());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
