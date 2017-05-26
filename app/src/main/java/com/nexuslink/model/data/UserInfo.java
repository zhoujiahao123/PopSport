package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class UserInfo {

    /**
     * code : 200
     * user : {"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":3,"uExp":0,"uHistoryStep":173258,"uHistoryMileage":754,"uAchievements":[false,false,false,false,false,false,false,false]}
     */

    private int code;
    private UserBean user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        @Override
        public String toString() {
            return "UserBean{" +
                    "uid=" + uid +
                    ", uName='" + uName + '\'' +
                    ", uGender='" + uGender + '\'' +
                    ", uImg='" + uImg + '\'' +
                    ", uHeight=" + uHeight +
                    ", uWeight=" + uWeight +
                    ", uFansnum=" + uFansnum +
                    ", uExp=" + uExp +
                    ", uHistoryStep=" + uHistoryStep +
                    ", uHistoryMileage=" + uHistoryMileage +
                    ", uAchievements=" + uAchievements +
                    '}';
        }

        /**
         * uid : 15
         * uName : 张兴锐
         * uGender : M
         * uImg : user15.jpeg
         * uHeight : 174
         * uWeight : 60
         * uFansnum : 3
         * uExp : 0
         * uHistoryStep : 173258
         * uHistoryMileage : 754
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
