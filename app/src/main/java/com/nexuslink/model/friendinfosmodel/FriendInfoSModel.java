package com.nexuslink.model.friendinfosmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public interface FriendInfoSModel extends BaseModel {
    void getUserInfo(int uId,OnUserCallBackListener listener);

    void getFollowInfo(int uId,OnUserCallBackListener listener);
}
