package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/3/5.
 */

public class SingleCommunityInfo {

    /**
     * code : 200
     * article : {"articleId":57,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"55yL6KeB5LqG5oCB5bqm\n","images":[],"commentNum":0,"likeNum":1,"lookNum":7,"date":"2017-03-05","time":"16:19:56","likeArticle":true}
     */

    private int code;
    private ArticleBean article;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public static class ArticleBean {
        /**
         * articleId : 57
         * userId : 15
         * userBean : {"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}
         * text : 55yL6KeB5LqG5oCB5bqm

         * images : []
         * commentNum : 0
         * likeNum : 1
         * lookNum : 7
         * date : 2017-03-05
         * time : 16:19:56
         * likeArticle : true
         */

        private int articleId;
        private int userId;
        private UserBeanBean userBean;
        private String text;
        private int commentNum;
        private int likeNum;
        private int lookNum;
        private String date;
        private String time;
        private boolean likeArticle;
        private List<String> images;

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

        public UserBeanBean getUserBean() {
            return userBean;
        }

        public void setUserBean(UserBeanBean userBean) {
            this.userBean = userBean;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isLikeArticle() {
            return likeArticle;
        }

        public void setLikeArticle(boolean likeArticle) {
            this.likeArticle = likeArticle;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class UserBeanBean {
            /**
             * uid : 15
             * uName : 张兴锐
             * uGender : M
             * uImg : user15.jpeg
             * uHeight : 0
             * uWeight : 0
             * uFansnum : 2
             * uExp : 0
             * uHistoryStep : 0
             * uHistoryMileage : 0
             * uAchievements : null
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
}
