package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommentInfo {



    private int code;
    private List<CommentsBean> comments;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * commentId : 1
         * articleId : 1
         * userId : 1
         * user : {"fId":1,"fName":"哇哈哈","fImg":"user1.jpeg","fAchievements":null}
         * commentFloor : 1
         * commentText : 1
         * date : 2017-03-18
         * time : 17:55:01
         */

        private int commentId;
        private int articleId;
        private int userId;
        private UserBean user;
        private int commentFloor;
        private String commentText;
        private String date;
        private String time;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getCommentFloor() {
            return commentFloor;
        }

        public void setCommentFloor(int commentFloor) {
            this.commentFloor = commentFloor;
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
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

        public static class UserBean {
            /**
             * fId : 1
             * fName : 哇哈哈
             * fImg : user1.jpeg
             * fAchievements : null
             */

            private int fId;
            private String fName;
            private String fImg;
            private Object fAchievements;

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

            public Object getFAchievements() {
                return fAchievements;
            }

            public void setFAchievements(Object fAchievements) {
                this.fAchievements = fAchievements;
            }
        }
    }
}
