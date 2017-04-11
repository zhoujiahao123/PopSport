package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class ArticleBean {

    /**
     * code : 200
     * articles : [{"articleId":4,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"8J+YgPCfmITwn5iB8J+YnfCfpKDwn5ij8J+YqfCfmJTwn5if8J+YoPCfmKvwn5ij4pi58J+YmvCf\nmKnwn6SX8J+kk/CfmJXwn5ih8J+YsvCfmLLwn5iu8J+YlvCfmKPwn6Sh8J+YmvCfmJrwn5iL8J+Y\ni/CfmJzwn5ic8J+YnPCfmJzwn5iS8J+YkvCfmJLwn5ir8J+Yq/CfpKHwn5ia8J+YmvCfpKHwn6Sh\n4pi54pi58J+YjvCfmJnwn5iZ8J+kofCfmJ3wn5iW\n","images":[],"commentNum":0,"likeNum":0,"lookNum":14,"date":"2017-03-21","time":"11:28:30","likeArticle":false},{"articleId":3,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"8J+Yj/CfmI8=\n","images":[],"commentNum":8,"likeNum":1,"lookNum":49,"date":"2017-03-19","time":"18:14:33","likeArticle":true},{"articleId":5,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5Z+65pys5a6M5ZaE8J+Yjg==\n","images":[],"commentNum":1,"likeNum":1,"lookNum":14,"date":"2017-03-24","time":"19:19:04","likeArticle":true},{"articleId":6,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5Y+R552A546p546p\n","images":["article6_1.png","article6_2.png"],"commentNum":0,"likeNum":1,"lookNum":16,"date":"2017-03-29","time":"16:16:00","likeArticle":true},{"articleId":8,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"8J+YgQ==\n","images":[],"commentNum":0,"likeNum":1,"lookNum":6,"date":"2017-03-30","time":"22:37:09","likeArticle":true},{"articleId":9,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5p2l5LiA5bGA\n","images":["article9_1.png"],"commentNum":0,"likeNum":0,"lookNum":6,"date":"2017-04-02","time":"22:20:00","likeArticle":false},{"articleId":10,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5a6M5oiQ57yp5pS+44CC\n","images":[],"commentNum":0,"likeNum":1,"lookNum":5,"date":"2017-04-03","time":"12:25:11","likeArticle":false},{"articleId":11,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5ZWm5ZWm5ZWm\n","images":["article11_1.png"],"commentNum":0,"likeNum":1,"lookNum":4,"date":"2017-04-07","time":"13:58:58","likeArticle":true},{"articleId":12,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5rWL6K+V\n","images":["article12_1.png"],"commentNum":0,"likeNum":0,"lookNum":6,"date":"2017-04-07","time":"16:39:24","likeArticle":false},{"articleId":13,"userId":15,"userBean":{"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null},"text":"5rWL6K+V\n","images":["article13_1.png","article13_2.png","article13_3.png","article13_4.png","article13_5.png","article13_6.png","article13_7.png","article13_8.png","article13_9.png","article13_10.png","article13_11.png"],"commentNum":5,"likeNum":1,"lookNum":40,"date":"2017-04-07","time":"16:41:12","likeArticle":true}]
     */

    private int code;
    private List<ArticlesBean> articles;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * articleId : 4
         * userId : 15
         * userBean : {"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpeg","uHeight":0,"uWeight":0,"uFansnum":2,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}
         * text : 8J+YgPCfmITwn5iB8J+YnfCfpKDwn5ij8J+YqfCfmJTwn5if8J+YoPCfmKvwn5ij4pi58J+YmvCf
         mKnwn6SX8J+kk/CfmJXwn5ih8J+YsvCfmLLwn5iu8J+YlvCfmKPwn6Sh8J+YmvCfmJrwn5iL8J+Y
         i/CfmJzwn5ic8J+YnPCfmJzwn5iS8J+YkvCfmJLwn5ir8J+Yq/CfpKHwn5ia8J+YmvCfpKHwn6Sh
         4pi54pi58J+YjvCfmJnwn5iZ8J+kofCfmJ3wn5iW

         * images : []
         * commentNum : 0
         * likeNum : 0
         * lookNum : 14
         * date : 2017-03-21
         * time : 11:28:30
         * likeArticle : false
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
