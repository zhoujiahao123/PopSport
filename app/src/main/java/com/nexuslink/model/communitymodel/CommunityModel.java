package com.nexuslink.model.communitymodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityModel {

    void getArticles(int userId, CallBackListener listener);
    void postLike(int userId,int articleId,CallBackListener listener);
    void postDisLike(int userId,int articleId,CallBackListener listener);
    void postComment(int userId,int articleId,String text,CallBackListener listener);
    void loadUserInfo(int userId,String text,CallBackListener listener);
    void getComments(int articleId,CallBackListener listener);

}
