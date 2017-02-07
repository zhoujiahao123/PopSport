package com.nexuslink.presenter.myfriendpresenter;

import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.friendmodel.FriendModel;
import com.nexuslink.model.myfriendmodel.MyFriendModel;
import com.nexuslink.model.myfriendmodel.OnMyFriendCallBackListener;
import com.nexuslink.ui.view.FriendView;
import com.nexuslink.ui.view.MyFriendView;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class MyFriendPresenterImpl extends MyFriendPresenter implements OnMyFriendCallBackListener{
    private MyFriendView view;
    private MyFriendModel myFriendModel;

    public MyFriendPresenterImpl(MyFriendModel myFriendModel, MyFriendView myFriendView) {
        view = myFriendView;
        this.myFriendModel = myFriendModel;
    }

    @Override
    public void setFriendInfo() {
        myFriendModel.getFriend(this);
    }

    @Override
    public void setFriendInfoError() {

    }

    @Override
    public void succeed(FollowedInfo followedInfo) {
        view.showFriend(followedInfo);
    }

    @Override
    public void failed(Throwable throwable) {

    }
}
