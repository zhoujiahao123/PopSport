package com.nexuslink.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class LoadRoomsResult {

    /**
     * code : 200
     * room : [{"roomId":1,"roomType":0,"roomGoal":60,"roomName":"一起跑步吧","startDate":null,"startTime":null,"users":[{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]},{"roomId":2,"roomType":0,"roomGoal":60,"roomName":"一起跑步","startDate":null,"startTime":null,"users":[{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]}]
     */

    private int code;
    private List<RoomBean> room;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RoomBean> getRoom() {
        return room;
    }

    public void setRoom(List<RoomBean> room) {
        this.room = room;
    }

    public static class RoomBean implements Parcelable {

        /**
         * roomId : 1
         * roomType : 0
         * roomGoal : 60
         * roomName : 一起跑步吧
         * startDate : null
         * startTime : null
         * users : [{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]
         */

        private int roomId;
        private int roomType;
        private int roomGoal;
        private String roomName;
        private Date startDate;
        private Date startTime;
        private List<UsersBean> users;

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

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean implements Parcelable {

            /**
             * uid : 15
             * uName : 张兴锐
             * uGender : M
             * uImg : user15.jpeg
             * uHeight : 174
             * uWeight : 60
             * uFansnum : 1
             * uExp : 0
             * uHistoryStep : 0
             * uHistoryMileage : 0
             * uAchievements : null
             */

            private int uid;
            private String uName;
            private String uGender;
            private String uImg;
            private int uHeight;
            private int uWeight;
            private int uFansnum;
            private int uExp;
            private int uHistoryStep;
            private int uHistoryMileage;
            private Boolean[] uAchievements;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUName() {
                return uName;
            }

            public void setUName(String uName) {
                this.uName = uName;
            }

            public String getUGender() {
                return uGender;
            }

            public void setUGender(String uGender) {
                this.uGender = uGender;
            }

            public String getUImg() {
                return uImg;
            }

            public void setUImg(String uImg) {
                this.uImg = uImg;
            }

            public int getUHeight() {
                return uHeight;
            }

            public void setUHeight(int uHeight) {
                this.uHeight = uHeight;
            }

            public int getUWeight() {
                return uWeight;
            }

            public void setUWeight(int uWeight) {
                this.uWeight = uWeight;
            }

            public int getUFansnum() {
                return uFansnum;
            }

            public void setUFansnum(int uFansnum) {
                this.uFansnum = uFansnum;
            }

            public int getUExp() {
                return uExp;
            }

            public void setUExp(int uExp) {
                this.uExp = uExp;
            }

            public int getUHistoryStep() {
                return uHistoryStep;
            }

            public void setUHistoryStep(int uHistoryStep) {
                this.uHistoryStep = uHistoryStep;
            }

            public int getUHistoryMileage() {
                return uHistoryMileage;
            }

            public void setUHistoryMileage(int uHistoryMileage) {
                this.uHistoryMileage = uHistoryMileage;
            }

            public Boolean[] getUAchievements() {
                return uAchievements;
            }

            public void setUAchievements(Boolean[] uAchievements) {
                this.uAchievements = uAchievements;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.uid);
                dest.writeString(this.uName);
                dest.writeString(this.uGender);
                dest.writeString(this.uImg);
                dest.writeInt(this.uHeight);
                dest.writeInt(this.uWeight);
                dest.writeInt(this.uFansnum);
                dest.writeInt(this.uExp);
                dest.writeInt(this.uHistoryStep);
                dest.writeInt(this.uHistoryMileage);
                dest.writeArray(this.uAchievements);
            }

            public UsersBean() {
            }

            protected UsersBean(Parcel in) {
                this.uid = in.readInt();
                this.uName = in.readString();
                this.uGender = in.readString();
                this.uImg = in.readString();
                this.uHeight = in.readInt();
                this.uWeight = in.readInt();
                this.uFansnum = in.readInt();
                this.uExp = in.readInt();
                this.uHistoryStep = in.readInt();
                this.uHistoryMileage = in.readInt();
                this.uAchievements = (Boolean[]) in.readArray(Boolean[].class.getClassLoader());
            }

            public static final Creator<UsersBean> CREATOR = new Creator<UsersBean>() {
                @Override
                public UsersBean createFromParcel(Parcel source) {
                    return new UsersBean(source);
                }

                @Override
                public UsersBean[] newArray(int size) {
                    return new UsersBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.roomId);
            dest.writeInt(this.roomType);
            dest.writeInt(this.roomGoal);
            dest.writeString(this.roomName);
            dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
            dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
            dest.writeList(this.users);
        }

        public RoomBean() {
        }

        protected RoomBean(Parcel in) {
            this.roomId = in.readInt();
            this.roomType = in.readInt();
            this.roomGoal = in.readInt();
            this.roomName = in.readString();
            long tmpStartDate = in.readLong();
            this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
            long tmpStartTime = in.readLong();
            this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
            this.users = new ArrayList<UsersBean>();
            in.readList(this.users, UsersBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<RoomBean> CREATOR = new Parcelable.Creator<RoomBean>() {
            @Override
            public RoomBean createFromParcel(Parcel source) {
                return new RoomBean(source);
            }

            @Override
            public RoomBean[] newArray(int size) {
                return new RoomBean[size];
            }
        };
    }
}
