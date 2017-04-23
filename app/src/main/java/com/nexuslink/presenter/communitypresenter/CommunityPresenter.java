package com.nexuslink.presenter.communitypresenter;

import android.widget.LinearLayout;

import com.vanniktech.emoji.EmojiEditText;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityPresenter {
    void postLike(int userId,int articleId);
    void postDisLike(int userId,int articleId);
    void postComment(LinearLayout commentsList, EmojiEditText input, LinearLayout linearLayout, int userId, int articleId, int pos);
    void onRefreshData(int userId,boolean autoRefresh);
    void onLoadMore(int aId);
     void loadComment(LinearLayout commentDetialLinear, int articleId, int pos);
}
