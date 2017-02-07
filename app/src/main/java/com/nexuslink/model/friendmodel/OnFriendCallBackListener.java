package com.nexuslink.model.friendmodel;

import com.nexuslink.model.data.FollowInfo;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public interface OnFriendCallBackListener {
    void succeed(FollowInfo followInfo);

    void failed(Throwable e);
}
