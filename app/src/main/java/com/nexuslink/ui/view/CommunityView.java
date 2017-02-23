package com.nexuslink.ui.view;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityView {
    void showSuccess(String str);
    void showError(String str);
    void clearInput(LinearLayout linearLayout,EditText input);
    void addMsgArticle(List<CommunityInfo.ArticlesBean> list);
    void setCommentsList(CommentsList commentsList, int aId,List<CommentItemData> commentItemDatas);
    void setCommentAdapter(CommentsList commentListView, int aId, List<CommentItemData> list);
    void addCommentNum(int pos);

}
