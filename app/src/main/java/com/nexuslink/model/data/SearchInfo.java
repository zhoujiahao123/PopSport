package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class SearchInfo {
    /**
     * code : 200
     * users : [{"fId":8,"fName":"1078463794","fImg":"user8.jpeg","fAchievements":[]}]
     */

    private int code;
    /**
     * fId : 8
     * fName : 1078463794
     * fImg : user8.jpeg
     * fAchievements : []
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
        private List<?> fAchievements;

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

        public List<?> getFAchievements() {
            return fAchievements;
        }

        public void setFAchievements(List<?> fAchievements) {
            this.fAchievements = fAchievements;
        }
    }
}
