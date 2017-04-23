package com.nexuslink.model.runmodel;

import com.amap.api.location.AMapLocation;
import com.nexuslink.model.CallBackListener;

import java.util.List;

/**
 * Created by 猿人 on 2017/1/28.
 */

public interface RunModel {
    void  saveRecord(List<AMapLocation> list, String date);
    void setDuration(int duration);
    float getDistance(List<AMapLocation> list);

    void calculateCurrentTime();
    String getRealCurrentTime();
    String getCurrentCol(float distance);
    String getCurrentMiles(float distance);
    //上传此次用户跑步的相关数据
    void postRoomData(int rId, long goal, CallBackListener listener);
    //上传用户单次跑步的信息
//    void postRunData(int )

}
