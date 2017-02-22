package com.nexuslink.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.nexuslink.R;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.ui.fragment.AppointmentFragment;
import com.nexuslink.ui.fragment.CommunityFragment;
import com.nexuslink.ui.fragment.PersonInfoFragment;
import com.nexuslink.ui.fragment.StepAndRunFragment;

import com.wuxiaolong.androidutils.library.DisplayMetricsUtil;

import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

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


    public void inVisibleTab() {
        tavView.setTabViewHeight(0);
    }

    public void visibleTab() {
        tavView.setTabViewHeight(DisplayMetricsUtil.dip2px(this,this.getResources().getDimension(R.dimen.tab_height)));
    }




}
