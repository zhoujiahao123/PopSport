package com.nexuslink.ui.view;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.model.data.CommentItemData;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityView<T> {
    void showSuccess(String str);
    void showError(String str);
    void clearInput(LinearLayout linearLayout,EditText input);
    void addMsgArticle(List<T> list);
    void setCommentsList(LinearLayout commentsList, int aId,List<CommentItemData> commentItemDatas);
    void setCommentAdapter(LinearLayout commentListView, int aId, List<CommentItemData> list);
    void addCommentNum(int pos);
    void showProgress();
    void hideProgress();
    void addCommunityItems(List<T> list);
    void addLikeNum(int pos);
    void decreaseLikeNum(int pos);
}
