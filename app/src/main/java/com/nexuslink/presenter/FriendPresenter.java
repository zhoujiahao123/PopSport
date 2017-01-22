package com.nexuslink.presenter;

import com.nexuslink.model.friendmodel.FriendModel;
import com.nexuslink.ui.view.FriendView;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public abstract class FriendPresenter extends BasePresenter<FriendModel,FriendView> {
    //关注某人
    public abstract void  followOne(int uId,int fId);

    //关注成功
    public abstract void followSucceed();

    //关注失败
    public abstract void followFailed();

    //获取用户的数据
    public abstract void getUserInfo();

    //获取好友的数据
    public abstract void getFriendInfo();

}
