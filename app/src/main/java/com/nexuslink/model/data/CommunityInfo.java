package com.nexuslink.model.data;

import java.util.Date;
import java.util.List;

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

        private int articleId;
        private int userId;
        private String userImageUrl;
        private String userName;
        private int userLevel;
        private String text;
        private List<String> images;
        private int commentNum;
        private int likeNum;
        private int lookNum;
        private Date date;
        private Date time;
        private boolean likeArticle;

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public void setUserImageUrl(String userImageUrl) {
            this.userImageUrl = userImageUrl;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getLookNum() {
            return lookNum;
        }

        public void setLookNum(int lookNum) {
            this.lookNum = lookNum;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public boolean isLikeArticle() {
            return likeArticle;
        }

        public void setLikeArticle(boolean likeArticle) {
            this.likeArticle = likeArticle;
        }
    }
}
