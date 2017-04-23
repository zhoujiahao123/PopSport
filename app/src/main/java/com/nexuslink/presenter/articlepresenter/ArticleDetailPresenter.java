package com.nexuslink.presenter.articlepresenter;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface ArticleDetailPresenter {
    void loadComments(int articleId);
    void postComment(int articleId);
    void postLike(int articleId);
    void loadArticle(int articleId);
}
