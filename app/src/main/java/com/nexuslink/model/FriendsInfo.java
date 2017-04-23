package com.nexuslink.model;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/23.
 */

public class FriendsInfo {

    /**
     * code : 200
     * users : [{"fId":16,"fName":"张冲","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":8,"fName":"得得得","fImg":"user8.png","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":18,"fName":"好哥哥","fImg":"user18.jpeg","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":24,"fName":"老司机","fImg":"user24.jpeg","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":26,"fName":"周家豪","fImg":"user26.jpeg","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":17,"fName":"威尔","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":27,"fName":"zhangxingrui","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]}]
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
         * fId : 16
         * fName : 张冲
         * fImg : null
         * fAchievements : [false,false,false,false,false,false,false,false]
         */

        private int fId;
        private String fName;
        private String fImg;
        private List<Boolean> fAchievements;

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }

        public String getFName() {
            return fName;
        }

        public void setFName(String fName) {
            this.fName = fName;
        }

        public String getFImg() {
            return fImg;
        }

        public void setFImg(String fImg) {
            this.fImg = fImg;
        }

        public List<Boolean> getFAchievements() {
            return fAchievements;
        }

        public void setFAchievements(List<Boolean> fAchievements) {
            this.fAchievements = fAchievements;
        }
    }
}
