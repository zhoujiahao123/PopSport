package com.nexuslink.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 猿人 on 2017/3/9.
 */

public class RoomGoal implements Parcelable {

    /**
     * code : 200
     * goals : [{"userId":15,"userName":"哎哎哎","time":1489056698923,"goal":60000}]
     */

    private int code;
    private List<GoalsBean> goals;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<GoalsBean> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalsBean> goals) {
        this.goals = goals;
    }

    public static class GoalsBean implements Parcelable {

        /**
         * userId : 15
         * userName : 哎哎哎
         * time : 1489056698923
         * goal : 60000
         */

        private int userId;
        private String userName;
        private long time;
        private int goal;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getGoal() {
            return goal;
        }

        public void setGoal(int goal) {
            this.goal = goal;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.userName);
            dest.writeLong(this.time);
            dest.writeInt(this.goal);
        }

        public GoalsBean() {
        }

        protected GoalsBean(Parcel in) {
            this.userId = in.readInt();
            this.userName = in.readString();
            this.time = in.readLong();
            this.goal = in.readInt();
        }

        public static final Parcelable.Creator<GoalsBean> CREATOR = new Parcelable.Creator<GoalsBean>() {
            @Override
            public GoalsBean createFromParcel(Parcel source) {
                return new GoalsBean(source);
            }

            @Override
            public GoalsBean[] newArray(int size) {
                return new GoalsBean[size];
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
        dest.writeTypedList(this.goals);
    }

    public RoomGoal() {
    }

    protected RoomGoal(Parcel in) {
        this.code = in.readInt();
        this.goals = in.createTypedArrayList(GoalsBean.CREATOR);
    }

    public static final Parcelable.Creator<RoomGoal> CREATOR = new Parcelable.Creator<RoomGoal>() {
        @Override
        public RoomGoal createFromParcel(Parcel source) {
            return new RoomGoal(source);
        }

        @Override
        public RoomGoal[] newArray(int size) {
            return new RoomGoal[size];
        }
    };
}
