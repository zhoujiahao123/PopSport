package com.nexuslink.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.LinearLayout;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.service.AlarmService;
import com.nexuslink.ui.fragment.AppointmentFragment;
import com.nexuslink.ui.fragment.CommunityFragment;
import com.nexuslink.ui.fragment.PersonInfoFragment;
import com.nexuslink.ui.fragment.StepAndRunFragment;
import com.nexuslink.util.PermissionsChecker;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UpLoadDatasUtils;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewActivity extends BaseActivity {

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
    //===============================================权限相关
    private static final int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        ButterKnife.bind(this);
        mPermissionsChecker = new PermissionsChecker(this);
        initView();
        //每次用户打开应用程序就进行一些数据的上传
        //检查用户联网否
        if(NetworkHelper.isNetworkAvailable(this)){
            Log.i(TAG,"网络可用");
            //进行数据上传
           new Thread(new Runnable() {
                @Override
                public void run() {
                    UpLoadDatasUtils.upLoadSteps();
                    UpLoadDatasUtils.upLoadRunDatas();
                }
            }).start();

        }
        //开始跑房的闹钟提示
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
    }


    private long mPressTime = 0;
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
        if((mNowTime - mPressTime)>2000){
            ToastUtil.showToast(this,"再按一次退出");
            mPressTime = mNowTime;
        }else{
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }
    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this,REQUEST_CODE,PERMISSIONS);
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
