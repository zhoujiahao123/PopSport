package com.nexuslink.presenter.myfriendpresenter;

import com.nexuslink.model.myfriendmodel.MyFriendModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.MyFriendView;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public abstract class MyFriendPresenter extends BasePresenter<MyFriendModel,MyFriendView> {
    public abstract void setFriendInfo();

    public abstract void setFriendInfoError();
}
