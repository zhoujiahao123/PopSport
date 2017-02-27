package com.nexuslink.model.runmodel;

import com.amap.api.location.AMapLocation;

import java.util.List;

/**
 * Created by 猿人 on 2017/1/28.
 */

public interface RunModel {
    void  saveRecord(List<AMapLocation> list, String date);
    String getDuration();
    float getDistance(List<AMapLocation> list);
    String getCurrentAverage(float distance);
    void calculateCurrentTime();
    String getRealCurrentTime();
    String getCurrentCol(float distance);
    String getCurrentMiles(float distance);
    void setStartTime(long startTime);
    void setEndTime(long endTime);
    String getMaxSpeed();

}
