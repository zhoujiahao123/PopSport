package com.nexuslink.model.data;

/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityInfo {
    private int flag;
    private CommunityBean communityBean;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CommunityBean getCommunityBean() {
        return communityBean;
    }

    public void setCommunityBean(CommunityBean communityBean) {
        this.communityBean = communityBean;
    }

    public static class CommunityBean{
        //头像url
        private String userImageUrl;
        private String userName;
        private String userLevel;
        private String content;
        private String[] contentImagsUrl;

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public void setUserImageUrl(String userImageUrl) {
            this.userImageUrl = userImageUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String[] getContentImagsUrl() {
            return contentImagsUrl;
        }

        public void setContentImagsUrl(String[] contentImagsUrl) {
            this.contentImagsUrl = contentImagsUrl;
        }
    }
}
