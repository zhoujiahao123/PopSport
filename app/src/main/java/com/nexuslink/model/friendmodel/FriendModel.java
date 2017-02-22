package com.nexuslink.model.friendmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public interface FriendModel extends BaseModel {
    //关注某人
    void followOne(int uId, int fId,OnFriendCallBackListener listener);

    //搜索某人
    void searchUser(int type,String name,OnFriendCallBackListener listener);
}
