package com.nexuslink.model.data;

import java.util.Date;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class JoinRoomResult {

    /**
     * code : 200
     * room : {"roomId":1,"roomType":0,"roomGoal":60,"roomName":"一起跑步吧","startDate":null,"startTime":null,"users":[{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":16,"uName":"张冲","uGender":"M","uImg":null,"uHeight":155,"uWeight":100,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]}
     */

    private int code;
    private RoomBean room;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public static class RoomBean {
        /**
         * roomId : 1
         * roomType : 0
         * roomGoal : 60
         * roomName : 一起跑步吧
         * startDate : null
         * startTime : null
         * users : [{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":16,"uName":"张冲","uGender":"M","uImg":null,"uHeight":155,"uWeight":100,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]
         */

        private int roomId;
        private int roomType;
        private int roomGoal;
        private String roomName;
        private Date startDate;
        private Date startTime;
        private List<LoadRoomsResult.RoomBean.UsersBean> users;

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public int getRoomType() {
            return roomType;
        }

        public void setRoomType(int roomType) {
            this.roomType = roomType;
        }

        public int getRoomGoal() {
            return roomGoal;
        }

        public void setRoomGoal(int roomGoal) {
            this.roomGoal = roomGoal;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public List<LoadRoomsResult.RoomBean.UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<LoadRoomsResult.RoomBean.UsersBean> users) {
            this.users = users;
        }


    }
}
