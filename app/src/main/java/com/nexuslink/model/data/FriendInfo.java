package com.nexuslink.model.data;

import java.util.List;

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
    /**
     * code : 200
     * friend : {"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":105992,"uHistoryMileage":14867,"uAchievements":[false,false,false,false,false,false,false,false]}
     * isFollowed : false
     */

    private int code;
    private FriendBean friend;
    private boolean isFollowed;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public FriendBean getFriend() {
        return friend;
    }

    public void setFriend(FriendBean friend) {
        this.friend = friend;
    }

    public boolean isIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public static class FriendBean {
        /**
         * uid : 15
         * uName : 张兴锐
         * uGender : M
         * uImg : user15.jpeg
         * uHeight : 174
         * uWeight : 60
         * uFansnum : 2
         * uExp : 0
         * uHistoryStep : 105992
         * uHistoryMileage : 14867
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
    }
}
