package com.nexuslink.model.data;

/**
 * Created by 猿人 on 2017/2/16.
 */

public class CommentItemData {
    private String userName;
    private String commentText;

    public CommentItemData(String userName, String commentText) {
        this.userName = userName;
        this.commentText = commentText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
