package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/9.
 */

public class MyLoadRoomsResult {

    /**
     * rooms : [{"roomId":7,"roomType":0,"roomGoal":3,"roomName":"听我们","startTime":"2017-04-09 12:53","users":[{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":110534,"uHistoryMileage":14867,"uAchievements":[false,false,false,false,false,false,false,false]}]}]
     * code : 200
     */

    private int code;
    private List<RoomsBean> rooms;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RoomsBean> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomsBean> rooms) {
        this.rooms = rooms;
    }

}
