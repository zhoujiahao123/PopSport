package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.nexuslink.R;
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

public class MainViewActivity extends AppCompatActivity implements View.OnLayoutChangeListener {

    //===============================================常量
    private static String TAG = "MainViewActivity";
    @BindView(R.id.tav_view)
    TabView tavView;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    //===============================================view相关
    private List<TabViewChild> tabViewChildList = new ArrayList<>();
    //===============================================屏幕
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        ButterKnife.bind(this);
        initView();

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

    }

    @Override
    protected void onResume() {
        super.onResume();
        rootLayout.addOnLayoutChangeListener(this);
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



    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){


        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){

        }
    }
}
