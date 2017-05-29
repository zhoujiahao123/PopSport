package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class FansInfo {

    /**
     * code : 200
     * fans : [{"uid":18,"uName":"好哥哥","uGender":"M","uImg":"user18.jpeg","uHeight":180,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":3294,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":26,"uName":"周家豪","uGender":"M","uImg":"user26.jpeg","uHeight":180,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":41,"uName":"1122","uGender":"M","uImg":"user41.png","uHeight":170,"uWeight":50,"uFansnum":3,"uExp":0,"uHistoryStep":1120,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]}]
     */

    private int code;
    private List<FansBean> fans;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FansBean> getFans() {
        return fans;
    }

    public void setFans(List<FansBean> fans) {
        this.fans = fans;
    }

    public static class FansBean {
        /**
         * uid : 18
         * uName : 好哥哥
         * uGender : M
         * uImg : user18.jpeg
         * uHeight : 180
         * uWeight : 60
         * uFansnum : 2
         * uExp : 0
         * uHistoryStep : 3294
         * uHistoryMileage : 0
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
