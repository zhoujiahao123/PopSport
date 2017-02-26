package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommentInfo {


    /**
     * code : 200
     * comments : [{"commentId":25,"articleId":12,"userId":15,"commentFloor":1,"commentText":"评论测试1","date":"2017-02-14","time":"21:52:19"},{"commentId":26,"articleId":12,"userId":15,"commentFloor":2,"commentText":"","date":"2017-02-14","time":"22:17:09"},{"commentId":27,"articleId":12,"userId":15,"commentFloor":3,"commentText":"","date":"2017-02-14","time":"22:17:14"},{"commentId":28,"articleId":12,"userId":15,"commentFloor":4,"commentText":"","date":"2017-02-14","time":"22:17:19"},{"commentId":29,"articleId":12,"userId":16,"commentFloor":5,"commentText":"政府的发达书法大赛","date":"2017-02-14","time":"22:24:18"},{"commentId":31,"articleId":12,"userId":15,"commentFloor":6,"commentText":"测试","date":"2017-02-14","time":"23:04:27"},{"commentId":32,"articleId":12,"userId":15,"commentFloor":7,"commentText":"我想问一下","date":"2017-02-14","time":"23:27:16"},{"commentId":33,"articleId":12,"userId":15,"commentFloor":8,"commentText":"仙武同修","date":"2017-02-14","time":"23:29:53"},{"commentId":34,"articleId":12,"userId":15,"commentFloor":9,"commentText":"酷兔兔","date":"2017-02-14","time":"23:35:53"},{"commentId":35,"articleId":12,"userId":15,"commentFloor":10,"commentText":"兔兔木有","date":"2017-02-14","time":"23:36:02"},{"commentId":36,"articleId":12,"userId":15,"commentFloor":10,"commentText":"哦我以为","date":"2017-02-14","time":"23:36:11"},{"commentId":37,"articleId":12,"userId":15,"commentFloor":10,"commentText":"弄完","date":"2017-02-14","time":"23:36:16"},{"commentId":38,"articleId":12,"userId":15,"commentFloor":10,"commentText":"哦哟哟。.hhj","date":"2017-02-14","time":"23:36:27"},{"commentId":39,"articleId":12,"userId":15,"commentFloor":10,"commentText":"jjbbbj","date":"2017-02-14","time":"23:36:30"},{"commentId":40,"articleId":12,"userId":15,"commentFloor":10,"commentText":"vvvv","date":"2017-02-14","time":"23:40:27"},{"commentId":41,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbb","date":"2017-02-14","time":"23:40:33"},{"commentId":42,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbb","date":"2017-02-14","time":"23:40:36"},{"commentId":43,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbb","date":"2017-02-14","time":"23:40:43"},{"commentId":44,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbbd","date":"2017-02-14","time":"23:41:06"},{"commentId":45,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbbdfff","date":"2017-02-14","time":"23:41:40"},{"commentId":46,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbbdfff","date":"2017-02-14","time":"23:41:45"},{"commentId":47,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbbdfff","date":"2017-02-14","time":"23:41:46"},{"commentId":48,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hbbdfff","date":"2017-02-14","time":"23:42:37"},{"commentId":51,"articleId":12,"userId":15,"commentFloor":10,"commentText":"hjjj","date":"2017-02-14","time":"23:47:07"},{"commentId":52,"articleId":12,"userId":15,"commentFloor":10,"commentText":"jjjnnn","date":"2017-02-14","time":"23:47:11"},{"commentId":53,"articleId":12,"userId":15,"commentFloor":10,"commentText":"vvdffxfjkk","date":"2017-02-14","time":"23:47:18"},{"commentId":55,"articleId":12,"userId":15,"commentFloor":10,"commentText":"cccc","date":"2017-02-14","time":"23:49:36"},{"commentId":56,"articleId":12,"userId":15,"commentFloor":10,"commentText":"ccxx","date":"2017-02-14","time":"23:49:44"},{"commentId":57,"articleId":12,"userId":10,"commentFloor":11,"commentText":"有Bug？","date":"2017-02-15","time":"00:16:29"},{"commentId":59,"articleId":12,"userId":16,"commentFloor":12,"commentText":"政府的发达书法大赛","date":"2017-02-15","time":"11:45:05"},{"commentId":62,"articleId":12,"userId":15,"commentFloor":13,"commentText":"擦","date":"2017-02-16","time":"21:57:24"}]
     */

    private int code;
    private List<CommentsBean> comments;

    @Override
    public String toString() {
        return "CommentInfo{" +
                "code=" + code +
                ", comments=" + comments +
                '}';
    }

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
        @Override
        public String toString() {
            return "CommentsBean{" +
                    "commentId=" + commentId +
                    ", articleId=" + articleId +
                    ", userId=" + userId +
                    ", commentFloor=" + commentFloor +
                    ", commentText='" + commentText + '\'' +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        /**
         * commentId : 25
         * articleId : 12
         * userId : 15
         * commentFloor : 1
         * commentText : 评论测试1
         * date : 2017-02-14
         * time : 21:52:19
         */

        private int commentId;
        private int articleId;
        private int userId;
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
    }
}
