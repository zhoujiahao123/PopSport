package com.nexuslink.model.runmodel;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.nexuslink.Run;
import com.nexuslink.RunDao;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.UserUtils;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 猿人 on 2017/1/28.
 */

public class RunModelImp implements RunModel {
    //===============================================辅助变量
    private long mStartTime;
    private long mEndTime;
    private int seconds,minutes,hours;
    private float maxSpeed = 0 ;
    DecimalFormat df = new DecimalFormat("#0.0");
    /**
     * 消除最大速度误差
     */
    private int removeErrorMsg = 0;
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
            float cal = Float.parseFloat(getCurrentCol(distance));
            String _date = date.split(" ")[0];
            String time = date.split(" ")[1];
            insertNewRecord(String.valueOf(distance), duration, average, pathLineString, startPoint,
                    endPoint, _date,time, cal);

        }
    }

    @Override
    public String getDuration() {
        return String.valueOf((mEndTime - mStartTime) / 1000f);
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
     * 单位 m/s
     * @param distance 单位m
     * @return
     */

    public String getCurrentAverage(float distance) {
        long nowTime = System.currentTimeMillis();
        float nowSpeed = distance / ((nowTime - mStartTime) / 1000f);
        //最开始的5次定位不进行记录
        if(removeErrorMsg > 5){
            if (maxSpeed<nowSpeed){
                maxSpeed = nowSpeed;
            }
        }else{
            removeErrorMsg++;
        }
        return df.format(nowSpeed)+"m/s";
    }


    public String getAverage(float distance) {
        return df.format(distance / ((float) (mEndTime - mStartTime) / 1000f));
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


    public void insertNewRecord(String distance, String duration, String average, String pathLineString, String startPoint, String endPoint, String date,String time, float cal) {
        Run run = new Run((long) mRunDao.loadAll().size(), distance,
                duration, average, pathLineString,
                startPoint, endPoint, date,time, cal);
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
        if(hours == 0){
            return getRealTime(minutes)+":"+getRealTime(seconds);
        }else{
            return getRealTime(hours)+":"+getRealTime(minutes)+":"+getRealTime(seconds);
        }
    }

    /**
     * 单位kcol
     * @param distance 单位m
     * @return
     */
    public String getCurrentCol(float distance) {
        final int weight = UserUtils.getUserWeight();
        return df.format(weight * distance * 1.036f/1000);
    }

    public String getCurrentMiles(float distance) {
        return df.format(distance);
    }

    @Override
    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    @Override
    public void setEndTime(long endTime) {
        this.mEndTime = endTime;
    }

    @Override
    public String getMaxSpeed() {
        return df.format(maxSpeed)+"m/s";
    }


    private String getRealTime(int time) {
        String realTime = String.valueOf(time);
        if (time < 10) {
            realTime = "0" + time;
        }
        return realTime;
    }
}
