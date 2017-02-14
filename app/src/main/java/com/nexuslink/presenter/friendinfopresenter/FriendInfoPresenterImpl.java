package com.nexuslink.presenter.friendinfopresenter;

import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.friendinfomodel.FriendInfoModelImpl;
import com.nexuslink.model.friendinfomodel.OnCallBackListener;
import com.nexuslink.ui.view.FriendInfoView;

/**
 * Created by ASUS-NB on 2017/2/8.
 */

public class FriendInfoPresenterImpl extends FriendInfoPresenter implements OnCallBackListener{
    private FriendInfoModelImpl model;
    private FriendInfoView view;
    public FriendInfoPresenterImpl(FriendInfoModelImpl model, FriendInfoView view){
        this.view= view ;
        this.model= model;
    }
    @Override
    public void getFriendInfo(int fId) {
        model.getFriendInfo(fId,this);
    }

    @Override
    public void setFriendInfo(FriendInfo friendInfo) {
        view.showFriendInfo(friendInfo);
    }

    @Override
    public void onSucceed(Object o) {
        if(o instanceof FriendInfo){
            setFriendInfo((FriendInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {

    }
}
