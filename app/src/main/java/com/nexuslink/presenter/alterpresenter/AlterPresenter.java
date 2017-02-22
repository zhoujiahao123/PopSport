package com.nexuslink.presenter.alterpresenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.model.altermodel.AlterModel;
import com.nexuslink.model.altermodel.OnCallBackListener;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.AlterView;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public abstract class AlterPresenter extends BasePresenter<AlterModel,AlterView> {
    public abstract void getUserInfo(int uId);

    public abstract void changeUserInfo(int uId,char uGender, int uHeight, int uWeight);

    public abstract void changeNickName(int uId,String uName);

    public abstract void changePassword(int uId,String oldPassword,String newPassword);

}
