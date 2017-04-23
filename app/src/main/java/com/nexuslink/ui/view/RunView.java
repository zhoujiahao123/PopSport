package com.nexuslink.ui.view;

import com.nexuslink.model.data.RoomGoal;

import java.util.List;

/**
 * Created by 猿人 on 2017/1/28.
 */

public interface RunView {

    //时间
    void setCurrentTime(String realTime);
    //速度
    void setCurrentSpeed(String speed);
    //设置能量
    void setCurrentCol(String col);
    //设置当前公里数
    void setCurrentDistance(String miles);
    //设置最大速度
    void setMaxSpeed(String maxSpeed);
    void showError(String str);
    void postDataSuccess();
    //跳转到详情界面，并把这次数据传送过去
    void intentToResult(List<RoomGoal.GoalsBean> goalsBeenList);
}
