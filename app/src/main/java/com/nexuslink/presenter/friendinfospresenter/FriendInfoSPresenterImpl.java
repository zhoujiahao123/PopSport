package com.nexuslink.presenter.friendinfospresenter;

import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.model.friendinfosmodel.FriendInfoSModel;
import com.nexuslink.model.friendinfosmodel.FriendInfoSModelImpl;
import com.nexuslink.model.friendinfosmodel.OnUserCallBackListener;
import com.nexuslink.presenter.friendinfopresenter.FriendInfoPresenterImpl;
import com.nexuslink.ui.view.FriendInfoSView;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class FriendInfoSPresenterImpl extends FriendInfoSPresenter implements OnUserCallBackListener{
    private FriendInfoSModel model;
    private FriendInfoSView view;

    public FriendInfoSPresenterImpl(FriendInfoSModel model,FriendInfoSView view){
        this.model = model;
        this.view = view;
    }
    @Override
    public void getUserInfo(int uId) {
        model.getUserInfo(uId,this);
    }

    @Override
    public void getFollowedInfo(int uId) {
        model.getFollowInfo(uId,this);
    }

    @Override
    public void onSucceed(Object o) {
        if(o instanceof UserInfo){
            view.showUserInfo((UserInfo)o);
        }else if(o instanceof FollowedInfo){
            view.showUserFollowed((FollowedInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {
            view.getUserFailed();
            view.getFollowedFailed();
    }
}
