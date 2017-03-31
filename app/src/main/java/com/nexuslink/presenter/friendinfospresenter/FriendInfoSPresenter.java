package com.nexuslink.presenter.friendinfospresenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public abstract class FriendInfoSPresenter extends BasePresenter<BaseModel,BaseView>{
    public abstract void getUserInfo(int uId);

    public abstract void getFollowedInfo(int uId);
}
