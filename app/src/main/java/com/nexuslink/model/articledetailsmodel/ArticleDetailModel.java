package com.nexuslink.model.articledetailsmodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface ArticleDetailModel {
    void postLike(int userId,int articleId,CallBackListener listener);
    void postDisLike(int userId,int articleId,CallBackListener listener);
    void postComment(int userId,int articleId,String text,CallBackListener listener);
    void getComments(int articleId,CallBackListener listener);
    void getArticle(int articleId, CallBackListener listener);
}
