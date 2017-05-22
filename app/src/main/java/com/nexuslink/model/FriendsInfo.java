package com.nexuslink.model;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/23.
 */

public class FriendsInfo {


    /**
     * code : 200
     * users : [{"uid":16,"uName":"张冲","uGender":"M","uImg":null,"uHeight":155,"uWeight":100,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":8,"uName":"得得得","uGender":"M","uImg":"user8.png","uHeight":170,"uWeight":50,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":18,"uName":"好哥哥","uGender":"M","uImg":"user18.jpeg","uHeight":180,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":3294,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":24,"uName":"老司机","uGender":"M","uImg":"user24.jpeg","uHeight":178,"uWeight":52,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":26,"uName":"周家豪","uGender":"M","uImg":"user26.jpeg","uHeight":180,"uWeight":60,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":17,"uName":"威尔","uGender":"M","uImg":null,"uHeight":199,"uWeight":75,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":27,"uName":"zhangxingrui","uGender":"M","uImg":null,"uHeight":173,"uWeight":50,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":30,"uName":"111","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":1,"uExp":0,"uHistoryStep":1206,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":9,"uName":"asd","uGender":"W","uImg":null,"uHeight":177,"uWeight":56,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":1,"uName":"哇哈哈","uGender":"M","uImg":"user1.jpeg","uHeight":175,"uWeight":50,"uFansnum":5,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":2,"uName":"哈哈哈233","uGender":"M","uImg":"user2.jpeg","uHeight":161,"uWeight":60,"uFansnum":4,"uExp":0,"uHistoryStep":24000,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":19,"uName":"好哥哥啊","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":12,"uName":"12313","uGender":"M","uImg":null,"uHeight":0,"uWeight":0,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":40,"uName":"匡政泽","uGender":"M","uImg":"user40.png","uHeight":170,"uWeight":50,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":153,"uAchievements":[false,false,false,false,false,false,false,false]},{"uid":41,"uName":"1122","uGender":"M","uImg":"user41.png","uHeight":170,"uWeight":50,"uFansnum":3,"uExp":0,"uHistoryStep":1120,"uHistoryMileage":0,"uAchievements":[false,false,false,false,false,false,false,false]}]
     */

    private int code;
    private List<UsersBean> users;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * uid : 16
         * uName : 张冲
         * uGender : M
         * uImg : null
         * uHeight : 155
         * uWeight : 100
         * uFansnum : 1
         * uExp : 0
         * uHistoryStep : 0
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
