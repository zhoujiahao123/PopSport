package com.nexuslink.ui.view;

import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.SearchInfo;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public interface FriendView extends BaseView{
    //搜索用户
    void searchUser(int type,String uName);

    //显示fragment
    void showUserfragment();

    //关注某人
    void startFollow(int uId,int fId);

    //关注成功
    void succeedFollow();

    //关注失败
    void failedFollow();

    void showSearchUser(SearchInfo searchInfo);

    //获取关注的好友
    void getFollowed();

    //获取关注好友成功
    void getFollowedSucceed(FollowedInfo followedInfo);
}
