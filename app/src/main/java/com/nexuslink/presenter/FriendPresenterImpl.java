package com.nexuslink.presenter;

import com.elvishew.xlog.XLog;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.SearchInfo;
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
    public void searchUser(int type, String name) {
        model.searchUser(type,name,this);
        XLog.e("presenter的searchUser");
    }

    @Override
    void setMV(FriendModel model, FriendView view) {
        this.view = view;
        this.model = model;
    }
    @Override
    public void onSucceed(Object o) {
        if(o instanceof FollowInfo){
            if(((FollowInfo) o).getFlag()==1){
                view.succeedFollow();
            }
        }else if(o instanceof SearchInfo){
            view.showSearchUser((SearchInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {

    }
}
