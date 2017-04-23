package com.nexuslink.ui.view;

import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.UserInfo;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public interface FriendInfoSView {
    void getUserInfo(int uId);

    void showUserInfo(UserInfo userInfo);

    void getUserFailed();

    void getUserFollow(int uId);

    void showUserFollowed(FollowedInfo followedInfo);

    void getFollowedFailed();
}
