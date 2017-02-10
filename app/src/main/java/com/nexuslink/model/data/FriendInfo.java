package com.nexuslink.model.data;

/**
 * Created by ASUS-NB on 2017/2/8.
 */

public class FriendInfo {
    private int fId;
    private int followFlag;
    private String fName;
    private char fGender;
    private String fImg;
    private float fHeight;
    private float fWeight;
    private int fFansNum;
    private int fExp;
    private long fHistoryStep;
    private long fHistoryMileage;
    private int[] fAchievements;

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public int getFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(int followFlag) {
        this.followFlag = followFlag;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public char getfGender() {
        return fGender;
    }

    public void setfGender(char fGender) {
        this.fGender = fGender;
    }

    public float getfWeight() {
        return fWeight;
    }

    public void setfWeight(float fWeight) {
        this.fWeight = fWeight;
    }

    public float getfHeight() {
        return fHeight;
    }

    public void setfHeight(float fHeight) {
        this.fHeight = fHeight;
    }

    public String getfImg() {
        return fImg;
    }

    public void setfImg(String fImg) {
        this.fImg = fImg;
    }

    public int getfFansNum() {
        return fFansNum;
    }

    public void setfFansNum(int fFansNum) {
        this.fFansNum = fFansNum;
    }

    public int getfExp() {
        return fExp;
    }

    public void setfExp(int fExp) {
        this.fExp = fExp;
    }

    public long getfHistoryStep() {
        return fHistoryStep;
    }

    public void setfHistoryStep(long fHistoryStep) {
        this.fHistoryStep = fHistoryStep;
    }

    public int[] getfAchievements() {
        return fAchievements;
    }

    public void setfAchievements(int[] fAchievements) {
        this.fAchievements = fAchievements;
    }

    public long getfHistoryMileage() {
        return fHistoryMileage;
    }

    public void setfHistoryMileage(long fHistoryMileage) {
        this.fHistoryMileage = fHistoryMileage;
    }
}
