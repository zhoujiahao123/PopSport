package com.nexuslink.model.data;

import android.os.Parcel;
import android.os.Parcelable;

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
    private List<RoomsBean> room;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RoomsBean> getRoom() {
        return room;
    }

    public void setRoom(List<RoomsBean> room) {
        this.room = room;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeTypedList(this.room);
    }

    public LoadRoomsResult() {
    }

    protected LoadRoomsResult(Parcel in) {
        this.code = in.readInt();
        this.room = in.createTypedArrayList(RoomsBean.CREATOR);
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
