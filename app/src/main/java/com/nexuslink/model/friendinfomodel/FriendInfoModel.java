package com.nexuslink.model.friendinfomodel;

import com.nexuslink.model.BaseModel;
import com.nexuslink.model.friendmodel.FriendModel;

/**
 * Created by ASUS-NB on 2017/2/8.
 */

public interface FriendInfoModel extends BaseModel{
    //获取信息
    void getFriendInfo(int fId, com.nexuslink.model.friendinfomodel.OnCallBackListener listener);
}
