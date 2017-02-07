package com.nexuslink.presenter;

import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.friendmodel.FriendModel;
import com.nexuslink.model.friendmodel.OnFriendCallBackListener;
import com.nexuslink.ui.view.FriendView;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class FriendPresenterImpl extends FriendPresenter implements OnFriendCallBackListener{
    private FriendModel model;
    private FriendView view;

    public FriendPresenterImpl(FriendModel model, FriendView view) {
        setMV(model,view);
    }

    @Override
    public void followOne(int uId,int fId) {
        model.followOne(uId,fId,this);
    }

    @Override
    public void followSucceed() {

    }

    @Override
    public void followFailed() {

    }

    @Override
    public void getUserInfo() {

    }

    @Override
    public void getFriendInfo() {

    }

    @Override
    void setMV(FriendModel model, FriendView view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void succeed(FollowInfo followInfo) {
        if(followInfo.getFollowFlag()==1){
            view.succeedFollow();
        }

    }

    @Override
    public void failed(Throwable throwable) {
        view.failedFollow();
    }
}
