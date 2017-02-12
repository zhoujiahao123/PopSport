package com.nexuslink.ui.view;

import com.nexuslink.model.data.CommunityInfo;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public interface CommunityView {
    void showSuccess();
    void showError();
    void addMsgArticle(List<CommunityInfo.CommunityBean> list);
    void addOneComment(String userName,String text);
    void addCommentNum();
}
