package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class FollowedInfo {
    private List<SimpleUser> simpleUserList;

    public List<SimpleUser> getSimpleUserList() {
        return simpleUserList;
    }

    public void setSimpleUserList(List<SimpleUser> simpleUserList) {
        this.simpleUserList = simpleUserList;
    }

    public static class SimpleUser{
        private int fId;
        private String fName;
        private String fImg;
        private int[] fAchievements;

        public int getfId() {
            return fId;
        }

        public void setfId(int fId) {
            this.fId = fId;
        }

        public String getfName() {
            return fName;
        }

        public void setfName(String fName) {
            this.fName = fName;
        }

        public String getfImg() {
            return fImg;
        }

        public void setfImg(String fImg) {
            this.fImg = fImg;
        }

        public int[] getfAchievements() {
            return fAchievements;
        }

        public void setfAchievements(int[] fAchievements) {
            this.fAchievements = fAchievements;
        }
    }
}
