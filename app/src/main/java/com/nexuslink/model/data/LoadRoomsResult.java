package com.nexuslink.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class LoadRoomsResult implements Parcelable {


    /**
     * code : 200
     * room : [{"roomId":5,"roomType":0,"roomGoal":36,"roomName":"图","startTime":"2017-03-15 16:59","users":[{"uid":15,"uName":"哎哎哎","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":1064,"uHistoryMileage":500,"uAchievements":[false,false,false,false,false,false,false,false]}]}]
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
         * roomId : 5
         * roomType : 0
         * roomGoal : 36
         * roomName : 图
         * startTime : 2017-03-15 16:59
         * users : [{"uid":15,"uName":"哎哎哎","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":1064,"uHistoryMileage":500,"uAchievements":[false,false,false,false,false,false,false,false]}]
         */

        private int roomId;
        private int roomType;
        private int roomGoal;
        private String roomName;
        private String startTime;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
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
             * uName : 哎哎哎
             * uGender : M
             * uImg : user15.jpeg
             * uHeight : 174
             * uWeight : 60
             * uFansnum : 2
             * uExp : 0
             * uHistoryStep : 1064
             * uHistoryMileage : 500
             * uAchievements : [false,false,false,false,false,false,false,false]
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
            private List<Boolean> uAchievements;

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

            public List<Boolean> getUAchievements() {
                return uAchievements;
            }

            public void setUAchievements(List<Boolean> uAchievements) {
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
                dest.writeList(this.uAchievements);
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
                this.uAchievements = new ArrayList<Boolean>();
                in.readList(this.uAchievements, Boolean.class.getClassLoader());
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
            dest.writeString(this.startTime);
            dest.writeList(this.users);
        }

        public RoomBean() {
        }

        protected RoomBean(Parcel in) {
            this.roomId = in.readInt();
            this.roomType = in.readInt();
            this.roomGoal = in.readInt();
            this.roomName = in.readString();
            this.startTime = in.readString();
            this.users = new ArrayList<UsersBean>();
            in.readList(this.users, UsersBean.class.getClassLoader());
        }

        public static final Creator<RoomBean> CREATOR = new Creator<RoomBean>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.room);
    }

    public LoadRoomsResult() {
    }

    protected LoadRoomsResult(Parcel in) {
        this.code = in.readInt();
        this.room = new ArrayList<RoomBean>();
        in.readList(this.room, RoomBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoadRoomsResult> CREATOR = new Parcelable.Creator<LoadRoomsResult>() {
        @Override
        public LoadRoomsResult createFromParcel(Parcel source) {
            return new LoadRoomsResult(source);
        }

        @Override
        public LoadRoomsResult[] newArray(int size) {
            return new LoadRoomsResult[size];
        }
    };
}
