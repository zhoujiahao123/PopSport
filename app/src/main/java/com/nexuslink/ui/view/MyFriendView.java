package com.nexuslink.ui.view;

import com.nexuslink.model.data.FollowedInfo;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public interface MyFriendView extends BaseView{
    //私信
    void sendMessage();

    //查看好友信息
    void showFriendInfo();

    //展现我的好友列表
    void showFriend(FollowedInfo followedInfo);
    //展现好友列表出错
    void showFriendError();
}
