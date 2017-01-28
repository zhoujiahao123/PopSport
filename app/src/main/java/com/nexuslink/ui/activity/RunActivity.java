package com.nexuslink.ui.activity;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.nexuslink.R;
import com.nexuslink.model.data.PathRecord;
import com.nexuslink.presenter.runpresenter.RunPresenter;
import com.nexuslink.ui.view.RunView;
import com.nexuslink.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunActivity extends AppCompatActivity implements LocationSource, RunView {

    //===============================================常量
    private static final String TAG = "RunActivity";
    private static final float MAX_ZOOM_LEVEL = 18f;
    private static final float MIN_ZOOM_LEVEL = 15f;
    private static final int TOTAL_TIME = 1000 * 60 * 20;//20分钟
    private static final int INTERVAL = 1000;
    @BindView(R.id.run_title_tv)
    TextView runTitleTv;
    //定时器
    private TimeCount time;
    //===============================================view
    @BindView(R.id.bt_run)
    ToggleButton btRun;
    @BindView(R.id.run_currentMiles)
    TextView CurrentMiles;
    @BindView(R.id.run_current_time)
    TextView CurrentTimeTv;
    @BindView(R.id.run_current_average_speed)
    TextView CurrentAverageSpeedTv;
    @BindView(R.id.run_current_consume)
    TextView CurrentConsume;
    //===============================================轨迹线
    private PolylineOptions mPolyoptions;
    private PathRecord record;
    //===============================================presenter
    private RunPresenter mRunPresenter;
    //===============================================地图层
    private MapView mMapView;
    private AMap aMap ;
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
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(myLocation));
                    //根据条件判断是否启动路线轨迹记录
                    if (btRun.isChecked()) {
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        ButterKnife.bind(this);
        initMapView(savedInstanceState);
        initPolyLine();
        initLocation();
        mRunPresenter = new RunPresenter(this);
        mLocationClient.startLocation();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPolyLine() {
        mPolyoptions = new PolylineOptions();
        mPolyoptions.width(10f);
        mPolyoptions.color(getResources().getColor(R.color.blue_normal, null));
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
        mLocationOption.setInterval(1000);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(25000);
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
        //显示定位层
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        time.cancel();
        time = null;
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

    @OnClick(R.id.bt_run)
    public void onClick() {
        if (btRun.isChecked()) {
            aMap.clear();
            if (record != null) {
                record = null;
            }
            time = new TimeCount(TOTAL_TIME, INTERVAL);
            time.start();
            record = new PathRecord();
            long mStartTime = System.currentTimeMillis();
            mRunPresenter.startRecord(mStartTime);
            record.setDate(getCurrentDate(mStartTime));
        } else {
            time.cancel();
            long mEndTime = System.currentTimeMillis();
            mRunPresenter.saveRecord(record.getPathline(),record.getDate(),mEndTime);
        }
    }
    private String getCurrentDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }

    ValueAnimator animator;
    @Override
    public void startTitleAnim() {
        final int DURATION = 1000;
        runTitleTv.setText("等待开始...");
        animator = new ValueAnimator().ofInt(0,1).setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                runTitleTv.setAlpha(fraction);
            }
        });
        animator.setRepeatCount(INTERVAL);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }
    @Override
    public void endTitleAnim() {
        animator.end();
        runTitleTv.setText("继续加油哦...");
    }
    @Override
    public void setCurrentTime(String realTime) {
        if(realTime.length()>5){
            CurrentMiles.setTextSize(getResources().getDimension(R.dimen.font_large));
        }
         CurrentTimeTv.setText(realTime);
    }

    @Override
    public void setCurrentSpeed(String speed) {
        CurrentAverageSpeedTv.setText(speed+"m/s");
    }

    @Override
    public void setCurrentCol(String col) {
        CurrentConsume.setText(col);
    }

    @Override
    public void setCurrentDistance(String miles) {
        CurrentMiles.setText(miles);
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
