package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class FansInfo {
    /**
     * code : 200
     * fans : [{"fId":18,"fName":"好哥哥","fImg":"user18.jpeg","fAchievements":[false,false,false,false,false,false,false,false]}]
     */

    private int code;
    /**
     * fId : 18
     * fName : 好哥哥
     * fImg : user18.jpeg
     * fAchievements : [false,false,false,false,false,false,false,false]
     */

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
