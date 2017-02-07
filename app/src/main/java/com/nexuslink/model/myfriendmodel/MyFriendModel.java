package com.nexuslink.model.myfriendmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public interface MyFriendModel extends BaseModel {
    //获取我的好友列表信息
    void getFriend(OnMyFriendCallBackListener listener);
}
