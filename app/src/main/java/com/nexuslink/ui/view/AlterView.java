package com.nexuslink.ui.view;

import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
import com.nexuslink.model.data.UserInfo;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public interface AlterView extends BaseView {
    void showUserInfo(UserInfo userInfo);

    void showError();

    void changeUserInfo(int uId,char uGender, int uHeight, int uWeight);

    void showChangeUserInfo(ChangeInfo changeInfo);

    void changeNickName(int uId,String uName);

    void showChangeNickName(ChangeInfo1 changeInfo1);
}
