package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/4/5.
 */

public class RankInfo {

    /**
     * code : 200
     * users : [{"uid":15,"uName":"哎哎哎","uGender":"M","uImg":"user15.jpeg","uHeight":174,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":87089,"uHistoryMileage":394923,"uAchievements":null},{"uid":1,"uName":"哇哈哈","uGender":"M","uImg":"user1.jpeg","uHeight":175,"uWeight":50,"uFansnum":2,"uExp":0,"uHistoryStep":12001,"uHistoryMileage":0,"uAchievements":null},{"uid":2,"uName":"哈哈哈233","uGender":"M","uImg":"user2.jpeg","uHeight":161,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":3,"uName":"哈哈哈2333","uGender":"M","uImg":null,"uHeight":161,"uWeight":60,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":4,"uName":"哈","uGender":"M","uImg":null,"uHeight":161,"uWeight":60,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":5,"uName":"jacob","uGender":"M","uImg":null,"uHeight":180,"uWeight":57,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":6,"uName":"哈2333","uGender":"M","uImg":null,"uHeight":161,"uWeight":60,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":7,"uName":"哈23","uGender":"M","uImg":null,"uHeight":161,"uWeight":60,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":8,"uName":"得得得","uGender":"M","uImg":"user8.png","uHeight":170,"uWeight":50,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":9,"uName":"asd","uGender":"W","uImg":null,"uHeight":177,"uWeight":56,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":10,"uName":"Asd,12313-qq-45641321","uGender":"M","uImg":null,"uHeight":0,"uWeight":0,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":12,"uName":"12313-sina-45641321","uGender":"M","uImg":null,"uHeight":0,"uWeight":0,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":13,"uName":"12313-sina-487946115","uGender":"M","uImg":"user13.jpeg","uHeight":0,"uWeight":0,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":14,"uName":"卧槽","uGender":"M","uImg":null,"uHeight":175,"uWeight":50,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":16,"uName":"张冲","uGender":"M","uImg":null,"uHeight":155,"uWeight":100,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":17,"uName":"威尔","uGender":"M","uImg":null,"uHeight":199,"uWeight":75,"uFansnum":2,"uExp":0,"uHistoryStep":15230,"uHistoryMileage":0,"uAchievements":null},{"uid":18,"uName":"好哥哥","uGender":"M","uImg":"user18.jpeg","uHeight":180,"uWeight":60,"uFansnum":2,"uExp":0,"uHistoryStep":315,"uHistoryMileage":0,"uAchievements":null},{"uid":19,"uName":"好哥哥啊","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":1,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":20,"uName":"","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},{"uid":21,"uName":"123456789","uGender":"M","uImg":null,"uHeight":170,"uWeight":50,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}]
     */

    private int code;
    /**
     * uid : 15
     * uName : 哎哎哎
     * uGender : M
     * uImg : user15.jpeg
     * uHeight : 174
     * uWeight : 60
     * uFansnum : 2
     * uExp : 0
     * uHistoryStep : 87089
     * uHistoryMileage : 394923
     * uAchievements : null
     */

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
        private Object uAchievements;

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

        public Object getUAchievements() {
            return uAchievements;
        }

        public void setUAchievements(Object uAchievements) {
            this.uAchievements = uAchievements;
        }
    }
}
