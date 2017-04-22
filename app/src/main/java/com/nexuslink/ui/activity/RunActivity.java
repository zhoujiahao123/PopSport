package com.nexuslink.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.nexuslink.R;
import com.nexuslink.RunDao;
import com.nexuslink.broadcast.AlarmReceiver;
import com.nexuslink.model.data.PathRecord;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.presenter.runpresenter.RunPresenter;
import com.nexuslink.ui.view.RunView;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.GPSUtil;
import com.nexuslink.util.TimeUtils;
import com.nexuslink.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunActivity extends AppCompatActivity implements LocationSource, RunView {

    //===============================================常量
    private static final String TAG = "RunActivity";
    private static final float MAX_ZOOM_LEVEL = 19f;
    private static final float MIN_ZOOM_LEVEL = 15f;
    private static final int TOTAL_TIME = 1000 * 60 * 20;//20分钟
    private static final int INTERVAL = 1000;

    @BindView(R.id.run_current_time)
    TextView mCurrentTime;
    @BindView(R.id.run_current_distance)
    TextView mCurrentDistance;
    @BindView(R.id.run_current_col)
    TextView mCurrentKcol;
    @BindView(R.id.max_speed_tv)
    TextView maxSpeedTv;
    @BindView(R.id.run_current_speed)
    TextView mAverageSpeedTv;
    @BindView(R.id.start_or_pause)
    Button mStartOrPauseBt;
    @BindView(R.id.finish)
    Button mFinish;
    @BindView(R.id.activity_run)
    LinearLayout activityRun;
    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.run_date)
    TextView runDate;
    @BindView(R.id.run_time)
    TextView runTime;
    //弹窗
    android.app.AlertDialog.Builder builder ;
    android.app.AlertDialog dialog;



    //定时器
    private TimeCount time;
    //===============================================辅助变量
    private boolean isFirstLoc = true;
    private boolean hasStartRun = false;
    private boolean isComeFromRoom = false;
    private int type  = -1;
    private int goal = -1;
    private int rId = -1;
    /**
     * 定时器
     */
    private CountDownTimer countDownTimer;
    /**
     * 数据库操作
     */
    private RunDao runDao = DBUtil.getRunDao();
    //===============================================轨迹线
    private PolylineOptions mPolyoptions;
    private PathRecord record;
    //===============================================presenter
    private RunPresenter mRunPresenter;
    //===============================================地图层
    private MapView mMapView;
    private AMap aMap;
    //===============================================定位
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public OnLocationChangedListener mListener;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (mListener != null && aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //显示系统小蓝点
                    mListener.onLocationChanged(aMapLocation);
                    //取得具体坐标，经纬度
                    LatLng myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    //关闭自动回到定位点
                    if (isFirstLoc) {
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(myLocation));
                        if(!hasStartRun){
                            isFirstLoc = false;
                        }
                    }
                    //根据条件判断是否启动路线轨迹记录
                    if (mStartOrPauseBt.getText().toString().equals("暂停")) {
                        record.addpoint(aMapLocation);
                        mPolyoptions.add(myLocation);
                        redrawLine();
                    }

                } else {
                    ToastUtil.showToast(RunActivity.this, "定位失败");
                    String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                            + aMapLocation.getErrorInfo();
                    Log.e(TAG, errText);
                }
            }
        }


    };

    private void redrawLine() {
        if (mPolyoptions.getPoints().size() > 0) {
            aMap.addPolyline(mPolyoptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        ButterKnife.bind(this);


        initViews();
        initMapView(savedInstanceState);
        initPolyLine();
        initLocation();
        mRunPresenter = new RunPresenter(this);
        mLocationClient.startLocation();

        //初始化跑房，如果有的话
        initRoom();
    }

    private void initRoom() {
        //初始化一些数据
        isComeFromRoom = getIntent().getBooleanExtra(AlarmReceiver.COME_FROM_RUNHOUSE,false);
        type = getIntent().getIntExtra("type",-1);
        goal = getIntent().getIntExtra("goal",-1);
        rId = getIntent().getIntExtra("rId",-1);
        //定义弹窗
        builder = new android.app.AlertDialog.Builder(RunActivity.this).setTitle("提示")
                .setMessage("这次跑步目标已经达到，正在为您进行上传，请稍等...");
        dialog = builder.create();

        if(type == 0){
            //时间型跑房
             countDownTimer  = new CountDownTimer(goal*60*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Log.i(TAG,"计时完成");
                    //开启弹窗进行提示用户
                    dialog.show();
                    //上传用户信息
                    //时间性跑房上传距离
                    String str = mCurrentDistance.getText().toString();
                    Log.i(TAG,str.substring(0,str.length()-2));
                    mRunPresenter.postRoomData(rId,Long.parseLong(str.substring(0,str.length()-2)));
                }
            };
            countDownTimer.start();
            startOrPause();
        }else if(type == 1){
            //距离型跑房
           TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int distance = Integer.parseInt(s.toString().substring(0,s.toString().length()-2));
                    if(distance >= goal){
                        //使用弹窗给用户提醒
                        dialog.show();
                        //上传用户信息 距离型跑房上传这个取得的时间
                        mRunPresenter.postRoomData(rId, TimeUtils.StrToTime(mCurrentTime.getText().toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            mCurrentDistance.addTextChangedListener(watcher);
            startOrPause();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        // 缺少权限时, 进入权限配置页面
       {
            //如何拥有权限，检查是否打开了GPS
            if(!GPSUtil.isOPen(this)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setCancelable(false);
                builder.setMessage("您当前尚未打开GPS");
                builder.setPositiveButton("好的，带我去打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("算了，下次再来", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        }
    }



    private void initViews() {
        //toolbar
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String time = getCurrentDate(System.currentTimeMillis());
        runDate.setText(time.split(" ")[0]);
        runTime.setText(time.split(" ")[1]);
        //button
        changToUnClickable();

    }


    private void initPolyLine() {
        mPolyoptions = new PolylineOptions();
        mPolyoptions.width(13f);
        mPolyoptions.color(getResources().getColor(R.color.blue_normal));
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initLocationOptions();
    }

    private void initLocationOptions() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(2000);
        mLocationOption
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(30000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private void initMapView(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        setUpAmap();
    }

    private void setUpAmap() {
        aMap.setLocationSource(this);
        UiSettings uiSettings = aMap.getUiSettings();
        //显示定位按钮
        uiSettings.setMyLocationButtonEnabled(true);

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_icon));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);

        //显示定位层
        aMap.setMyLocationEnabled(true);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);

        aMap.setMinZoomLevel(MIN_ZOOM_LEVEL);
        aMap.setMaxZoomLevel(MAX_ZOOM_LEVEL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (time != null) {
            time.cancel();
            time = null;
        }
        if(type == 0 && countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer= null;
        }
        mLocationClient = null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.startLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    private String getCurrentDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }


    @Override
    public void setCurrentTime(String realTime) {
        if (realTime.length() > 5) {
            //  CurrentMiles.setTextSize(getResources().getDimension(R.dimen.font_large));
        }
        //     CurrentTimeTv.setText(realTime);
        mCurrentTime.setText(realTime);
    }

    @Override
    public void setCurrentSpeed(String speed) {
        //    CurrentAverageSpeedTv.setText(speed+"m/s");
        mAverageSpeedTv.setText(speed);
    }

    @Override
    public void setCurrentCol(String col) {
        //       CurrentConsume.setText(col);
        mCurrentKcol.setText(col);
    }

    @Override
    public void setCurrentDistance(String miles) {
        //  CurrentMiles.setText(miles);
        mCurrentDistance.setText(miles);
    }

    @Override
    public void setMaxSpeed(String maxSpeed) {
        maxSpeedTv.setText(maxSpeed);
    }

    @Override
    public void showError(String str) {
        ToastUtil.showToast(this,str);
        finish();
    }

    @Override
    public void postDataSuccess() {
        dialog.dismiss();
    }

    @Override
    public void intentToResult(List<RoomGoal.GoalsBean> goalsBeenList) {
        Intent intent = new Intent(this,RunHouseResultActivity.class);
        intent.putParcelableArrayListExtra("resultBeans", (ArrayList<? extends Parcelable>) goalsBeenList);
        intent.putExtra("type",type);
        startActivity(intent);
        finish();
    }



    @OnClick({R.id.start_or_pause, R.id.finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_or_pause:
                startOrPause();
                break;
            case R.id.finish:
                onBackPressed();
                break;
        }
    }

    private void startOrPause() {
        if (mStartOrPauseBt.getText().toString().equals("开始")) {

            if(hasStartRun){
                mLocationClient.startLocation();
            } else{
                //用户开启了轨迹记录功能
                hasStartRun = true;
                //开启自动回到定位处
                isFirstLoc = true;
                //打开结束开关
                changToClickable();
                if (record != null) {
                    record = null;
                }
                time = new TimeCount(TOTAL_TIME, INTERVAL);
                record = new PathRecord();
                long mStartTime = System.currentTimeMillis();
                mRunPresenter.startRecord(mStartTime);
                record.setDate(getCurrentDate(mStartTime));
            }
            //改变按钮的状态
            mStartOrPauseBt.setText("暂停");
            time.start();

        } else {
            mStartOrPauseBt.setText("开始");
            mLocationClient.stopLocation();
            time.cancel();
        }
    }

    @Override
    public void onBackPressed() {

        if(hasStartRun){
            //弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            final View dialogView = LayoutInflater.from(this).inflate(R.layout.run_wran_dialog, null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialogView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changToUnClickable();
                    String durationStr[] = mCurrentTime.getText().toString().split(":");
                    int hour = Integer.parseInt(durationStr[0]);
                    int minute = Integer.parseInt(durationStr[1]);
                    int second = Integer.parseInt(durationStr[2]);
                    int duration = hour*60*60+minute*60+second;
                    mRunPresenter.saveRecord(record.getPathline(), record.getDate(), duration);
                    dialog.dismiss();
                    //通知首界面进行更新
                    EventBus.getDefault().post(new RunViewFresh());

                   /* //在这里进行上传，如果上传成功，就将数据库中的匹配到时间的更新，否则就进行提示
                    //分解时间进行数据库的查找
                    String date = record.getDate().split(" ")[0];
                    String time = record.getDate().split(" ")[1];
                    //===============================================网络请求进行上传，上传成功，就更新

                    //进行查找
                    Run run = runDao.queryBuilder().where(RunDao.Properties.Date.eq(date),RunDao.Properties.Time.eq(time)).unique();
                    run.setHasUpLoad(true);
                    runDao.update(run);*/
                    if(!isComeFromRoom){
                        finish();
                    }else{
                        //展示此次运动成果,这里继续上传到房间去
                        if(type == 0){
                            String str = mCurrentDistance.getText().toString();
                            mRunPresenter.postRoomData(rId,Long.parseLong(str.substring(0,str.length()-2)));
                        }else if(type == 1){
                            mRunPresenter.postRoomData(rId,TimeUtils.StrToTime(mCurrentTime.getText().toString()));
                        }
                    }

                }
            });
        }else{
            finish();
        }

    }

    private void changToClickable() {
        mFinish.setClickable(true);
        mFinish.setBackground(getDrawable(R.drawable.bt_run_finish_click));
    }

    private void changToUnClickable() {
        mFinish.setClickable(false);
        mFinish.setBackground(getDrawable(R.drawable.bt_run_finish_unclick));
    }



    /**
     * 倒计时类，用于统计时间
     */
    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mRunPresenter.refreshUI(record.getPathline());
        }

        @Override
        public void onFinish() {
            time.start();
        }
    }
}
