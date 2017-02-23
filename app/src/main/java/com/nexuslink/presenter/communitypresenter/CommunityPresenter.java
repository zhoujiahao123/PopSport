package com.nexuslink.presenter.communitypresenter;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.ui.view.linearlistview.LinearListView;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityPresenter {
    void postLike(int userId,int articleId);
    void postDisLike(int userId,int articleId);
    void postComment( EditText input, LinearLayout linearLayout,int userId, int articleId, int pos);
    void onRefreshData(int userId);

     void loadComment(LinearListView commentDetialLinear,int articleId, int pos);
}
