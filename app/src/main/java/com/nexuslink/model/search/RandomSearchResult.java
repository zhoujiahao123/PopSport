package com.nexuslink.model.search;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/28.
 */

public class RandomSearchResult {

    /**
     * code : 200
     * randomUsers : [{"fId":16,"fName":"张冲","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":2,"fName":"哈哈哈233","fImg":"user2.jpeg","fAchievements":[true,false,false,false,false,false,false,false]},{"fId":18,"fName":"好哥哥","fImg":"user18.jpeg","fAchievements":[false,false,false,false,false,false,false,false]},{"fId":3,"fName":"哈哈哈2333","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":20,"fName":"","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":5,"fName":"jacob","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":22,"fName":"好哥哥哦","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":9,"fName":"asd","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]},{"fId":27,"fName":"zhangxingrui","fImg":null,"fAchievements":[false,false,false,false,false,false,false,false]}]
     */

    private int code;
    private List<RandomUsersBean> randomUsers;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RandomUsersBean> getRandomUsers() {
        return randomUsers;
    }

    public void setRandomUsers(List<RandomUsersBean> randomUsers) {
        this.randomUsers = randomUsers;
    }

    public static class RandomUsersBean {
        /**
         * fId : 16
         * fName : 张冲
         * fImg : null
         * fAchievements : [false,false,false,false,false,false,false,false]
         */

        private int fId;
        private String fName;
        private Object fImg;
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

        public Object getFImg() {
            return fImg;
        }

        public void setFImg(Object fImg) {
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
