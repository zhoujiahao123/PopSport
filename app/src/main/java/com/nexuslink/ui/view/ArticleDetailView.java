package com.nexuslink.ui.view;

import com.nexuslink.model.data.CommentInfo;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface ArticleDetailView {
    void setCommentsView(List<CommentInfo.CommentsBean> list);
    String getCommentInput();
    void showError(String str);
    void addCommentView(CommentInfo.CommentsBean commentsBean);
    void addCommentNum();
    void clear();
}
