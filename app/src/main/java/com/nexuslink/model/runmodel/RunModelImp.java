package com.nexuslink.model.runmodel;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.RoomGoal;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.UserUtils;

import java.text.DecimalFormat;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 猿人 on 2017/1/28.
 */

public class RunModelImp implements RunModel {
    //===============================================辅助变量
    private int duration;
    private int seconds, minutes, hours;
    DecimalFormat df = new DecimalFormat("#0.0");
    /**
     * api
     */
    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    //===============================================数据库
    private RunDao mRunDao = DBUtil.getRunDao();


    @Override
    public void saveRecord(List<AMapLocation> list, String date) {
        if (list != null && list.size() > 0) {
            String duration = getDuration();
            float distance = getDistance(list);
            String average = getAverage(distance);
            String pathLineString = getPathLineString(list);
            AMapLocation firstLocation = list.get(0);
            AMapLocation lastLocation = list.get(list.size() - 1);
            String startPoint = amapLocationToString(firstLocation);
            String endPoint = amapLocationToString(lastLocation);
            float kcal = Float.parseFloat(getCurrentCol(distance));
            String _date = date.split(" ")[0];
            String time = date.split(" ")[1];
            insertNewRecord(String.valueOf(distance), duration, average, pathLineString, startPoint,
                    endPoint, _date, time, kcal);

        }
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getDuration() {
        return String.valueOf(this.duration);
    }

    @Override
    public float getDistance(List<AMapLocation> list) {
        float distance = 0;
        if (list == null || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation firstPoint = list.get(i);
            LatLng firstLatLng = new LatLng(firstPoint.getLatitude(), firstPoint.getLongitude());
            AMapLocation secondPoint = list.get(i + 1);
            LatLng secondLatLng = new LatLng(secondPoint.getLatitude(), secondPoint.getLongitude());
            //计算两点之间的距离
            double betweenDis = AMapUtils.calculateLineDistance(firstLatLng, secondLatLng);
            distance += betweenDis;
        }
        return distance;
    }


    /**
     * m/s
     *
     * @param distance
     * @return
     */
    public String getAverage(float distance) {
        return df.format(distance / duration);
    }


    public String getPathLineString(List<AMapLocation> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer pathLine = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            AMapLocation location = list.get(i);
            String locString = amapLocationToString(location);
            pathLine.append(locString + ";");
        }
        String pathLineString = pathLine.toString();
        Log.i(TAG, pathLineString);
        pathLineString = pathLineString.substring(0, pathLineString.length() - 1);
        Log.i(TAG, pathLineString);
        return pathLineString;
    }

    public String amapLocationToString(AMapLocation location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getLatitude()).append(",");
        locString.append(location.getLongitude()).append(",");
        locString.append(location.getProvider()).append(",");
        locString.append(location.getTime()).append(",");
        locString.append(location.getSpeed()).append(",");
        locString.append(location.getBearing());
        return locString.toString();
    }


    public void insertNewRecord(String distance, String duration, String average, String pathLineString, String startPoint, String endPoint, String date, String time, float kcal) {
        Run run = new Run(null, distance,
                duration, average, pathLineString,
                startPoint, endPoint, date, time, kcal, false);
        mRunDao.insert(run);
    }

    @Override
    public void calculateCurrentTime() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
            if (minutes == 60) {
                minutes = 0;
                hours++;
            }
        }
    }

    @Override
    public String getRealCurrentTime() {

        return getRealTime(hours) + ":" + getRealTime(minutes) + ":" + getRealTime(seconds);

    }

    /**
     * 单位kcol
     *
     * @param distance 单位m
     * @return
     */
    public String getCurrentCol(float distance) {
        final int weight = UserUtils.getUserWeight();
        return df.format(weight * distance * 1.036f / 1000);
    }

    public String getCurrentMiles(float distance) {
        return df.format(distance);
    }


    @Override
    public void postRoomData(int rId, long goal, final CallBackListener listener) {
        api.setGoal(UserUtils.getUserId(), rId, goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RoomGoal>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(RoomGoal roomGoal) {
                        if (roomGoal.getCode() == Constants.SUCCESS) {
                            listener.onFinish(roomGoal.getGoals());
                        } else {
                            listener.onError(new Exception("上传跑房数据时失败"));
                        }
                    }
                });
    }


    private String getRealTime(int time) {
        String realTime = String.valueOf(time);
        if (time < 10) {
            realTime = "0" + time;
        }
        return realTime;
    }
}
