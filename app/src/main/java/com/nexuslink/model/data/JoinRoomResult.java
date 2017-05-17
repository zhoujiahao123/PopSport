package com.nexuslink.model.data;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class JoinRoomResult {

    /**
     * code : 200
     * room : {"roomId":2,"roomType":0,"roomGoal":1000,"roomName":"123","startTime":"2017-04-08 12:00","users":[{"uid":1,"uName":"哇哈哈","uGender":"M","uImg":"user1.jpeg","uHeight":175,"uWeight":50,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[true,true,false,false,false,false,false,true]},{"uid":30,"uName":"111","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":125714,"uHistoryMileage":14872,"uAchievements":[false,false,false,false,false,false,false,false]}]}
     */

    private int code;
    private RoomsBean room;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RoomsBean getRoom() {
        return room;
    }

    public void setRoom(RoomsBean room) {
        this.room = room;
    }


}
