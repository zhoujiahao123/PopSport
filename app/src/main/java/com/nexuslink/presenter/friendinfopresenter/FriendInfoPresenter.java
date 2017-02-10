package com.nexuslink.presenter.friendinfopresenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/2/8.
 */

public abstract class FriendInfoPresenter extends BasePresenter<BaseModel,BaseView> {
    public abstract void getFriendInfo(int fId);

    public abstract void setFriendInfo(FriendInfo friendInfo);

}
