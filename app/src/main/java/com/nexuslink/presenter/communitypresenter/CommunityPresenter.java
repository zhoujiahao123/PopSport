package com.nexuslink.presenter.communitypresenter;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityPresenter {
    void postLike(int userId,int articleId);
    void postDisLike(int userId,int articleId);
    void postComment(int userId,int articleId,String text,int pos);
    void onRefreshData(int userId);
    void loadUserInfo(ImageView imageView, TextView nameText, TextView levelText,int userId);

}
