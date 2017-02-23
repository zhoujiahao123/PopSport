package com.nexuslink.ui.view;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.ui.view.linearlistview.LinearListView;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityView {
    void showSuccess(String str);
    void showError(String str);
    void clearInput(LinearLayout linearLayout,EditText input);
    void addMsgArticle(List<CommunityInfo.ArticlesBean> list);
    void addOneComment( int aId, String userName, String text);
    void setCommentAdapter(LinearListView commentListView,int aId,List<CommentItemData> list);
    void addCommentNum(int pos);

}
