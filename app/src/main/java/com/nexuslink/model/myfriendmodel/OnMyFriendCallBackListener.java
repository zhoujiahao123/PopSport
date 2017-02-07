package com.nexuslink.model.myfriendmodel;

import com.nexuslink.model.data.FollowedInfo;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public interface OnMyFriendCallBackListener {
    void succeed(FollowedInfo followedInfo);
    void failed(Throwable throwable);
}
