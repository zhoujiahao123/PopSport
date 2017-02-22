package com.nexuslink.ui.view;

import com.nexuslink.model.data.SearchInfo;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public interface FriendView extends BaseView{
    //搜索用户
    void searchUser(int type,String uName);

    //显示fragment
    void showUserfragment();

    //关注成功
    void succeedFollow();

    //关注失败
    void failedFollow();

    void showSearchUser(SearchInfo searchInfo);
}
