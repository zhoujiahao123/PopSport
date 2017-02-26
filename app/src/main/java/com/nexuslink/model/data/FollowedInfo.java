package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class FollowedInfo{


    /**
     * code : 200
     * users : [{"fId":19,"fName":"好哥哥啊","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":22,"fName":"好哥哥哦","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":18,"fName":"好哥哥","fImg":"user18.jpeg","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":1,"fName":"哇哈哈","fImg":"user1.jpeg","fAchievements":[true,true,false,false,false,false,false,true]},{"fId":23,"fName":"哇哦","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]}]
     */

    private int code;
    /**
     * fId : 19
     * fName : 好哥哥啊
     * fImg : null
     * fAchievements : [false,false,false,false,false,false,false,false]
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
